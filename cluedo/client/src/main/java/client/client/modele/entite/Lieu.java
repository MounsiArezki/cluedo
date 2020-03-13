package client.client.modele.entite;

public enum Lieu implements ICarte{

    SALLE_RECEPTION("Salle de réception"),
    SALLE_BILLARD("Salle de billard"),
    JARDIN_HIVERS("Jardin d'hivers"),
    SALLE_MANGER("Salle à manger"),
    ENTREE("Entrée"),
    BIBLIOTHEQUE("Bibliothèque"),
    SALON("Salon"),
    BUREAU("Bureau");

    private String nom;

    Lieu(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
