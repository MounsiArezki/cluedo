package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;
import webservice_v2.config.ServiceConfig;
import webservice_v2.exception.DejaInscritException;
import webservice_v2.exception.MdpIncorrectException;
import webservice_v2.exception.NonInscritException;
import webservice_v2.exception.PasConnecteException;
import webservice_v2.modele.entite.Invitation;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;
import webservice_v2.facade.Facade;

import java.net.URI;
import java.util.Collection;

import static webservice_v2.flux.GlobalReplayProcessor.*;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlUser {

    private Facade facade = Facade.getFac();

    // récupérer tous les utilisateurs
    @GetMapping(value = ServiceConfig.URL_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> getUsers() {
        Collection<User> liste = facade.getUsers();
        return ResponseEntity.ok(liste);
    }

    // récupérer tous les utilisateurs connectés
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ServiceConfig.URL_USER_CONNECTED, method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Collection<User>> getAllConnectedUsers() {
        return Flux.from(connectedUsersNotification);
    }

    // ajouter un utilisateur
    @PostMapping(value = ServiceConfig.URL_USER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User u = facade.addUser(user.getPseudo(), user.getPwd());
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(u.getId())
                    .toUri();
            return ResponseEntity
                    .created(location)
                    .body(u);
        }
        catch (DejaInscritException e) {
            System.out.println("409 ws error");
            return ResponseEntity.status(409).build();
        }

    }

    // trouver un utilisateur par son id
    @GetMapping(value = ServiceConfig.URL_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findUser(@PathVariable(name=ServiceConfig.USER_ID_PARAM) String id) {
        User user=facade.findUser(id);
        return ResponseEntity.ok(user);
    }

    // trouver un/des user(s) par le début du pseudo
    @GetMapping(value = ServiceConfig.URL_USER_FILTRE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> filterUserByLogin(@PathVariable String filter) {
        Collection<User> liste=facade.filterUserByLogin(filter);
        return ResponseEntity.ok(liste);
    }

    // supprimer un utilisateur
    @DeleteMapping(value = ServiceConfig.URL_USER_ID)
    public ResponseEntity<String> removeUser(@PathVariable(name=ServiceConfig.USER_ID_PARAM) String id) {
        facade.removeUser(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    // connecter un utilisateur
    @PostMapping(value = ServiceConfig.URL_USER_CONNEXION)
    public ResponseEntity<?> connectUser(@RequestBody User user)  {
        try {
            User u = facade.connexion(user.getPseudo(), user.getPwd());
            connectedUsersNotification.onNext(facade.getConnectedUsers());
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(u.getId())
                    .toUri();
            connectedUsersNotification.onNext(facade.getConnectedUsers());
            return ResponseEntity
                    .created(location)
                    .body(u);
        }
        catch (MdpIncorrectException | NonInscritException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // déconnecter un utilisateur
    @DeleteMapping(value = ServiceConfig.URL_USER_DECONNEXION)
    public ResponseEntity<String> deconnectUser(@PathVariable(name=ServiceConfig.USER_ID_PARAM) String id, @RequestBody String pseudo) {
        try {
            facade.deconnexion(pseudo);
            connectedUsersNotification.onNext(facade.getConnectedUsers());
        } catch (PasConnecteException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    // récupère les parties SAUVEGARDEES d'un hôte
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ServiceConfig.URL_USER_ID_PARTIES_SAUVEGARDEES, method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Partie> getPartieSauvegardees(@PathVariable(name=ServiceConfig.USER_ID_PARAM) String id) {
        Collection<Partie> lp = facade.findPartieSauvegardeesByHost(id);
        return Flux.from(partieNotification).filter(lp::contains);
    }

    // récupère les invitations émises d'un user
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ServiceConfig.URL_USER_ID_INVITATION_EMISE, method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Invitation> getInvEmises(@PathVariable(name=ServiceConfig.USER_ID_PARAM) String id) {
        Collection<Invitation> li = facade.findInvitationByHost(id);
        return Flux.from(invitationsNotification).filter(li::contains);
    }

    // récupère les invitations reçues d'un user
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ServiceConfig.URL_USER_ID_INVITATION_RECU, method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Invitation> getInvRecues(@PathVariable(name=ServiceConfig.USER_ID_PARAM) String id) {
        return Flux.from(invitationsNotification).filter( i -> {
            User u = facade.findUser(id);
            return i.getInvites().contains(u);
        });
    }
}
