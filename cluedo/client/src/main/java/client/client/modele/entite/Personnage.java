package client.client.modele.entite;

import client.client.modele.entite.io.ImageUrl;


import java.net.URL;

public enum Personnage implements ICarte{
    MOUTARDE("Moutarde", ImageUrl.MOUTARDE_IMG.getUrl()),
    ROSE("Rose",ImageUrl.ROSE_IMG.getUrl()),
    PERVENCHE("Pervenche",ImageUrl.PERVENCHE_IMG.getUrl()),
    OLIVE("Olive",ImageUrl.OLIVE_IMG.getUrl()),
    VIOLET("Violet",ImageUrl.VIOLET_IMG.getUrl()),
    ORCHIDEE("Orchidée",ImageUrl.ORCHIDE_IMG.getUrl());

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
