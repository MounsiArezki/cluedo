package client.client.modele.entite.carte;

import client.client.modele.entite.Position;

public enum Lieu implements ICarte{

    HALL("le Hall",  'h', new Position(11, 3)),
    LOUNGE("le Lounge", 'l', new Position(20, 2)),
    DINING_ROOM("le Dining Room", 'd', new Position(20, 11)),
    KITCHEN("la Kitchen", 'k', new Position(22, 23)),
    BALL_ROOM("la Ball Room",  'a', new Position(11, 20)),
    CONSERVATORY("le Conservatory",  'c', new Position(2, 20)),
    BILLIARD_ROOM("la Billiard Room",  'b', new Position(2, 14)),
    LIBRARY("la Library",  'i', new Position(3, 8)),
    STUDY("la Study",  's', new Position(3, 1)),
    EXIT("la Exit",  'e', new Position(11, 11))
    ;

    private String nom;
    private char key;
    private Position center;
    private String imageUrl;

    Lieu(){}

    public Position getCenter() {
        return center;
    }
    Lieu(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    Lieu(String name,  char key, Position centre) {
        this.nom = name;
        this.key = key;
        this.center = centre;
    }

    public static Lieu getLieu(char key) {
        for(Lieu lieu : Lieu.values()) {
            if(lieu.getKey() == key || (char) (key + 32) == lieu.getKey())
                return lieu;
        }
        return null;
    }

    @Override
    public TypeCarte getTypeCarte(){
        return TypeCarte.LIEU;
    }

}
