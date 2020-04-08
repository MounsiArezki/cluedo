package client.client.service;

import client.client.config.ServiceConfig;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.Joueur;
import client.client.modele.entite.Partie;
import client.client.modele.entite.User;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Facade implements IUserService, IInvitationService, IPartieService, IJoueurService {
    RestTemplate restTemplate;
    Gson gson;

    public Facade() {
        RestTemplateBuilder builder=new RestTemplateBuilder();
        restTemplate=new RestTemplate();
        gson=new Gson();
    }

    private HttpEntity<String> buildHttpEntity(Object o){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity=new HttpEntity<>(gson.toJson(o), headers);
        return httpEntity;
    }
    //
    // IUSERSERVICE
    //
    public void subscribeToTest(Consumer<String> fct) throws IOException {

        Flux<String> events = WebClient
                .create("http://localhost:8080/serv/test")
                .get()
                .retrieve()
                .bodyToFlux(String.class);

        //Disposable disposable = events.subscribe(test -> System.out.println("test " + test));

        //events.subscribe(eventCallback,
        //        Throwable::printStackTrace);

        events.subscribe( fct , Throwable::printStackTrace);
    }

    public String postTest(String test) throws HttpStatusCodeException {
        HttpEntity<String> httpEntity=buildHttpEntity(test);
        System.out.println(gson.toJson(httpEntity));
        ResponseEntity<String>res=null;

            res=restTemplate.postForEntity("http://localhost:8080/serv/test",httpEntity,String.class);



        return gson.fromJson(res.getBody(), String.class);
    }

    @Override
    public User[] getAllUsers() {
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_USER, String.class);
        return gson.fromJson(res.getBody(), User[].class);
    }

    @Override
    public User[] getAllUsersWithFiltre(String filtre) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("filtre", filtre);
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_USER, String.class, params);
        return gson.fromJson(res.getBody(), User[].class);
    }

    @Override
    public User connexion(String login, String pwd) {
        User user=new User(login, pwd);
        HttpEntity<String> httpEntity=buildHttpEntity(user);
        System.out.println(gson.toJson(httpEntity));
        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.URL_USER_CONNEXION,httpEntity,String.class);
        return gson.fromJson(res.getBody(), User.class);
    }

    @Override
    public void deconnexion() {
        User user=VariablesGlobales.getUser();
        System.out.println(user.getId()+"deconnec");
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());
        restTemplate.delete(ServiceConfig.URL_USER_DECONNEXION,params);
    }

    @Override
    public User insciption(String login, String pwd) {
        User user=new User(login, pwd);
        HttpEntity<String> httpEntity=buildHttpEntity(user);
        System.out.println(gson.toJson(httpEntity));
        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.URL_USER,httpEntity,String.class);
        return gson.fromJson(res.getBody(),User.class);
    }

    @Override
    public void desinscrition() {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());
        restTemplate.delete(ServiceConfig.URL_USER_ID, httpEntity, params);
    }

    @Override
    public Invitation[] getAllInvitationsRecues() {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_USER_ID_INVITATION_RECU, String.class, params);
        return gson.fromJson(res.getBody(),Invitation[].class);
    }

    @Override
    public Invitation[] getAllInvitationsEmises() {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_USER_ID_INVITATION_EMISE, String.class,params);
        return gson.fromJson(res.getBody(),Invitation[].class);
    }


    //
    // IINVITATIONSERVICE
    //
    @Override
    public Invitation creerInvitation(User hote, List<User> listeInvites) {
        Invitation invitation=new Invitation(hote, listeInvites);
        HttpEntity<String> httpEntity=buildHttpEntity(invitation);
        ResponseEntity<String> res=restTemplate.postForEntity(ServiceConfig.URL_INVITATION, httpEntity, String.class);
        return gson.fromJson(res.getBody(),Invitation.class);
    }

    @Override
    public void supprimerInvitation(String idInvitation) {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);
        restTemplate.delete(ServiceConfig.URL_INVITATION_ID, httpEntity, String.class,params);
    }

    @Override
    public void accepterInvitation(String idInvitation) {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);
        System.out.println("http "+gson.toJson(httpEntity));
        System.out.println("params "+gson.toJson(params));
        restTemplate.put(ServiceConfig.URL_INVITATION_ID_ACCEPTATION, httpEntity, params);
    }

    @Override
    public void refuserInvitation(String idInvitation) {
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
    public void sauvegarderPartie(String idPartie) {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        restTemplate.put(ServiceConfig.URL_PARTIE_ID_SAUVEGARDE, httpEntity, params);
    }

    @Override
    public Partie restaurerPartie(String idPartie) {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put("idHote",user.getId());
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_PARTIE_ID_RESTAURATION, String.class, params);
        return gson.fromJson(res.getBody(),Partie.class);
    }

    @Override
    public Partie getPartieById(String idPartie) {
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String, String> params = new HashMap<>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        ResponseEntity<String> res = restTemplate.getForEntity(ServiceConfig.URL_PARTIE_ID, String.class, params);
        Partie partie= null;
        System.out.println(res.getBody());
        try {
            partie = objectMapper.readValue(res.getBody(), Partie.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partie;
    }

    @Override
    public Partie emettreHypothese(String idPartie, String idJoueur, Map<TypeCarte, ICarte> hypothese){
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String, String> params = new HashMap<>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put(ServiceConfig.JOUEUR_ID_PARAM, idJoueur);
        HttpEntity<String> httpEntity=buildHttpEntity(hypothese);
        ResponseEntity<String> res = restTemplate.postForEntity(ServiceConfig.URL_PARTIE_ID_JOUEUR_HYPOTHESE, httpEntity, String.class, params);
        Partie partie = null;
        try {
            partie = objectMapper.readValue(res.getBody(),Partie.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return partie;
    }

    //
    // IJOUEURSERVICE
    //

    @Override
    public List<ICarte> getCartesJoueurs(String idPartie) {
        ObjectMapper objectMapper = new ObjectMapper();
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.JOUEUR_ID_PARAM, user.getId());
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_PARTIE_ID_JOUEUR_CARTE, String.class,params);
        List<ICarte> listeCartes = new ArrayList<>();
        try {
            ICarte[] cartes = objectMapper.readValue(res.getBody(),ICarte[].class);
            listeCartes.addAll(List.of(cartes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listeCartes;
    }

    @Override
    public Map<ICarte, Joueur> getFicheJoueur(String idPartie) {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        ResponseEntity<String> res=restTemplate.getForEntity(ServiceConfig.URL_PARTIE_ID_JOUEUR_FICHE, String.class,params);
        return gson.fromJson(res.getBody(),Map.class);
    }

}
