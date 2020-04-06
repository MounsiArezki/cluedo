package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webservice_v2.config.ServiceConfig;
import webservice_v2.exception.JoueurPasDansLaPartieException;
import webservice_v2.exception.PartieInexistanteException;
import webservice_v2.facade.Facade;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.carte.ICarte;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlJoueur {

    private Facade facade = Facade.getFac();

    @GetMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_CARTE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ICarte>> getJoueurCartes(@PathVariable String idP, @PathVariable String idJ){
        try {
            return ResponseEntity.ok(facade.getJoueurCartes(idP, idJ));
        } catch (JoueurPasDansLaPartieException e) {
            System.out.println("200 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_CARTE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<ICarte, Joueur>> getJoueurFiche(@PathVariable String idP, @PathVariable String idJ){
        try {
            return ResponseEntity.ok(facade.getJoueurFiche(idP, idJ));
        } catch (JoueurPasDansLaPartieException e) {
            System.out.println("200 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}