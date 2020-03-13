package webservice.webservice.modele.entite.carte;

import webservice.webservice.modele.entite.carte.ICarte;

public enum Personnage implements ICarte {
    MOUTARDE("Moutarde"),
    ROSE("Rose"),
    PERVENCHE("Pervenche"),
    OLIVE("Olive"),
    VIOLET("Violet"),
    ORCHIDEE("Orchidée");

    private String nom;

    Personnage(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
