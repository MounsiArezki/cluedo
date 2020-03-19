package client.client.controleur;

import client.client.modele.entite.io.FxmlPath;
import client.client.vue.CreerPartie;
import javafx.stage.Stage;

public class CreerPartieControleur {

    CreerPartie creerPartie;
    Stage creerPartieStage;


    public CreerPartieControleur(Stage creerPartieStage) {
        this.creerPartieStage = creerPartieStage;
        creerPartie = (CreerPartie)CreerPartie.creerInstance(creerPartieStage , FxmlPath.CREER_PARTIE.getUrl());
        creerPartie.setControleur(this);
        creerPartie.show("Creation Partie");
    }

    public void goToMenu() {
        new MenuControleur(creerPartieStage);
    }

    public void goToPlateau() {
        new PlateauControleur(creerPartieStage);
    }
}
