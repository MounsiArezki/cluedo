package webservice_v2.modele.entite.carte;

public enum Personnage implements ICarte {
    MOUTARDE("MOUTARDE"),
    ROSE("ROSE"),
    PERVENCHE("PERVENCHE"),
    OLIVE("OLIVE"),
    VIOLET("VIOLET"),
    ORCHIDEE("ORCHIDEE");

    private String nom;

    public String getNom() {
        return nom;
    }

    Personnage(String nom) {
        this.nom = nom;
    }

    public static ICarte getCarteByNom(String nom) {
        for (ICarte c : Personnage.values()) {
            if (c.getNom().equalsIgnoreCase(nom)) {
                return c;
            }
        }
        return null;
    }

}
