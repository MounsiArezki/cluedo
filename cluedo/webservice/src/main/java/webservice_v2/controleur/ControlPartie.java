package webservice_v2.controleur;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webservice_v2.config.ServiceConfig;
import webservice_v2.entite.Partie;
import webservice_v2.entite.User;
import webservice_v2.facade.Facade;

import java.net.URI;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlPartie{

    private Facade facade = Facade.getFac();

    @GetMapping(value = "/partie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Partie> getPartieById(@PathVariable String id){
        Partie partie=facade.findPartie(id);
        return ResponseEntity.ok(partie);
    }


    @PutMapping(value = "/partie/{id}/sauvegarde", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePartie(@PathVariable String id, @RequestBody User user) {
        facade.savePartie(id, user.getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping(value = "/partie/{id}/restauration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Partie> restorePartie(@PathVariable String id, @RequestBody User user) {
        Partie partie=facade.restorePartie(id, user.getId());
        return ResponseEntity.ok(partie);
    }

}