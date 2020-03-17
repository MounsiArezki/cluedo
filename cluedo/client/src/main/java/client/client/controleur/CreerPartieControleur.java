package client.client.controleur;

import client.client.modele.entite.io.FxmlUrl;
import client.client.vue.CreerPartie;
import client.client.vue.Menu;
import client.client.vue.View;
import javafx.stage.Stage;

public class CreerPartieControleur {

    CreerPartie creerPartie;
    Stage creerPartieStage;


    public CreerPartieControleur(Stage creerPartieStage) {
        this.creerPartieStage = creerPartieStage;
        creerPartie = (CreerPartie)CreerPartie.creerInstance(creerPartieStage , FxmlUrl.CREER_PARTIE.getUrl());
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
