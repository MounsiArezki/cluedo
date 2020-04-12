package client.client.service;

import client.client.modele.entite.Invitation;
import client.client.modele.entite.Partie;
import client.client.modele.entite.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

public interface IInvitationService {

    public Invitation creerInvitation(User hote, List<User> listeInvites) throws HttpStatusCodeException, JsonProcessingException;

    public void supprimerInvitation(String idInvitation) throws HttpStatusCodeException, JsonProcessingException;

    public void accepterInvitation(String idInvitation) throws HttpStatusCodeException, JsonProcessingException;

    public void refuserInvitation(String idInvitation) throws HttpStatusCodeException, JsonProcessingException;

}
