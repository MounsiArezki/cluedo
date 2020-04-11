package client.client.modele.entite.carte;

import client.client.modele.entite.io.ImagePath;
import javafx.scene.paint.Color;

public enum Personnage implements ICarte{
    MOUTARDE("MOUTARDE", Color.ORANGE),
    ROSE("ROSE", Color.RED),
    PERVENCHE("PERVENCHE", Color.BLUE),
    OLIVE("OLIVE", Color.GREEN),
    VIOLET("VIOLET", Color.PURPLE),
    ORCHIDEE("ORCHIDEE", Color.WHITE);

    private String nom;
    private Color color;

    Personnage(){}

     Personnage(String nom,Color color) {
        this.nom = nom;
        this.color=color;
    }

    public Color getColor() {
        return color;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public TypeCarte getTypeCarte(){
        return TypeCarte.PERSONNAGE;
    }
}
