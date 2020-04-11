package client.client.modele.entite.carte;

import client.client.modele.entite.io.ImagePath;


public enum  Arme implements ICarte {

    COUTEAU("COUTEAU"),
    CHANDELIER("CHANDELIER"),
    REVOLVER("REVOLVER"),
    CORDE("CORDE"),
    TUYAU_DE_PLOMB("TUYAU_DE_PLOMB"),
    CLE_ANGLAISE("CLE_ANGLAISE");

    private String nom;

    Arme(){}

    Arme(String nom) {
        this.nom = nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public TypeCarte getTypeCarte(){
        return TypeCarte.ARME;
    }

}
