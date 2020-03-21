package client.client.service;

import client.client.config.ServiceConfig;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.Partie;
import client.client.modele.entite.User;
import com.google.gson.Gson;
import jdk.jfr.ContentType;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facade implements IUserService, IInvitationService, IPartieService {

    RestTemplate restTemplate;
    Gson gson;

    public Facade() {
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
        restTemplate.put(ServiceConfig.URL_INVITATION_ID_ACCEPTATION, httpEntity, Partie.class, params);
    }

    @Override
    public void refuserInvitation(String idInvitation) {
        User user=VariablesGlobales.getUser();
        HttpEntity<String> httpEntity=buildHttpEntity(user);
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);
        restTemplate.put(ServiceConfig.URL_INVITATION_ID_REFUS, httpEntity, Partie.class, params);
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
        Map<String, String> params = new HashMap<>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        ResponseEntity<String> res = restTemplate.getForEntity(ServiceConfig.URL_PARTIE_ID, String.class, params);
        return gson.fromJson(res.getBody(), Partie.class);
    }
}
