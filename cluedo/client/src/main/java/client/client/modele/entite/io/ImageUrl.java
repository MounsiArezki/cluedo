package client.client.modele.entite.io;

import java.net.URL;

public enum  ImageUrl {




    cluedoPlateau(Object.class.getResource("/image/cluedo.png"));

    private URL url;

    ImageUrl(URL url) {
        this.url = url;
    }


    public URL getUrl() {
        return url;
    }
}
