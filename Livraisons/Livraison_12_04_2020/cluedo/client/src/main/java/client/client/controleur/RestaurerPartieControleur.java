package client.client.controleur;

import client.client.modele.entite.io.FxmlPath;
import client.client.vue.RestaurerPartieView;
import javafx.stage.Stage;

    public class RestaurerPartieControleur {

        Stage restaurerPartieStage;
        RestaurerPartieView restaurerPartie;

        public RestaurerPartieControleur(Stage restaurerPartieStage) {
            this.restaurerPartieStage = restaurerPartieStage;
            restaurerPartie = (RestaurerPartieView)restaurerPartie.creerInstance(restaurerPartieStage , FxmlPath.RESTAURER_PARTIE.getUrl());
            restaurerPartie.setControleur(this);
            restaurerPartie.show("Restaurer Partie");
        }

    public void goToMenu() {
        new MenuControleur(restaurerPartieStage);
    }



}
