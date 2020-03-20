package client.client.service;

import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    public ResponseEntity<User[]> getAllUsers();

    public ResponseEntity<User[]> getAllUsersWithFiltre(String filtre);

    public ResponseEntity<User> connexion(String login, String pwd);

    public void deconnexion();

    public ResponseEntity<User> insciption(String login, String pwd);

    public void desinscrition();

    public ResponseEntity<Invitation[]> getAllInvitationsRecues();

    public ResponseEntity<Invitation[]> getAllInvitationsEmises();

}
