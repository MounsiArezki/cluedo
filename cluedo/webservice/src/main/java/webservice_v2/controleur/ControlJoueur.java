package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webservice_v2.config.ServiceConfig;
import webservice_v2.exception.JoueurPasDansLaPartieException;
import webservice_v2.exception.PartieInexistanteException;
import webservice_v2.exception.partie.ActionNonAutoriseeException;
import webservice_v2.exception.partie.PasJoueurActifException;
import webservice_v2.exception.partie.PasJoueurCourantException;
import webservice_v2.facade.Facade;
import webservice_v2.flux.GlobalReplayProcessor;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlJoueur {

    private Facade facade = Facade.getFac();

    @GetMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_CARTE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ICarte>> getJoueurCartes(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ){
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
    public ResponseEntity<Map<ICarte, Joueur>> getJoueurFiche(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ){
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
    public ResponseEntity<List<Integer>> lancerDes(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ) {
        try {
            List<Integer> des=facade.lancerDes(idP,idJ);
            Partie partie=facade.findPartie(idP);
            GlobalReplayProcessor.partieNotification.onNext(partie);
            return ResponseEntity.ok(des);
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

    // récupérer les données de l'accusation et les transmettre à la facade
    @PostMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_ACCUSER)
    public ResponseEntity<?> accuser(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ, @RequestBody Map<TypeCarte, ICarte> mc)  {
        try {

            facade.accuser(idP, idJ, mc);
            Partie partie=facade.findPartie(idP);
            GlobalReplayProcessor.partieNotification.onNext(partie);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PasJoueurCourantException e) {
            System.out.println("401 ws ce n'est pas le tour de ce joueur");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ActionNonAutoriseeException e) {
            System.out.println("401 ws action non autorisé : ce n'est pas le moment pour lancer une accusation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (JoueurPasDansLaPartieException e) {
            System.out.println("200 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    // récupérer les données de l'hypothèse et les transmettre à la facade
    @PostMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_HYPOTHESE)
    public ResponseEntity<?> hypothese(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ, @RequestBody Map<TypeCarte, ICarte> mc)  {
        try {
            facade.hypothese(idP, idJ, mc);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PasJoueurCourantException e) {
            System.out.println("401 ws ce n'est pas le tour de ce joueur");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ActionNonAutoriseeException e) {
            System.out.println("401 ws action non autorisé : ce n'est pas le moment pour émettre une hypothèse");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (JoueurPasDansLaPartieException e) {
            System.out.println("200 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    // récupérer les données de l'hypothèse et les transmettre à la facade
    @PutMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_HYPOTHESE)
    public ResponseEntity<?> revelerCarte(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ, @RequestBody ICarte carte)  {
        try {
            facade.revelerCarte(idP, idJ, carte);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PasJoueurActifException e) {
            System.out.println("401 ws ce n'est pas le tour de ce joueur");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ActionNonAutoriseeException e) {
            System.out.println("401 ws action non autorisé : ce n'est pas le moment pour émettre une hypothèse");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (JoueurPasDansLaPartieException e) {
            System.out.println("200 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    // terminer son tour
    @PostMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_PASSER)
    public ResponseEntity<?> passer(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ)  {
        try {
            facade.passer(idP, idJ);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PasJoueurCourantException e) {
            System.out.println("401 ws ce n'est pas le tour de ce joueur");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ActionNonAutoriseeException e) {
            System.out.println("401 ws action non autorisé : ce n'est pas le moment pour lancer une accusation");
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