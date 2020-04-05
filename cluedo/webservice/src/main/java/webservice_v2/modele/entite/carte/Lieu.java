package webservice_v2.modele.entite.carte;

public enum Lieu implements ICarte {

    BALL_ROOM("Salle de réception"),
    BILLIARD_ROOM("Salle de billard"),
    CONSERVATORY("Jardin d'hivers"),
    DINING_ROOM("Salle à manger"),
    HALL("Entrée"),
    LIBRARY("Bibliothèque"),
    LOUNGE("Salon"),
    KITCHEN("Cuisine"),
    STUDY("Bureau");

    private String nom;

    Lieu(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}
