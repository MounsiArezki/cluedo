package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;
import webservice_v2.config.ServiceConfig;
import webservice_v2.exception.PartieInexistanteException;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;
import webservice_v2.facade.Facade;

import java.net.URI;

import static webservice_v2.flux.GlobalReplayProcessor.partieNotification;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlPartie{

    private Facade facade = Facade.getFac();

    // récupère la partie à restorer selon son id
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ServiceConfig.URL_PARTIE_ID, method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Partie> getPartieById(@PathVariable(name=ServiceConfig.PARTIE_ID_PARAM) String id) {
        Partie p;
        try {
            p = facade.findPartie(id);
            return Flux.from(partieNotification).filter(p::equals);
        } catch (PartieInexistanteException e) {
            // exception à traiter
        }
        return null;
    }

    // mettre une partie à sauvegarder (pour continuer plus tard)
    @PutMapping(value = ServiceConfig.URL_PARTIE_ID_SAUVEGARDE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> savePartie(@PathVariable(name=ServiceConfig.PARTIE_ID_PARAM) String id, @RequestBody User user) {
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

    // récupère la partie à restorer selon son id
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = ServiceConfig.URL_PARTIE_ID_RESTAURATION, method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Partie> restorePartie(@PathVariable(name=ServiceConfig.PARTIE_ID_PARAM) String id, @RequestBody User user) {
        Partie p = facade.restorePartie(id, user.getId());
        return Flux.from(partieNotification).filter(p::equals);
    }

}