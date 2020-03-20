package client.client.service;

import client.client.modele.entite.Invitation;
import client.client.modele.entite.Partie;
import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

public interface IInvitationService {

    public ResponseEntity<Invitation> creerInvitation(User hote, List<User> listeInvites)throws HttpClientErrorException, HttpServerErrorException;

    public void supprimerInvitation(String idInvitation)throws HttpClientErrorException, HttpServerErrorException;

    public void accepterInvitation(String idInvitation)throws HttpClientErrorException, HttpServerErrorException;

    public void refuserInvitation(String idInvitation)throws HttpClientErrorException, HttpServerErrorException;

}
