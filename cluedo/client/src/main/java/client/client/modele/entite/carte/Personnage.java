package client.client.modele.entite.carte;

import client.client.modele.entite.io.ImagePath;
import javafx.scene.paint.Color;

public enum Personnage implements ICarte{
    MOUTARDE("Moutarde", Color.ORANGE),
    ROSE("Rose", Color.RED),
    PERVENCHE("Pervenche", Color.BLUE),
    OLIVE("Olive", Color.GREEN),
    VIOLET("Violet", Color.PURPLE),
    ORCHIDEE("Orchidée", Color.WHITE);

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
