package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webservice_v2.config.ServiceConfig;
import webservice_v2.entite.Invitation;
import webservice_v2.entite.Partie;
import webservice_v2.entite.User;
import webservice_v2.facade.Facade;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlInvitation {

    private Facade facade = Facade.getFac();

    // récupérer toutes les invitations
    @GetMapping(value = "/invitation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Invitation>> getInv() {
        Collection<Invitation> liste= facade.getInvitations();
        return ResponseEntity.ok(liste);
    }

    // ajouter une invitation ET une partie
    @PostMapping(value = "/invitation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invitation> createInv(@RequestBody Invitation invitation) {
        User hote= invitation.getHote();
        Partie partie = facade.addPartie(hote.getId());
        /*ServletUriComponentsBuilder
                .fromUriString(ServiceConfig.URL_PARTIE)
                .path("/{id}")
                .buildAndExpand(partie.getId())
                .toUri(); //URI Partie*/
        Invitation i = facade.addInvitation(
                partie.getId(),
                invitation.getHote().getId(),
                invitation.getInvites()
        );
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
    @GetMapping(value = "/invitation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invitation> getInvById(@PathVariable String id) {
        Invitation i= facade.findInvitation(id);
        return ResponseEntity.ok(i);
    }

    // supprimer une invitation
    @DeleteMapping(value = "/invitation/{id}")
    public ResponseEntity<String> removeInv(@PathVariable String id) {
        facade.removeInvitation(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    //accepter une invitation
    @PutMapping(value = "/invitation/{id}/acceptation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> acceptInv(@PathVariable String id, @RequestBody User user) {
        facade.accepterInvitation(id, user.getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }

    //accepter une invitation
    @PutMapping(value = "/invitation/{id}/refus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> refuseInv(@PathVariable String id, @RequestBody User user) {
        facade.refuserInvitation(id, user.getId());
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
