package webservice_v2.modele.entite.carte;

public enum Personnage implements ICarte {
    MOUTARDE("Moutarde"),
    ROSE("Rose"),
    PERVENCHE("Pervenche"),
    OLIVE("Olive"),
    VIOLET("Violet"),
    ORCHIDEE("Orchidée");

    private String nom;

    public String getNom() {
        return nom;
    }

    Personnage(String nom) {
        this.nom = nom;
    }

}
