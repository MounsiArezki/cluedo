package client.client.modele.entite.carte;

import client.client.modele.entite.io.ImagePath;

public enum  Arme implements ICarte {

    COUTEAU("couteau", ImagePath.COUTEAU_IMG.getUrl()),
    CHANDELIER("CHANDELIER", ImagePath.CHANDELIER_IMG.getUrl()),
    REVOLVER("revolver", ImagePath.REVOLVER_IMG.getUrl()),
    CORDE("CORDE", ImagePath.CORDE_IMG.getUrl()),
    TUYAU_DE_PLOMB("TUYAU_EN_PLOMB", ImagePath.TUYAU_EN_PLOMP_IMG.getUrl()),
    CLE_ANGLAISE("cle", ImagePath.CLE_IMG.getUrl()) ;

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
