package client.client.modele.entite;

import java.net.URL;

public enum Lieu implements ICarte{

    SALLE_RECEPTION("Salle de réception"),
    SALLE_BILLARD("Salle de billard"),
    JARDIN_HIVERS("Jardin d'hivers"),
    SALLE_MANGER("Salle à manger"),
    ENTREE("Entrée"),
    BIBLIOTHEQUE("Bibliothèque"),
    SALON("Salon"),
    BUREAU("Bureau");

    private String nom;

    public String getImageUrl() {
        return imageUrl;
    }

    private String imageUrl;

    Lieu(String nom) {
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
