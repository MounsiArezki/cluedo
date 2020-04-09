package webservice_v2.modele.entite.carte;

public enum Lieu implements ICarte {

    BALL_ROOM("BALL_ROOM"),
    BILLIARD_ROOM("BILLIARD_ROOM"),
    CONSERVATORY("CONSERVATORY"),
    DINING_ROOM("DINING_ROOM"),
    HALL("HALL"),
    LIBRARY("LIBRARY"),
    LOUNGE("LOUNGE"),
    KITCHEN("KITCHEN"),
    STUDY("STUDY");

    private String nom;

    Lieu(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public static ICarte getCarteByNom(String nom) {
        for (ICarte c : Lieu.values()) {
            if (c.getNom().equalsIgnoreCase(nom)) {
                return c;
            }
        }
        return null;
    }
}
