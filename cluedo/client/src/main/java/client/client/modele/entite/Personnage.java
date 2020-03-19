package client.client.modele.entite;

import client.client.modele.entite.io.ImagePath;

public enum Personnage implements ICarte{
    MOUTARDE("Moutarde", ImagePath.MOUTARDE_IMG.getUrl()),
    ROSE("Rose", ImagePath.ROSE_IMG.getUrl()),
    PERVENCHE("Pervenche", ImagePath.PERVENCHE_IMG.getUrl()),
    OLIVE("Olive", ImagePath.OLIVE_IMG.getUrl()),
    VIOLET("Violet", ImagePath.VIOLET_IMG.getUrl()),
    ORCHIDEE("Orchidée", ImagePath.ORCHIDE_IMG.getUrl());

    private String nom;

     Personnage(String nom, String imageUrl) {
        this.nom = nom;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private String imageUrl;

     Personnage(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public void ImageUrl(String url) {
        this.imageUrl=url;

    }
}
