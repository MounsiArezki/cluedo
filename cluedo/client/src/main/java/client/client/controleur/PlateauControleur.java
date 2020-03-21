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
        plateau.distribuerCartes();
        plateau.show("plateau");
    }


    public Collection<ICarte> getCarteJoueur() {
        return cartesJ;
    }


    public void afficherListeJoueurs(){

    }

}
