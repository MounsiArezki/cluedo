package client.client.controleur;

import client.client.global.VariablesGlobales;
import client.client.modele.entite.Joueur;
import client.client.modele.entite.Partie;
import client.client.modele.entite.Position;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.Personnage;
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

import javax.servlet.http.Part;
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
        plateauView.refresh();
        plateauView.setTimer(5);
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

    public void retourMenu(){
        plateauView.stopTimer();
        new MenuControleur(plateauStage);
    }

    public void createCharacters() {
        this.characters = new ArrayList<>();

        // creer joueur
        Joueur j = partie.getJoueurs().get(VariablesGlobales.getUser().getId());
        Personnage perso = (Personnage) j.getPersonnage();
        Position p = new Position(7,7);
        this.player = new Player( this.plateauView, perso, getCluedoBoard().getItemFromCoordinate(p.getX(),p.getY()));

        this.characters.add(
                this.player
        );

        // TO DO ajout autres joueurs
        //....

        for(Place[] places : this.getCluedoBoard().getGrid()) {
            for(Place place : places) {
                place.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> this.getPlayer().moveTo((Place) event.getTarget()));
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
            }
        });
    }
}
