package client.client.controleur;

import client.client.modele.entite.*;
import client.client.modele.entite.io.FxmlPath;
import client.client.vue.Plateau;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;

public class PlateauControleur {

    Stage plateauStage;

    Plateau plateau;

    Partie partie;

    //simulation facade api
    Collection<ICarte> cartesJ ;



    public PlateauControleur(Stage plateauStage) {
        this.cartesJ= List.of(Personnage.MOUTARDE,Personnage.OLIVE,Arme.CORDE,Arme.CLE,Arme.COUTEAU);
        this.plateauStage =plateauStage;

        plateau = (Plateau)Plateau.creerInstance(plateauStage , FxmlPath.PLATEAU.getUrl());
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
