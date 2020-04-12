package webservice_v2.modele.entite.carte;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Arme implements ICarte {

    CHANDELIER("CHANDELIER"),
    COUTEAU("COUTEAU"),
    TUYAU_DE_PLOMB("TUYAU_DE_PLOMB"),
    REVOLVER("REVOLVER"),
    CORDE("CORDE"),
    CLE_ANGLAISE("CLE_ANGLAISE");

    private String nom;

    Arme(String nom) {
        this.nom = nom;
    }

    @JsonValue
    public String getNom() {
        return nom;
    }

    public static ICarte getCarteByNom(String nom) {
        for (ICarte c : Arme.values()) {
            if (c.getNom().equalsIgnoreCase(nom)) {
                return c;
            }
        }
        return null;
    }


}
