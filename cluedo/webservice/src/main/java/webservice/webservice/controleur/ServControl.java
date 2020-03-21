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

import javax.servlet.http.Part;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/serv")
@CrossOrigin("*")
public class ServControl {

    Facade facade = Facade.getFac();

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les utilisateurs
    // ----------------------------------------------------------------------------------------

    // récupérer tous les utilisateurs
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() {
        System.out.println("/user get");
        return ResponseEntity.ok(facade.getUsers().stream().map(e -> UserDTO.creer(e)).collect(Collectors.toList()));
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
        ResponseEntity<User> responseEntity=new ResponseEntity<>(user, HttpStatus.CREATED);
        Gson gson=new Gson();
        System.out.println(gson.toJson(responseEntity));
        return responseEntity;
    }

    // trouver un utilisateur par son id
    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(UserDTO.creer(facade.findUser(id)));
    }

    // trouver un utilisateur par son pseudo/login
    // Probleme URI ????

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
        ResponseEntity<User> responseEnty=ResponseEntity.created(location).body(user);
        Gson g=new Gson();
        System.out.println(g.toJson(responseEnty));
        return responseEnty;
    }

    // deconnecter un utilisateur
    @DeleteMapping(value = "/user/connexion/{id}")
    public ResponseEntity<String> deconnectUser(@PathVariable String id) {
        facade.deconnexion(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    // recupere les parties d'un hote
    @GetMapping(value = "/user/{id}/partie", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PartieDTO>> getParties(@PathVariable String id) {
        return ResponseEntity.ok(facade.getParties().stream().filter(u -> u.getIdHote().equals(id)).map(e -> PartieDTO.creer(e)).collect(Collectors.toList()));
    }

    // recupere les invitations emises d'un user
    @GetMapping(value = "/user/{id}/invitation/emise", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InvitationDTO>> getInvEmises(@PathVariable String id) {
        return ResponseEntity.ok(facade.getInvitations().stream().filter(u -> u.getIdHote().equals(id)).map(e -> InvitationDTO.creer(e)).collect(Collectors.toList()));
    }

    // recupere les invitations recues d'un user
    @GetMapping(value = "/user/{id}/invitation/recue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InvitationDTO>> getInvRecues(@PathVariable String id) {
        return ResponseEntity.ok(facade.getInvitations().stream().filter(u -> facade.findIfInvite(u,id)).map(e -> InvitationDTO.creer(e)).collect(Collectors.toList()));
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les invitations
    // ----------------------------------------------------------------------------------------

    // récupérer toutes les invitations
    @GetMapping(value = "/invitation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InvitationDTO>> getInv() {
        return ResponseEntity.ok(facade.getInvitations().stream().map(e -> InvitationDTO.creer(e)).collect(Collectors.toList()));
    }

    // ajouter une invitation ET une partie
    @PostMapping(value = "/invitation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvitationDTO> createInv(@RequestBody InvitationDTO invitationDTO) {
        Partie partie = facade.addPartie(invitationDTO.getIdHote());
        ServletUriComponentsBuilder.fromUriString("/partie").path("/{id}").buildAndExpand(partie.getId()).toUri();//URI Partie
        Invitation invitation = facade.addInvitation(partie.getId(), invitationDTO.getIdHote(), invitationDTO.getInvites());
        InvitationDTO newInvitationDTO = InvitationDTO.creer(invitation);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newInvitationDTO.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // trouver une invitation par son id
    @GetMapping(value = "/invitation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvitationDTO> getInvById(@PathVariable String id) {
        return ResponseEntity.ok(InvitationDTO.creer(facade.findInvitation(id)));
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

    @PutMapping(value = "/partie/{id}/sauvegarde", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePartie(@PathVariable String id, @RequestBody UserDTO userDTO) {
        facade.savePartie(userDTO.getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/partie/{id}/restauration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PartieDTO> getPartieById(@PathVariable String id) {
        return ResponseEntity.ok(PartieDTO.creer(facade.findPartie(id)));
    }
}