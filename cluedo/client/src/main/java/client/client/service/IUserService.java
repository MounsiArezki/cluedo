package client.client.service;

import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    public ResponseEntity<User[]> getAllUsers(String login);

    public ResponseEntity<User> connexion(String login, String pwd);

    public void deconnexion(String idUser);

    public ResponseEntity<User> insciption(String login, String pwd);

    public void desinscrition(String idUser);

    public ResponseEntity<Invitation[]> getAllInvitationsRecues(String idUser);

    public ResponseEntity<Invitation[]> getAllInvitationsEmises(String idUser);

}
