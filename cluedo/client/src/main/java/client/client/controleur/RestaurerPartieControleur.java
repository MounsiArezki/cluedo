package client.client.controleur;

import client.client.modele.entite.io.FxmlUrl;
import client.client.vue.CreerPartie;
import client.client.vue.RestaurerPartie;
import javafx.stage.Stage;

public class RestaurerPartieControleur {

    Stage restaurerPartieStage;
    RestaurerPartie restaurerPartie;

    public RestaurerPartieControleur(Stage restaurerPartieStage) {
        this.restaurerPartieStage = restaurerPartieStage;
        restaurerPartie = (RestaurerPartie)restaurerPartie.creerInstance(restaurerPartieStage , FxmlUrl.RESTAURER_PARTIE.getUrl());
        restaurerPartie.setControleur(this);
        restaurerPartie.show("Restaurer Partie");
    }

    public void goToMenu() {
        new MenuControleur(restaurerPartieStage);
    }



}