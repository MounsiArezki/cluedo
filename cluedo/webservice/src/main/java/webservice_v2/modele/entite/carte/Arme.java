package webservice_v2.modele.entite.carte;

public enum Arme implements ICarte {

    CHANDELIER("Chandelier"),
    POIGNARD("Poignard"),
    BARRE_DE_FER("Barre de fer"),
    REVOLVER("Révolver"),
    CORDE("Corde"),
    CLE_ANGLAISE("Clé anglaise");

    private String nom;

    Arme(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
