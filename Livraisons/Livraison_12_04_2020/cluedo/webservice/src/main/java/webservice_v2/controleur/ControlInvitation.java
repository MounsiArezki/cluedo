package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;
import webservice_v2.config.ServiceConfig;
import webservice_v2.exception.InvitationInvalideException;
import webservice_v2.exception.JoueurNonConnecteException;
import webservice_v2.exception.PartieInexistanteException;
import webservice_v2.modele.entite.Invitation;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;
import webservice_v2.facade.Facade;

import java.net.URI;

import static webservice_v2.flux.GlobalReplayProcessor.invitationsNotification;
import static webservice_v2.flux.GlobalReplayProcessor.partieNotification;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlInvitation {

    private Facade facade = Facade.getFac();

    // récupérer tous les invitations
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ServiceConfig.URL_INVITATION, method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Invitation> getAllInvitations() { return Flux.from(invitationsNotification); }

    // ajouter une invitation ET une partie
    @PostMapping(value = ServiceConfig.URL_INVITATION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invitation> createInv(@RequestBody Invitation invitation) {
        User hote= invitation.getHote();
        Partie partie = facade.addPartie(hote.getId());
        partieNotification.onNext(partie);
        /*ServletUriComponentsBuilder
                .fromUriString(ServiceConfig.URL_PARTIE)
                .path("/{id}")
                .buildAndExpand(partie.getId())
                .toUri(); //URI Partie*/
        Invitation i;
        try {
            i = facade.addInvitation(
                    partie.getId(),
                    invitation.getHote().getId(),
                    invitation.getInvites()
            );
            invitationsNotification.onNext(i);
        } catch (InvitationInvalideException | JoueurNonConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(i.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(i);
    }

    // trouver une invitation par son id
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ServiceConfig.URL_INVITATION_ID, method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Invitation> getInvById(@PathVariable(name=ServiceConfig.INVITATION_ID_PARAM) String id) {
        Invitation inv = facade.findInvitation(id);
        return Flux.from(invitationsNotification).filter(inv::equals);
    }

    // supprimer une invitation
    @DeleteMapping(value = ServiceConfig.URL_INVITATION_ID)
    public ResponseEntity<String> removeInv(@PathVariable String id) {
        facade.removeInvitation(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    // accepter une invitation
    @PutMapping(value = ServiceConfig.URL_INVITATION_ID_ACCEPTATION, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> acceptInv(@PathVariable(name = ServiceConfig.INVITATION_ID_PARAM) String id, @RequestBody User user) {
        try {
            facade.accepterInvitation(id, user.getId());
            Partie p = facade.findPartie(facade.findInvitation(id).getIdPartie());
            partieNotification.onNext(p);
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }

    // refuser une invitation
    @PutMapping(value = ServiceConfig.URL_INVITATION_ID_REFUS, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> refuseInv(@PathVariable(name = ServiceConfig.INVITATION_ID_PARAM) String id, @RequestBody User user) {
        try {
            facade.refuserInvitation(id, user.getId());
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }
}
