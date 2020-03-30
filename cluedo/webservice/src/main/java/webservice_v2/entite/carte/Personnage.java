package webservice_v2.entite.carte;

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
