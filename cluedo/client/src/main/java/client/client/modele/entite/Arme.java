package client.client.modele.entite;

import client.client.modele.entite.io.ImageUrl;

import java.net.URL;

public enum  Arme implements ICarte {

    COUTEAU("couteau", ImageUrl.COUTEAU_IMG.getUrl()),
    CHANDELIER("CHANDELIER", ImageUrl.CHANDELIER_IMG.getUrl()),
    REVOLVER("revolver", ImageUrl.REVOLVER_IMG.getUrl()),
    CORDE("CORDE", ImageUrl.CORDE_IMG.getUrl()),
    TUYAU_EN_PLOMB("TUYAU_EN_PLOMB", ImageUrl.TUYAU_EN_PLOMP_IMG.getUrl()),
    CLE("cle", ImageUrl.CLE_IMG.getUrl()) ;

    Arme(String nom, String imageUrl) {
        this.nom = nom;
        this.imageUrl = imageUrl;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    private String nom;



    private String imageUrl;
    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public void ImageUrl(String url) {
        this.imageUrl=url;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }
}
