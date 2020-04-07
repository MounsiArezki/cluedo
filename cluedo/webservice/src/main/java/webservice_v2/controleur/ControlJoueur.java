package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import webservice_v2.config.ServiceConfig;
import webservice_v2.exception.JoueurPasDansLaPartieException;
import webservice_v2.exception.PartieInexistanteException;
import webservice_v2.exception.partie.ActionNonAutoriseeException;
import webservice_v2.exception.partie.PasJoueurCourantException;
import webservice_v2.facade.Facade;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;

import java.net.URI;
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

    @GetMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_FICHE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_LANCER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> lancerDes(@PathVariable String idP, @PathVariable String idJ) {
        try {
            return ResponseEntity.ok(facade.lancerDes(idP, idJ));
        } catch (PasJoueurCourantException e) {
            System.out.println("401 ws ce n'est pas le tour de ce joueur");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ActionNonAutoriseeException e) {
            System.out.println("401 ws action non autorisé : ce n'est pas le moment pour lancer les dés");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (JoueurPasDansLaPartieException e) {
            System.out.println("200 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    // créer une accusation et la transmettre à la facade
    @PostMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_ACCUSER)
    public ResponseEntity<?> accuser(@PathVariable String idP, @PathVariable String idJ, @RequestBody Map<TypeCarte, ICarte> mc)  {
        try {
            facade.accuser(idP, idJ, mc);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PasJoueurCourantException e) {
            System.out.println("401 ws ce n'est pas le tour de ce joueur");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ActionNonAutoriseeException e) {
            System.out.println("401 ws action non autorisé : ce n'est pas le moment pour lancer les dés");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (JoueurPasDansLaPartieException e) {
            System.out.println("200 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

}