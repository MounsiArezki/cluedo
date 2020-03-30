package webservice.webservice.controleurv2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webservice.webservice.DTO.entite.UserDTO;
import webservice.webservice.config.ServiceConfig;
import webservice.webservice.modele.Facade;
import webservice.webservice.modele.entite.Invitation;
import webservice.webservice.modele.entite.Partie;
import webservice.webservice.modele.entite.User;
import webservice.webservice.modele.exception.connexionException.DejaInscritException;
import webservice.webservice.modele.exception.connexionException.MdpIncorrectException;
import webservice.webservice.modele.exception.connexionException.NonInscritException;

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
    public ResponseEntity<List<UserDTO>> getUsers() {

        return ResponseEntity.ok(facade.getUsers().stream().map(UserDTO::creer).collect(Collectors.toList()));
    }

    // ajouter un utilisateur
    @PostMapping(value = ServiceConfig.URL_USER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {

        User user = null;
        try {
            user = facade.addUser(userDTO.getPseudo(), userDTO.getPwd());
            UserDTO newUserDTO = UserDTO.creer(user);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(newUserDTO.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (DejaInscritException e) {
            System.out.println("409 ws error");
            return ResponseEntity.status(409).build();
        }

    }

    // trouver un utilisateur par son id
    @GetMapping(value = ServiceConfig.URL_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findUser(@PathVariable String id) {
        return ResponseEntity.ok(UserDTO.creer(facade.findUser(id)));
    }

    // trouver un/des user(s) par le début du pseudo
    @GetMapping(value = ServiceConfig.URL_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> filterUserByLogin(@PathVariable String filter) {
        System.out.println("/user filter");
        return ResponseEntity.ok(facade.filterUserByLogin(filter).stream().map(UserDTO::creer).collect(Collectors.toList()));
    }

    // supprimer un utilisateur
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<String> removeUser(@PathVariable String id) {
        facade.removeUser(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    // connecter un utilisateur
    @PostMapping(value = "/user/connexion")
    public ResponseEntity<?> connectUser(@RequestBody UserDTO userDTO)  {
        User user = null;
        try {
            user = facade.connexion(userDTO.getPseudo(), userDTO.getPwd());
        }  catch (MdpIncorrectException | NonInscritException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        ResponseEntity<?> responseEnty = ResponseEntity.created(location).body(user);

        return responseEnty;
    }

    // déconnecter un utilisateur
    @DeleteMapping(value = "/user/connexion/{id}")
    public ResponseEntity<String> deconnectUser(@PathVariable String id) {
        facade.deconnexion(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    // récupère les parties d'un hôte
    @GetMapping(value = "/user/{id}/partie", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Partie>> getParties(@PathVariable String id) {
        return ResponseEntity.ok(facade.findPartieByHost(id));
    }

    // récupère les invitations émises d'un user
    @GetMapping(value = "/user/{id}/invitation/emise", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Invitation>> getInvEmises(@PathVariable String id) {
        return ResponseEntity.ok(facade.findInvitationByHost(id));
    }

    // récupère les invitations reçues d'un user
    @GetMapping(value = "/user/{id}/invitation/recue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Invitation>> getInvRecues(@PathVariable String id) {
        return ResponseEntity.ok(facade.findInvitationByGuest(id));
    }
}
