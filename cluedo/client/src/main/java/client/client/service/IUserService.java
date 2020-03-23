package client.client.service;

import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    public User[] getAllUsers();

    public User[] getAllUsersWithFiltre(String filtre);

    public User connexion(String login, String pwd);

    public void deconnexion();

    public User insciption(String login, String pwd);

    public void desinscrition();

    public Invitation[] getAllInvitationsRecues();

    public Invitation[] getAllInvitationsEmises();

}

