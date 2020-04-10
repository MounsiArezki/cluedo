package client.client.controleur;

import client.client.global.VariablesGlobales;
import client.client.modele.entite.Joueur;
import client.client.modele.entite.Partie;
import client.client.modele.entite.Position;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.Lieu;
import client.client.modele.entite.carte.Personnage;
import client.client.modele.entite.etat_partie.Actions;
import client.client.modele.entite.etat_partie.IEtatPartie;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IJoueurService;
import client.client.service.IPartieService;
import client.client.vue.PlateauView;
import client.client.vue.cluedoPlateau.plateau.Board;
import client.client.vue.cluedoPlateau.player.Character;
import client.client.vue.cluedoPlateau.player.Player;
import client.client.vue.place.Place;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;

public class PlateauControleur {

    Stage plateauStage;
    PlateauView plateauView;
    Partie partie;
    Player player;

    IPartieService partieService;
    IJoueurService joueurService;

    public Player getPlayer() {
        return player;
    }

    List<Character> characters;
    //simulation get autres joueurs partie
    Collection<Joueur> joueursPartie;

    public PlateauControleur(Stage plateauStage, String idPartie) {
        this.plateauStage = plateauStage;
        this.partieService = Facade.getInstance();
        this.joueurService = Facade.getInstance();
        partie=new Partie();

        try {
            partieService.subscribeFluxPartie(idPartie, this::consumeFluxPartie);
        }
        catch (JsonProcessingException e) {
            plateauView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            plateauView.showMessage("Erreur "+e.getStatusCode(), Alert.AlertType.ERROR);
        }

        plateauView = (PlateauView) PlateauView.creerInstance(plateauStage, FxmlPath.PLATEAU.getUrl());
        plateauView.setControleur(this);
        plateauView.drawCluedoBoard();
        plateauView.show("plateau");
    }

    public void afficherListeJoueurs(){

    }

    public Collection<ICarte> getMyCard(){
        Collection<ICarte> cartesJ= new ArrayList<>();
        try {
            cartesJ = joueurService.getCartesJoueurs(partie.getId());
        }
        catch (JsonProcessingException e) {
            plateauView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            plateauView.showMessage("Erreur "+e.getStatusCode(), Alert.AlertType.ERROR);
        }
        System.out.println(cartesJ.toString());
        return cartesJ;
    }

    public Stage getPlateauStage() {
        return plateauStage;
    }

    public void setPlateauStage(Stage plateauStage) {
        this.plateauStage = plateauStage;
    }

    public PlateauView getPlateauView() {
        return plateauView;
    }

    public void setPlateauView(PlateauView plateauView) {
        this.plateauView = plateauView;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public IPartieService getPartieService() {
        return partieService;
    }

    public void setPartieService(IPartieService partieService) {
        this.partieService = partieService;
    }

    public Board<Place> getCluedoBoard(){
        return plateauView.getBoard();
    }

    public void goToHypothese(){
        /*Position p = partie.getJoueurs().get(VariablesGlobales.getUser().getId()).getPosition();
        Place place = getCluedoBoard().getItemFromCoordinate(p.getX(), p.getY());
        if(place instanceof LieuPlace){
            Lieu l = ((LieuPlace) place).getRoom();
            new HypotheseControleur(l, partie);
        }*/
        Lieu l = Lieu.HALL;
        new HypotheseControleur(l, partie);
        plateauView.showMessage("Vous devez être dans un lieu pour émettre une hypothèse", Alert.AlertType.ERROR);
    }

    public void goToAccusation(){
        new AccusationControleur(partie);
    }

    public void retourMenu(){
        new MenuControleur(plateauStage);
    }

    public void passerTour(){
        try {
            joueurService.passerTour(partie.getId());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createCharacters() {
        this.characters = new ArrayList<>();

        // creer joueur
        Joueur j = partie.getJoueurs().get(VariablesGlobales.getUser().getId());
        Personnage perso = (Personnage) j.getPersonnage();
        Position p = j.getPosition();
        this.player = new Player( this.plateauView, perso, getCluedoBoard().getItemFromCoordinate(p));

        this.characters.add(
                this.player
        );
        //attention distinguer les autres players

        for(Place[] places : this.getCluedoBoard().getGrid()) {
            for(Place place : places) {
                place.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> this.getPlayer().moveTo((Place) event.getTarget()));
            }
        }
    }

    public void gestionAction(){

        IEtatPartie etat = this.partie.getEtatPartie();
        Joueur j = etat.obtenirJoueurCourant();
        getPlateauView().disableAll(true);
        if(!etat.obtenirJoueurCourant().equals(this.partie.getJoueurs().get(VariablesGlobales.getUser().getId()))){
            getPlayer().setMY_TURN(false);
        } else {
            getPlayer().setMY_TURN(true);
            List<Actions> actionsList = etat.obtenirActionsPossibles();
            for (Actions action : actionsList){
                System.out.println(action);
                switch (action){
                    case PASSER:
                        this.plateauView.disablePasser(false);
                        break;
                    case ACCUSER:
                        this.plateauView.disableAccusation(false);
                        break;
                    case DEPLACER:
                        break;
                    case LANCER_DES:
                        this.plateauView.disableDes(false);
                        // activer les bouttons
                        break;
                    case JOUER_INDICE:
                        break;
                    case REVELER_CARTE:
                        this.plateauView.disableCartes(false);
                        break;
                    case PIOCHER_INDICE:
                        break;
                    case EMETTRE_HYPOTHESE:
                        this.plateauView.disableHypothese(false);
                        break;
                }
            }



        }

    }






    public int roll() {
        return this.player.lancerDes();
    }

    public void consumeFluxPartie(Partie partie){
        Platform.runLater( () ->{
            if (partie != null) {
                this.partie=partie;
                plateauView.refresh();
            }
        });
    }
}
