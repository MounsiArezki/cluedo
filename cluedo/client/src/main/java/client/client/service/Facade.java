package client.client.service;

import client.client.config.ServiceConfig;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.Joueur;
import client.client.modele.entite.Partie;
import client.client.modele.entite.User;
import client.client.modele.entite.carte.ICarte;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.*;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


public class Facade implements IUserService, IInvitationService, IPartieService, IJoueurService {

    RestTemplate restTemplate;
    ObjectMapper objectMapper;
    WebClient webClient;

    public static Facade facade=new Facade();

    public static Facade getInstance(){
        return new Facade();
    }

    public Facade() {
        objectMapper=new ObjectMapper();
        ExchangeStrategies strategies=ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecs -> {
                    clientDefaultCodecs.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                    clientDefaultCodecs.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                })
                .build();
        webClient=WebClient.builder().exchangeStrategies(strategies).build();
        restTemplate=new RestTemplate();
    }

    private HttpEntity<String> buildHttpEntity(Object o){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String objet="";
        try {
            objet= objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpEntity<String> httpEntity=new HttpEntity<>(objet, headers);
        return httpEntity;
    }

    //
    // IUSERSERVICE
    //
    @Override
    public void subscribeFluxUsersConnectes(Consumer<User[]> consumer) throws HttpStatusCodeException, JsonProcessingException {
        Flux<User[]> events = webClient
                .get()
                .uri(ServiceConfig.URL_USER_CONNECTED)
                .retrieve()
                .bodyToFlux(User[].class);

        events.subscribe( consumer , Throwable::printStackTrace);
    }

    @Override
    public User[] getAllUsersWithFiltre(String filtre) throws HttpStatusCodeException, JsonProcessingException{
        /*Map<String, String> params = new HashMap<String, String>();
        params.put("filtre", filtre);
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_USER, String.class, params);
        return gson.fromJson(res.getBody(), User[].class);*/
        return new User[]{};
    }

    @Override
    public User connexion(String login, String pwd) throws HttpStatusCodeException, JsonProcessingException{
        User user=new User(login, pwd);
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.URL_USER_CONNEXION,httpEntity,String.class);

        User userCo=objectMapper.readValue(res.getBody(), User.class);
        return userCo;
    }

    @Override
    public void deconnexion(String id) throws HttpStatusCodeException, JsonProcessingException{

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, id);

        restTemplate.delete(ServiceConfig.URL_USER_DECONNEXION,params);
    }

    @Override
    public User insciption(String login, String pwd) throws HttpStatusCodeException, JsonProcessingException{
        User user=new User(login, pwd);
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.URL_USER,httpEntity,String.class);

        User userInsc=objectMapper.readValue(res.getBody(), User.class);
        return userInsc;
    }

    @Override
    public void desinscrition() throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());

        restTemplate.delete(ServiceConfig.URL_USER_ID, httpEntity, params);
    }

    @Override
    public void subscribeFluxInvitationsRecues(String idUser, Consumer<Invitation> consumer) throws HttpStatusCodeException, JsonProcessingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, idUser);

        Flux<Invitation> events = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ServiceConfig.URL_USER_ID_INVITATION_RECU)
                        .build(params)
                )
                .retrieve()
                .bodyToFlux(Invitation.class);

        events.subscribe( consumer , Throwable::printStackTrace);
    }

    @Override
    public Invitation[] getAllInvitationsEmises() throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());

        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_USER_ID_INVITATION_EMISE, String.class,params);

        return objectMapper.readValue(res.getBody(),Invitation[].class);
    }


    //
    // IINVITATIONSERVICE
    //
    @Override
    public Invitation creerInvitation(User hote, List<User> listeInvites) throws HttpStatusCodeException, JsonProcessingException {
        Invitation invitation=new Invitation(hote, listeInvites);
        HttpEntity<String> httpEntity=buildHttpEntity(invitation);

        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.URL_INVITATION, httpEntity, String.class);

        return objectMapper.readValue(res.getBody(),Invitation.class);
    }

    @Override
    public void supprimerInvitation(String idInvitation) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);

        restTemplate.delete(ServiceConfig.URL_INVITATION_ID, httpEntity, String.class,params);
    }

    @Override
    public void accepterInvitation(String idInvitation) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);

        restTemplate.put(ServiceConfig.URL_INVITATION_ID_ACCEPTATION, httpEntity, params);
    }

    @Override
    public void refuserInvitation(String idInvitation) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);

        restTemplate.put(ServiceConfig.URL_INVITATION_ID_REFUS, httpEntity, params);
    }


    //
    // IPARTIESERVICE
    //
    @Override
    public void subscribeFluxPartie(String idPartie, Consumer<Partie> consumer) throws HttpStatusCodeException, JsonProcessingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);

        Flux<Partie> events = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ServiceConfig.URL_PARTIE_ID)
                        .build(params)
                )
                .retrieve()
                .bodyToFlux(Partie.class);

        events.subscribe( consumer , Throwable::printStackTrace);
    }

    @Override
    public void sauvegarderPartie(String idPartie) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);

        restTemplate.put(ServiceConfig.URL_PARTIE_ID_SAUVEGARDE, httpEntity, params);
    }

    @Override
    public Partie restaurerPartie(String idPartie) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put("idHote",user.getId());
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_PARTIE_ID_RESTAURATION, String.class, params);
        return objectMapper.readValue(res.getBody(),Partie.class);
    }



    //
    // IJOUEURSERVICE
    //

    @Override
    public List<ICarte> getCartesJoueurs(String idPartie) throws HttpStatusCodeException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);

        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_PARTIE_ID_JOUEUR_CARTE, String.class,params);

        ICarte[] cartes = objectMapper.readValue(res.getBody(),ICarte[].class);
        List<ICarte> listeCartes = new ArrayList<>(List.of(cartes));

        return listeCartes;
    }

    @Override
    public void subscribeFluxFichePartie(String idPartie, String idJoueur, Consumer<Map<ICarte, Joueur>> consumer) throws HttpStatusCodeException, JsonProcessingException {
        TypeReference<HashMap<ICarte,Joueur>> typeReference=new TypeReference<HashMap<ICarte, Joueur>>() {};
        Flux<Map<ICarte,Joueur>> events = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ServiceConfig.URL_PARTIE_ID_JOUEUR_FICHE)
                        .queryParam(ServiceConfig.PARTIE_ID_PARAM, idPartie)
                        .queryParam(ServiceConfig.JOUEUR_ID_PARAM, idJoueur)
                        .build()
                )
                .retrieve()
                .bodyToFlux(String.class)
                .map(string -> {
                    try {
                        return objectMapper.readValue(string, typeReference);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return new HashMap<>();
                    }
                });

        events.subscribe( consumer , Throwable::printStackTrace);
    }

    public void subscribeToTest(Consumer<String> fct) throws IOException {

        Flux<String> events = WebClient
                .create("http://localhost:8080/serv/test")
                .get()
                .retrieve()
                .bodyToFlux(String.class);

        events.subscribe( fct , Throwable::printStackTrace);
    }

    public String postTest(String test) throws HttpStatusCodeException {
        HttpEntity<String> httpEntity=buildHttpEntity(test);
        ResponseEntity<String>res=null;

        res=restTemplate.postForEntity("http://localhost:8080/serv/test",httpEntity,String.class);

        String tests="";
        try {
            tests=objectMapper.readValue(res.getBody(), String.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return tests;
    }

}
