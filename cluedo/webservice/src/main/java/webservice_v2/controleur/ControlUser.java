package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webservice_v2.config.ServiceConfig;
import webservice_v2.connexionException.DejaInscritException;
import webservice_v2.connexionException.MdpIncorrectException;
import webservice_v2.connexionException.NonInscritException;
import webservice_v2.connexionException.PasConnecteException;
import webservice_v2.entite.Invitation;
import webservice_v2.entite.Partie;
import webservice_v2.entite.User;
import webservice_v2.facade.Facade;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlUser {

    private Facade facade = Facade.getFac();

    // récupérer tous les utilisateurs
    @GetMapping(value = ServiceConfig.URL_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> getUsers() {
        Collection<User> liste=facade.getUsers();
        return ResponseEntity.ok(liste);
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
    public ResponseEntity<User> findUser(@PathVariable String id) {
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
    public ResponseEntity<String> removeUser(@PathVariable String id) {
        facade.removeUser(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    // connecter un utilisateur
    @PostMapping(value = ServiceConfig.URL_USER_CONNEXION)
    public ResponseEntity<?> connectUser(@RequestBody User user)  {
        try {
            User u = facade.connexion(user.getPseudo(), user.getPwd());
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(u.getId())
                    .toUri();

            ResponseEntity<?> responseEnty = ResponseEntity
                    .created(location)
                    .body(u);
            return responseEnty;
        }
        catch (MdpIncorrectException | NonInscritException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // déconnecter un utilisateur
    @DeleteMapping(value = ServiceConfig.URL_USER_DECONNEXION)
    public ResponseEntity<String> deconnectUser(@PathVariable String id) {
        try {
            facade.deconnexion(id);
        } catch (PasConnecteException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    // récupère les parties SAUVEGARDEES d'un hôte
    @GetMapping(value = ServiceConfig.URL_USER_ID_PARTIES_SAUVEGARDEES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Partie>> getPartiesSauvegardees(@PathVariable String id) {
        Collection<Partie> liste = facade.findPartieSauvegardeesByHost(id);
        return ResponseEntity.ok(liste);
    }

    // récupère les invitations émises d'un user
    @GetMapping(value = ServiceConfig.URL_USER_ID_INVITATION_EMISE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Invitation>> getInvEmises(@PathVariable String id) {
        Collection<Invitation> invitations= facade.findInvitationByHost(id);
        return ResponseEntity.ok(invitations);
    }

    // récupère les invitations reçues d'un user
    @GetMapping(value = ServiceConfig.URL_USER_ID_INVITATION_RECU, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Invitation>> getInvRecues(@PathVariable String id) {
        Collection<Invitation> liste= facade.findInvitationByGuest(id);
        return ResponseEntity.ok(liste);
    }
}
