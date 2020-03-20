package client.client.service;

import client.client.modele.entite.Invitation;
import client.client.modele.entite.Partie;
import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IInvitationService {

    public ResponseEntity<Invitation> creerInvitation(User hote, List<User> listeInvites);

    public void supprimerInvitation(String idInvitation);

    public void accepterInvitation(String idInvitation);

    public void refuserInvitation(String idInvitation);

}
