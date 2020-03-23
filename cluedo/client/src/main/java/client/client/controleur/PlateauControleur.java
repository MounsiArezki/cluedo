package client.client.controleur;

import client.client.modele.entite.*;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IPartieService;
import client.client.vue.Plateau;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;

public class PlateauControleur {

    Stage plateauStage;

    Plateau plateau;

    Partie partie;

    IPartieService partieService;

    //simulation facade api
    Collection<ICarte> cartesJ ;

    public PlateauControleur(Stage plateauStage, String idPartie) {
        this.cartesJ= List.of(Personnage.MOUTARDE,Personnage.OLIVE,Arme.CORDE,Arme.CLE,Arme.COUTEAU);
        this.plateauStage = plateauStage;
        this.partieService = new Facade();
        this.partie = partieService.getPartieById(idPartie);
        plateau = (Plateau)Plateau.creerInstance(plateauStage, FxmlPath.PLATEAU.getUrl());
        plateau.setControleur(this);
        plateau.refresh();
        plateau.setTimer(5);
        plateau.distribuerCartes();
        plateau.drawCluedoBoard();
        plateau.show("plateau");
    }


    public Collection<ICarte> getCarteJoueur() {
        return cartesJ;
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

    public Collection<ICarte> getCartesJ() {
        return cartesJ;
    }

    public void setCartesJ(Collection<ICarte> cartesJ) {
        this.cartesJ = cartesJ;
    }

    public void retourMenu(){
        plateau.stopTimer();
        new MenuControleur(plateauStage);
    }
}
