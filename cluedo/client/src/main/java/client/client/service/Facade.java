package client.client.service;

import client.client.config.ServiceConfig;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facade implements IUserService, IInvitationService {

    RestTemplate restTemplate;

    public Facade() {
        restTemplate=new RestTemplate();
    }


    //
    // IUSERSERVICE
    //
   @Override
    public ResponseEntity<User[]> getAllUsers(String login) {
        ResponseEntity<User[]> res=restTemplate.getForEntity(ServiceConfig.URL_USER, User[].class);
        return res;
    }

    @Override
    public ResponseEntity<User> connexion(String login, String pwd) {
        User user=new User(login, pwd);
        ResponseEntity<User> res=restTemplate.postForEntity(ServiceConfig.URL_USER_CONNEXION,user,User.class);
        return res;
    }

    @Override
    public void deconnexion(String idUser) {
        restTemplate.delete(ServiceConfig.URL_USER_CONNEXION,idUser,String.class);
    }

    @Override
    public ResponseEntity<User> insciption(String login, String pwd) {
        User user=new User(login, pwd);
        ResponseEntity<User> res=restTemplate.postForEntity(ServiceConfig.URL_USER,user,User.class);
        return res;
    }

    @Override
    public void desinscrition(String idUser) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", idUser);
        restTemplate.delete(ServiceConfig.URL_USER_ID,idUser,String.class,params);
    }

    @Override
    public ResponseEntity<Invitation[]> getAllInvitationsRecues(String idUser) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, idUser);
        ResponseEntity<Invitation[]> res=restTemplate.getForEntity(ServiceConfig.URL_USER_ID_INVITATION_RECU, Invitation[].class,params);
        return res;
    }

    @Override
    public ResponseEntity<Invitation[]> getAllInvitationsEmises(String idUser) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.USER_ID_PARAM, idUser);
        ResponseEntity<Invitation[]> res=restTemplate.getForEntity(ServiceConfig.URL_USER_ID_INVITATION_EMISE, Invitation[].class,params);
        return res;
    }


    //
    // IINVITATIONSERVICE
    //
    @Override
    public ResponseEntity<Invitation> creerInvitation(User hote, List<User> listeInvites) {
        Invitation invitation=new Invitation(hote, listeInvites);
        ResponseEntity<Invitation> res=restTemplate.postForEntity(ServiceConfig.URL_INVITATION,invitation,Invitation.class);
        return res;
    }

    @Override
    public void supprimerInvitation(String idInvitation) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(ServiceConfig.INVITATION_ID_PARAM, idInvitation);
        restTemplate.delete(ServiceConfig.URL_INVITATION_ID,idInvitation,String.class,params);
    }
}
