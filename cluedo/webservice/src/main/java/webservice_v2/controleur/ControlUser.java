package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;
import webservice_v2.config.ServiceConfig;
import webservice_v2.exception.*;
import webservice_v2.modele.entite.Invitation;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;
import webservice_v2.facade.Facade;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

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
        } catch (InscriptionIncorrecteException e) {
            System.out.println("409 saisie incomplète");
            return ResponseEntity.status(409).build();
        }

    }

    // trouver un utilisateur par son id
    @GetMapping(value = ServiceConfig.URL_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findUser(@PathVariable(name=ServiceConfig.USER_ID_PARAM) String id) {
        User user;
        try {
            user = facade.findUser(id);
        } catch (NonInscritException e) {
            System.out.println("404 joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    // trouver un/des user(s) par le début du pseudo
    @GetMapping(value = ServiceConfig.URL_USER_FILTRE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> filterUserByLogin(@PathVariable String filtre) {
        Collection<User> liste=facade.filterUserByLogin(filtre);
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
            connectedUsersNotification.onNext(facade.getAvailableUsers());
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(u.getId())
                    .toUri();
            connectedUsersNotification.onNext(facade.getAvailableUsers());
            return ResponseEntity
                    .created(location)
                    .body(u);
        }
        catch (MdpIncorrectException e) {
            System.out.println("401 ws mot de passe incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (DejaCoException e) {
            System.out.println("402 ws joueur déjà connecté");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (NonInscritException e) {
            System.out.println("404 ws joueur inexistant");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // déconnecter un utilisateur
    @DeleteMapping(value = ServiceConfig.URL_USER_DECONNEXION)
    public ResponseEntity<String> deconnectUser(@PathVariable(name=ServiceConfig.USER_ID_PARAM) String id) {
        try {
            String pseudo=facade.findUser(id).getPseudo();
            facade.deconnexion(pseudo);
            connectedUsersNotification.onNext(facade.getAvailableUsers());
        } catch (PasConnecteException e) {
            System.out.println("401 il faut être connecté pour se déconnecter");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NonInscritException e) {
            System.out.println("404 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
        Collection<Invitation> li = Collections.emptyList();
        try {
            li = facade.findInvitationByHost(id);
        } catch (NonInscritException e) {
            System.out.println("404 joueur inexistant");
        }
        return Flux.from(invitationsNotification).filter(i -> {
            User u = null;
            try {
                u = facade.findUser(id);
            } catch (NonInscritException e) {
                e.printStackTrace();
            }
            return i.getHote().equals(u);
        });
    }

    // récupère les invitations reçues d'un user
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ServiceConfig.URL_USER_ID_INVITATION_RECU, method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Invitation> getInvRecues(@PathVariable(name=ServiceConfig.USER_ID_PARAM) String id) {
        return Flux.from(invitationsNotification).filter(i -> {
            User u = null;
            try {
                u = facade.findUser(id);
            } catch (NonInscritException e) {
                e.printStackTrace();
            }
            return i.getInvites().contains(u);
        });
    }
}
