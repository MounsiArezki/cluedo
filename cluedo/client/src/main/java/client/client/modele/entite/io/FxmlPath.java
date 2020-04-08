package client.client.modele.entite.io;

import client.client.vue.*;

import java.net.URL;

public enum FxmlPath {

    LOGIN(Login.class.getResource("/vue/login.fxml")),
    PLATEAU(Plateau.class.getResource("/vue/plateau.fxml")),
    MENU(Menu.class.getResource("/vue/menu.fxml")),
    CREER_PARTIE(CreerPartie.class.getResource("/vue/creerPartie.fxml")),
    RESTAURER_PARTIE(RestaurerPartie.class.getResource("/vue/restaurerPartie.fxml")),
    ACCUSATION(Accusation.class.getResource("/vue/accusation.fxml")),
    HYPOTHESE(HypotheseVue.class.getResource("/vue/hypothese.fxml"));

    private URL url;

    FxmlPath(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }



}
