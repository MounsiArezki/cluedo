package client.client.service;

import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.function.Consumer;

public interface IUserService {

    public void subscribeFluxUsersConnectes(Consumer<User[]> consumer)throws HttpStatusCodeException, JsonProcessingException;

    public User[] getAllUsersWithFiltre(String filtre)throws HttpStatusCodeException, JsonProcessingException;

    public User connexion(String login, String pwd) throws HttpStatusCodeException, JsonProcessingException, DejaConnecteException, MdpIncorrectOuNonInscritException;

    public void deconnexion(String id) throws HttpStatusCodeException, JsonProcessingException;

    public User insciption(String login, String pwd) throws HttpStatusCodeException, JsonProcessingException, DejaInscritException;

    public void desinscrition() throws HttpStatusCodeException, JsonProcessingException;

    public void subscribeFluxInvitationsRecues(String idUser, Consumer<Invitation> consumer)throws HttpStatusCodeException, JsonProcessingException;

    public Invitation[] getAllInvitationsEmises() throws HttpStatusCodeException, JsonProcessingException;

}

