package client.client.service;

import client.client.config.CodeStatus;
import client.client.config.ServiceConfig;
import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.*;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;
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
                .uri(ServiceConfig.BASE_URL+ServiceConfig.URL_USER_CONNECTED)
                .retrieve()
                .bodyToFlux(User[].class);

        events.subscribe( consumer , Throwable::printStackTrace);
    }

    @Override
    public User[] getAllUsersWithFiltre(String filtre) throws HttpStatusCodeException, JsonProcessingException{
        /*Map<String, String> params = new HashMap<String, String>();
        params.put("filtre", filtre);
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_USER, String.class, params);
        return gson.fromJson(res.getBody(), User[].class);*/
        return new User[]{};
    }

    @Override
    public User connexion(String login, String pwd) throws JsonProcessingException, DejaConnecteException, MdpIncorrectOuNonInscritException {
        User user=new User(login, pwd);
        HttpEntity<String> httpEntity=buildHttpEntity(user);
        ResponseEntity<String> res = null;
        try{
             res=restTemplate.postForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_USER_CONNEXION,httpEntity,String.class);
        }catch (HttpStatusCodeException e){
            if (e.getStatusCode().value() == CodeStatus.CONFLICT.getCode()){
                throw new DejaConnecteException();
            }else if (e.getStatusCode().value() == CodeStatus.UNAUTHORIZED.getCode()){
                throw  new MdpIncorrectOuNonInscritException();
            }
        }

        User userCo=objectMapper.readValue(res.getBody(), User.class);
        return userCo;
    }

    @Override
    public void deconnexion(String id) throws HttpStatusCodeException, JsonProcessingException{

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, id);

        restTemplate.delete(ServiceConfig.BASE_URL+ServiceConfig.URL_USER_DECONNEXION,params);
    }

    @Override
    public User insciption(String login, String pwd) throws HttpStatusCodeException, JsonProcessingException, DejaInscritException {
        User user=new User(login, pwd);
        HttpEntity<String> httpEntity=buildHttpEntity(user);
        ResponseEntity<String> res = null;
    try{
        res=restTemplate.postForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_USER,httpEntity,String.class);

    }catch (HttpStatusCodeException e){
        if (e.getStatusCode().value()==CodeStatus.CONFLICT.getCode()){
            throw new DejaInscritException();
        }
    }

        User userInsc=objectMapper.readValue(res.getBody(), User.class);
        return userInsc;
    }

    @Override
    public void desinscrition() throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());

        restTemplate.delete(ServiceConfig.BASE_URL+ServiceConfig.URL_USER_ID, httpEntity, params);
    }

    @Override
    public void subscribeFluxInvitationsRecues(String idUser, Consumer<Invitation> consumer) throws HttpStatusCodeException, JsonProcessingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, idUser);

        Flux<Invitation> events = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(ServiceConfig.SCHEME)
                        .host(ServiceConfig.HOST)
                        .port(ServiceConfig.PORT)
                        .path(ServiceConfig.BASE_API+ServiceConfig.URL_USER_ID_INVITATION_RECU)
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

        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_USER_ID_INVITATION_EMISE, String.class,params);

        return objectMapper.readValue(res.getBody(),Invitation[].class);
    }


    //
    // IINVITATIONSERVICE
    //
    @Override
    public Invitation creerInvitation(User hote, List<User> listeInvites) throws HttpStatusCodeException, JsonProcessingException {
        Invitation invitation=new Invitation(hote, listeInvites);
        HttpEntity<String> httpEntity=buildHttpEntity(invitation);

        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_INVITATION, httpEntity, String.class);

        return objectMapper.readValue(res.getBody(),Invitation.class);
    }

    @Override
    public void supprimerInvitation(String idInvitation) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);

        restTemplate.delete(ServiceConfig.BASE_URL+ServiceConfig.URL_INVITATION_ID, httpEntity, String.class,params);
    }

    @Override
    public void accepterInvitation(String idInvitation) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);

        restTemplate.put(ServiceConfig.BASE_URL+ServiceConfig.URL_INVITATION_ID_ACCEPTATION, httpEntity, params);
    }

    @Override
    public void refuserInvitation(String idInvitation) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);

        restTemplate.put(ServiceConfig.BASE_URL+ServiceConfig.URL_INVITATION_ID_REFUS, httpEntity, params);
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
                        .scheme(ServiceConfig.SCHEME)
                        .host(ServiceConfig.HOST)
                        .port(ServiceConfig.PORT)
                        .path(ServiceConfig.BASE_API+ServiceConfig.URL_PARTIE_ID)
                        .build(params)
                )
                .retrieve()
                .bodyToFlux(Partie.class);

        events.subscribe( consumer , Throwable::printStackTrace);
    }

    @Override
    public Partie emettreHypothese(String idPartie, String idJoueur, Map<TypeCarte, ICarte> hypothese) {
        return null;
    }

    @Override
    public void sauvegarderPartie(String idPartie) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);

        restTemplate.put(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_SAUVEGARDE, httpEntity, params);
    }

    @Override
    public Partie restaurerPartie(String idPartie) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();

        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put("idHote",user.getId());
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_RESTAURATION, String.class, params);
        return objectMapper.readValue(res.getBody(),Partie.class);
    }



    //
    // IJOUEURSERVICE
    //

    @Override
    public List<ICarte> getCartesJoueurs(String idPartie) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);

        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_JOUEUR_CARTE, String.class,params);

        ICarte[] cartes = objectMapper.readValue(res.getBody(),ICarte[].class);
        List<ICarte> listeCartes = new ArrayList<>(List.of(cartes));

        return listeCartes;
    }

    @Override
    public void subscribeFluxFichePartie(String idPartie, Consumer<Map<ICarte, Joueur>> consumer) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());

        TypeReference<HashMap<ICarte,Joueur>> typeReference=new TypeReference<HashMap<ICarte, Joueur>>() {};

        Flux<Map<ICarte,Joueur>> events = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(ServiceConfig.SCHEME)
                        .host(ServiceConfig.HOST)
                        .port(ServiceConfig.PORT)
                        .path(ServiceConfig.BASE_API+ServiceConfig.URL_PARTIE_ID_JOUEUR_FICHE)
                        .build(params)
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

    @Override
    public void emettreHypothese(String idPartie, List<String> hypothese) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());

        HttpEntity<String> httpEntity=buildHttpEntity(hypothese);

        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_JOUEUR_HYPOTHESE, httpEntity, String.class, params);
    }

    @Override
    public List<Integer> lancerDes(String idPartie) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());

        ResponseEntity<Integer[]> res=restTemplate.getForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_JOUEUR_HYPOTHESE, Integer[].class, params);

        return new ArrayList<>(List.of(res.getBody()));
    }

    @Override
    public void emettreAccusation(String idPartie, List<String> accusation) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());

        HttpEntity<String> httpEntity=buildHttpEntity(accusation);
        System.out.println(httpEntity.getBody());

        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_JOUEUR_ACCUSER, httpEntity, String.class, params);
    }

    @Override
    public void passerTour(String idPartie) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());

        restTemplate.put(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_JOUEUR_PASSER, params);
    }

    @Override
    public void seDeplacer(String idPartie, Position position) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());

        HttpEntity<String> httpEntity=buildHttpEntity(position);

        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_JOUEUR_SE_DEPLACER, httpEntity, String.class, params);
    }

    @Override
    public void revelerCarte(String idPartie, ICarte carte) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());

        HttpEntity<String> httpEntity=buildHttpEntity(carte);

        restTemplate.put(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_JOUEUR_HYPOTHESE, httpEntity, String.class, params);
    }

    @Override
    public List<ICarte> piocherIndices(String idPartie) throws HttpStatusCodeException, JsonProcessingException {
        User user=VariablesGlobales.getUser();

        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());

        ResponseEntity<ICarte[]> res=restTemplate.getForEntity(ServiceConfig.BASE_URL+ServiceConfig.URL_PARTIE_ID_JOUEUR_PIOCHER_INDICES, ICarte[].class, params);

        return new ArrayList<>(List.of(res.getBody()));
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
