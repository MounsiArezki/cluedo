package webservice_v2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webservice.webservice.DTO.entite.InvitationDTO;
import webservice.webservice.DTO.entite.UserDTO;
import webservice.webservice.modele.Facade;
import webservice.webservice.modele.entite.Invitation;
import webservice.webservice.modele.entite.Partie;
import webservice.webservice.modele.entite.User;
import webservice_v2.connexionException.DejaInscritException;
import webservice_v2.connexionException.MdpIncorrectException;
import webservice_v2.connexionException.NonInscritException;

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
    // Méthodes sur les invitations
    // ----------------------------------------------------------------------------------------



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