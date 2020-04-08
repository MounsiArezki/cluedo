package client.client.controleur;

import client.client.global.VariablesGlobales;
import client.client.modele.entite.Joueur;
import client.client.modele.entite.Partie;
import client.client.modele.entite.Position;
import client.client.modele.entite.carte.Arme;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.Lieu;
import client.client.modele.entite.carte.Personnage;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IJoueurService;
import client.client.service.IPartieService;
import client.client.vue.Plateau;
import client.client.vue.cluedoPlateau.plateau.Board;
import client.client.vue.cluedoPlateau.player.Character;
import client.client.vue.cluedoPlateau.player.Player;
import client.client.vue.place.DepartPlace;
import client.client.vue.place.Place;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.*;

public class PlateauControleur {

    Stage plateauStage;
    Plateau plateau;
    Partie partie;
    Player player ;

    IPartieService partieService;
    IJoueurService joueurService;

    public Player getPlayer() {
        return player;
    }

    /*
        Simulation WS
     */

    List<Character> characters ;
    //simulation get autres joueurs partie
    Collection<Joueur> joueursPartie;

    public Collection<ICarte> getMyCard(){
        Collection<ICarte> cartesJ= joueurService.getCartesJoueurs(partie.getId());
        System.out.println(cartesJ.toString());
        return cartesJ;

    }



    /*
        END
     */
    public PlateauControleur(Stage plateauStage, String idPartie) {
        this.plateauStage = plateauStage;
        this.partieService = new Facade();
        this.joueurService = new Facade();
        this.partie = partieService.getPartieById(idPartie);
        plateau = (Plateau)Plateau.creerInstance(plateauStage, FxmlPath.PLATEAU.getUrl());
        plateau.setControleur(this);
        plateau.refresh();
        plateau.setTimer(5);
        plateau.drawCluedoBoard();

        plateau.show("plateau");
    }

    public void afficherListeJoueurs(){

    }

    public Stage getPlateauStage() {
        return plateauStage;
    }

    public void setPlateauStage(Stage plateauStage) {
        this.plateauStage = plateauStage;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public Partie getPartie() {
        this.partie = partieService.getPartieById(partie.getId());
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
        return plateau.getBoard();
    }

    public void retourMenu(){
        plateau.stopTimer();
        new MenuControleur(plateauStage);
    }

    public void createCharacters() {
        this.characters = new ArrayList<>();

        // creer joueur
        Joueur j = partie.getJoueurs().get(VariablesGlobales.getUser().getId());
        Personnage perso = (Personnage) j.getPersonnage();
        Position p = new Position(7,7);
        this.player = new Player( this.plateau, perso, getCluedoBoard().getItemFromCoordinate(p.getX(),p.getY()));

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
}
