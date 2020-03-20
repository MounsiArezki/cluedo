package webservice.webservice.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webservice.webservice.DTO.entite.UserDTO;
import webservice.webservice.modele.Facade;
import webservice.webservice.modele.entite.User;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/serv")
public class ServControl {

    Facade facade= Facade.getFac();

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les utilisateurs
    // ----------------------------------------------------------------------------------------

    // récupérer tous les utilisateurs
    @GetMapping(value="/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(facade.getUsers().stream().map(e -> UserDTO.creer(e)).collect(Collectors.toList()));
    }

    // ajouter un utilisateur
    @PostMapping(value="/user")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        User user=facade.addUser(userDTO.getPseudo(),userDTO.getPwd());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    // trouver un utilisateur par son id
    @GetMapping(value="/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(UserDTO.creer(facade.findUser(id)));
    }

    // trouver un utilisateur par son pseudo/login
    // Probleme URI ????

    // supprimer un utilisateur
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<Long> removeUser(@PathVariable long id){
        facade.removeUser(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
