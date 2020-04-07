package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webservice_v2.config.ServiceConfig;
import webservice_v2.exception.PartieInexistanteException;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;
import webservice_v2.facade.Facade;

import java.net.URI;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlPartie{

    private Facade facade = Facade.getFac();

    @GetMapping(value = ServiceConfig.URL_PARTIE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Partie> getPartieById(@PathVariable String id){
        Partie partie= null;
        try {
            partie = facade.findPartie(id);
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(partie);
    }


    @PutMapping(value = ServiceConfig.URL_PARTIE_ID_SAUVEGARDE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePartie(@PathVariable String id, @RequestBody User user) {
        try {
            facade.savePartie(id, user.getId());
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

    @GetMapping(value = ServiceConfig.URL_PARTIE_ID_RESTAURATION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Partie> restorePartie(@PathVariable String id, @RequestBody User user) {
        Partie partie=facade.restorePartie(id, user.getId());
        return ResponseEntity.ok(partie);
    }


}