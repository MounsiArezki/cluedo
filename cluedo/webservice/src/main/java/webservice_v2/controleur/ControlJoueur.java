package webservice_v2.controleur;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webservice_v2.config.ServiceConfig;
import webservice_v2.exception.JoueurPasDansLaPartieException;
import webservice_v2.exception.PartieInexistanteException;
import webservice_v2.exception.partie.*;
import webservice_v2.facade.Facade;
import webservice_v2.flux.GlobalReplayProcessor;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.Position;
import webservice_v2.modele.entite.carte.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ServiceConfig.BASE_URL)
@CrossOrigin("*")
public class ControlJoueur {

    private Facade facade = Facade.getFac();

    // récupération des cartes d'jn joueur
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

    // récupération fiche joueur
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

    // lancement des dès
    @GetMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_LANCER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> lancerDes(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ) {
        try {
            List<Integer> des=facade.lancerDes(idP,idJ);
            GlobalReplayProcessor.partieNotification.onNext(facade.findPartie(idP));
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

    // tirer une carte indice
    @GetMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_TIRER_INDICE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> carteIndice(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ, @RequestBody List<Integer> des) {
        try {
            facade.tirerIndice(idP, idJ, des);
            GlobalReplayProcessor.partieNotification.onNext(facade.findPartie(idP));
            return ResponseEntity.ok(des);
        } catch (PasJoueurCourantException e) {
            System.out.println("401 ws ce n'est pas le tour de ce joueur");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (PiocherIndiceNonAutoriseException e) {
            System.out.println("401 ws action non autorisé : vous n'avez pas fait de loupe au lancer de dé");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ActionNonAutoriseeException e) {
            System.out.println("401 ws action non autorisé : ce n'est pas le moment pour de piocher une carte");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (JoueurPasDansLaPartieException e) {
            System.out.println("200 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    // se déplacer
    @PostMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_DEPLACER)
    public ResponseEntity<?> deplacer(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ, @RequestBody Position pos)  {
        try {
            facade.deplacer(idP, idJ, pos);
            GlobalReplayProcessor.partieNotification.onNext(facade.findPartie(idP));
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PasJoueurCourantException e) {
            System.out.println("401 ws ce n'est pas le tour de ce joueur");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ActionNonAutoriseeException e) {
            System.out.println("401 ws action non autorisé : ce n'est pas le moment pour de se déplacer");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (JoueurPasDansLaPartieException e) {
            System.out.println("200 ws joueur non trouvé");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PartieInexistanteException e) {
            System.out.println("200 ws partie non trouvée");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (DeplacementNonAutoriseException e) {
            System.out.println("401 ws action non autorisé : ce déplacement n'est pas possible");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // récupérer les données de l'accusation et les transmettre à la facade
    @PostMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_ACCUSER)
    public ResponseEntity<?> accuser(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ, @RequestBody List<String> mc)  {
        try {
            Map<TypeCarte, ICarte> mapCartes = new HashMap<>();
            for (String carte : mc){
                if (Arme.getCarteByNom(carte) != null){
                    mapCartes.put(TypeCarte.ARME, Arme.getCarteByNom(carte));
                } else if (Personnage.getCarteByNom(carte) != null){
                    mapCartes.put(TypeCarte.PERSONNAGE, Personnage.getCarteByNom(carte));
                } else if (Lieu.getCarteByNom(carte) != null){
                    mapCartes.put(TypeCarte.LIEU, Lieu.getCarteByNom(carte));
                } else {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
            }
            facade.accuser(idP, idJ, mapCartes);
            GlobalReplayProcessor.partieNotification.onNext(facade.findPartie(idP));
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
    public ResponseEntity<?> hypothese(@PathVariable(name = ServiceConfig.PARTIE_ID_PARAM) String idP, @PathVariable(name=ServiceConfig.JOUEUR_ID_PARAM) String idJ, @RequestBody List<String> mc)  {
        try {
            Map<TypeCarte, ICarte> mapCartes = new HashMap<>();
            for (String carte : mc){
                if (Arme.getCarteByNom(carte) != null){
                    System.out.println("ici");
                    mapCartes.put(TypeCarte.ARME, Arme.getCarteByNom(carte));
                } else if (Personnage.getCarteByNom(carte) != null){
                    System.out.println("là");
                    mapCartes.put(TypeCarte.PERSONNAGE, Personnage.getCarteByNom(carte));
                } else if (Lieu.getCarteByNom(carte) != null){
                    System.out.println("oups");
                    mapCartes.put(TypeCarte.LIEU, Lieu.getCarteByNom(carte));
                } else {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
            }
            facade.hypothese(idP, idJ, mapCartes);
            GlobalReplayProcessor.partieNotification.onNext(facade.findPartie(idP));
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
            GlobalReplayProcessor.partieNotification.onNext(facade.findPartie(idP));
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
    @PutMapping(value = ServiceConfig.URL_PARTIE_ID_JOUEUR_PASSER)
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