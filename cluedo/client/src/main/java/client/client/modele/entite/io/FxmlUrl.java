package client.client.modele.entite.io;

import client.client.vue.Login;

import java.net.URL;

public enum FxmlUrl {

    LOGIN(Login.class.getResource("/vue/login.fxml")),
    PLATEAU(Object.class.getResource("/vue/plateau.fxml")),
    MENU(Object.class.getResource("/vue/menu.fxml")),
    CREER_PARTIE(Object.class.getResource("/vue/creerPartie.fxml")),
    RESTAURER_PARTIE(Object.class.getResource("/vue/restaurerPartie.fxml")),
    ACCUSATION(Object.class.getResource("/vue/accusation.fxml")),
    HYPOTHESE(Object.class.getResource("/vue/hypothese.fxml"));

    private URL url;

    FxmlUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }



}
