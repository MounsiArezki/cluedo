package client.client.service;

import client.client.config.ServiceConfig;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.Partie;
import client.client.modele.entite.User;
import jdk.jfr.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facade implements IUserService, IInvitationService, IPartieService {

    RestTemplate restTemplate;

    public Facade() {
        restTemplate=new RestTemplate();
    }


    //
    // IUSERSERVICE
    //
   @Override
    public ResponseEntity<User[]> getAllUsers() {
        ResponseEntity<User[]> res=restTemplate.getForEntity(ServiceConfig.URL_USER, User[].class);
        return res;
    }

    @Override
    public ResponseEntity<User[]> getAllUsersWithFiltre(String filtre) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("filtre", filtre);
        ResponseEntity<User[]> res=restTemplate.getForEntity(ServiceConfig.URL_USER, User[].class, params);
        return res;
    }

    @Override
    public ResponseEntity<User> connexion(String login, String pwd) {
        User user=new User(login, pwd);
        ResponseEntity<User> res=restTemplate.postForEntity(ServiceConfig.URL_USER_CONNEXION,user,User.class);
        return res;
    }

    @Override
    public void deconnexion() {
        User user=VariablesGlobales.getUser();
        restTemplate.delete(ServiceConfig.URL_USER_CONNEXION,user);
    }

    @Override
    public ResponseEntity<User> insciption(String login, String pwd) {
        User user=new User(login, pwd);
        ResponseEntity<User> res=restTemplate.postForEntity(ServiceConfig.URL_USER,user,User.class);
        return res;
    }

    @Override
    public void desinscrition() {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());
        restTemplate.delete(ServiceConfig.URL_USER_ID, user, params);
    }

    @Override
    public ResponseEntity<Invitation[]> getAllInvitationsRecues() {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());
        ResponseEntity<Invitation[]> res=restTemplate.getForEntity(ServiceConfig.URL_USER_ID_INVITATION_RECU, Invitation[].class, params);
        return res;
    }

    @Override
    public ResponseEntity<Invitation[]> getAllInvitationsEmises() {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, user.getId());
        ResponseEntity<Invitation[]> res=restTemplate.getForEntity(ServiceConfig.URL_USER_ID_INVITATION_EMISE, Invitation[].class,params);
        return res;
    }


    //
    // IINVITATIONSERVICE
    //
    @Override
    public ResponseEntity<Invitation> creerInvitation(User hote, List<User> listeInvites) {
        Invitation invitation=new Invitation(hote, listeInvites);
        ResponseEntity<Invitation> res=restTemplate.postForEntity(ServiceConfig.URL_INVITATION, invitation, Invitation.class);
        return res;
    }

    @Override
    public void supprimerInvitation(String idInvitation) {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);
        restTemplate.delete(ServiceConfig.URL_INVITATION_ID, user, String.class,params);
    }

    @Override
    public void accepterInvitation(String idInvitation) {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);
        restTemplate.put(ServiceConfig.URL_INVITATION_ID_ACCEPTATION, user, Partie.class, params);
    }

    @Override
    public void refuserInvitation(String idInvitation) {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);
        restTemplate.put(ServiceConfig.URL_INVITATION_ID_REFUS, user, Partie.class, params);
    }


    //
    // IPARTIESERVICE
    //


    @Override
    public void sauvegarderPartie(String idPartie) {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        restTemplate.put(ServiceConfig.URL_PARTIE_ID_SAUVEGARDE, user, params);
    }

    @Override
    public ResponseEntity<Partie> restaurerPartie(String idPartie) {
        User user=VariablesGlobales.getUser();
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.PARTIE_ID_PARAM, idPartie);
        params.put("idHote",user.getId());
        ResponseEntity<Partie> res=restTemplate.getForEntity(ServiceConfig.URL_PARTIE_ID_RESTAURATION, Partie.class, params);
        return res;
    }
}
