package client.client.modele.entite.io;

import java.net.URL;

public enum FxmlUrl {

    LOGIN(Object.class.getResource("/vue/login.fxml")),
    PLATEAU(Object.class.getResource("/vue/plateau.fxml")),
    MENU(Object.class.getResource("/vue/menu.fxml")),
    CREER_PARTIE(Object.class.getResource("/vue/creer.fxml")),
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
