package webservice_v2.modele.entite.carte;

public enum Arme implements ICarte {

    CHANDELIER("Chandelier"),
    COUTEAU("Couteau"),
    TUYAU_DE_PLOMB("Tuyau de plomb"),
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
