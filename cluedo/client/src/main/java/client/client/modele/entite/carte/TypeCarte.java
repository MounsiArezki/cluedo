package client.client.modele.entite.carte;

public enum TypeCarte {

    OU("OU"),
    QUI("QUI"),
    AVECQUOI("AVECQUOI"),
    SPECIALE("SPECIALE"),
    LIEU("LIEU"),
    ARME("ARME"),
    PERSONNAGE("PERSONNAGE");

    private String nom;

    TypeCarte(){}

    TypeCarte(String nom){
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
