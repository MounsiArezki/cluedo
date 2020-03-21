package webservice.webservice.controleur;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webservice.webservice.DTO.entite.InvitationDTO;
import webservice.webservice.DTO.entite.PartieDTO;
import webservice.webservice.DTO.entite.UserDTO;
import webservice.webservice.modele.Facade;
import webservice.webservice.modele.entite.Invitation;
import webservice.webservice.modele.entite.Partie;
import webservice.webservice.modele.entite.User;
import webservice.webservice.modele.exception.DejaCoException;
import webservice.webservice.modele.exception.MdpIncorrectException;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/serv")
@CrossOrigin("*")
public class ServControl {

    private Facade facade = Facade.getFac();

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les utilisateurs
    // ----------------------------------------------------------------------------------------

    // récupérer tous les utilisateurs
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() {
        System.out.println("/user get");
        return ResponseEntity.ok(facade.getUsers().stream().map(UserDTO::creer).collect(Collectors.toList()));
    }

    // ajouter un utilisateur
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        System.out.println("/user post");
        User user = facade.addUser(userDTO.getPseudo(), userDTO.getPwd());
        UserDTO newUserDTO = UserDTO.creer(user);
        /*URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUserDTO.getId()).toUri();*/
        ResponseEntity<User> responseEntity = new ResponseEntity<>(user, HttpStatus.CREATED);
        Gson gson = new Gson();
        System.out.println(gson.toJson(responseEntity));
        return responseEntity;
    }

    // trouver un utilisateur par son id
    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findUser(@PathVariable String id) {
        return ResponseEntity.ok(UserDTO.creer(facade.findUser(id)));
    }

    // trouver un/des user(s) par le début du pseudo
    @GetMapping(value = "/user/filtre/{filter}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "/user/connexion", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> connectUser(@RequestBody UserDTO userDTO) throws DejaCoException, MdpIncorrectException {
        User user = facade.connexion(userDTO.getPseudo(), userDTO.getPwd());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        ResponseEntity<User> responseEnty = ResponseEntity.created(location).body(user);
        Gson g = new Gson();
        System.out.println(g.toJson(responseEnty));
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

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les invitations
    // ----------------------------------------------------------------------------------------

    // récupérer toutes les invitations
    @GetMapping(value = "/invitation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InvitationDTO>> getInv() {
        return ResponseEntity.ok(facade.getInvitations().stream().map(InvitationDTO::creer).collect(Collectors.toList()));
    }

    // ajouter une invitation ET une partie
    @PostMapping(value = "/invitation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invitation> createInv(@RequestBody Invitation invitationDTO) {
        Partie partie = facade.addPartie(invitationDTO.getHote().getId());
        ServletUriComponentsBuilder.fromUriString("/partie").path("/{id}").buildAndExpand(partie.getId()).toUri(); //URI Partie
        Invitation invitation = facade.addInvitation(partie.getId(), invitationDTO.getHote().getId(), invitationDTO.getInvites());
        InvitationDTO newInvitationDTO = InvitationDTO.creer(invitation);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newInvitationDTO.getId()).toUri();
        return ResponseEntity.created(location).body(invitation);
    }

    // trouver une invitation par son id
    @GetMapping(value = "/invitation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invitation> getInvById(@PathVariable String id) {
        return ResponseEntity.ok(facade.findInvitation(id));
    }

    // supprimer une invitation
    @DeleteMapping(value = "/invitation/{id}")
    public ResponseEntity<String> removeInv(@PathVariable String id) {
        facade.removeInvitation(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    //accepter une invitation
    @PutMapping(value = "/invitation/{id}/acceptation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> acceptInv(@PathVariable String id, @RequestBody UserDTO userDTO) {
        facade.accepterInvitation(id, userDTO.getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    //accepter une invitation
    @PutMapping(value = "/invitation/{id}/refus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> refuseInv(@PathVariable String id, @RequestBody UserDTO userDTO) {
        facade.refuserInvitation(id, userDTO.getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les parties
    // ----------------------------------------------------------------------------------------

    @GetMapping(value = "/partie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Partie> getPartieById(@PathVariable String id){
        Partie partie=facade.findPartie(id);
        return ResponseEntity.ok(partie);
    }


    @PutMapping(value = "/partie/{id}/sauvegarde", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePartie(@PathVariable String id, @RequestBody UserDTO userDTO) {
        facade.savePartie(id, userDTO.getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/partie/{id}/restauration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Partie> restorePartie(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(facade.restorePartie(id, userDTO.getId()));
    }
}