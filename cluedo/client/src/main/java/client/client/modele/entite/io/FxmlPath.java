package client.client.modele.entite.io;

import client.client.vue.*;

import java.net.URL;

public enum FxmlPath {

    LOGIN(LoginView.class.getResource("/vue/login.fxml")),
    PLATEAU(PlateauView.class.getResource("/vue/plateau.fxml")),
    MENU(MenuView.class.getResource("/vue/menu.fxml")),
    CREER_PARTIE(CreerPartieView.class.getResource("/vue/creerPartie.fxml")),
    RESTAURER_PARTIE(RestaurerPartieView.class.getResource("/vue/restaurerPartie.fxml")),
    ACCUSATION(AccusationView.class.getResource("/vue/accusation.fxml")),
    HYPOTHESE(HypotheseView.class.getResource("/vue/hypothese.fxml"));

    private URL url;

    FxmlPath(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }



}
