package client.client.modele.entite.carte;

import client.client.modele.entite.io.ImagePath;
import javafx.scene.paint.Color;

public enum Personnage implements ICarte{
    MOUTARDE("Moutarde", ImagePath.MOUTARDE_IMG.getUrl(),Color.ORANGE),
    ROSE("Rose", ImagePath.ROSE_IMG.getUrl(),Color.PURPLE),
    PERVENCHE("Pervenche", ImagePath.PERVENCHE_IMG.getUrl(),Color.GREEN),
    OLIVE("Olive", ImagePath.OLIVE_IMG.getUrl(),Color.BLUE),
    VIOLET("Violet", ImagePath.VIOLET_IMG.getUrl(),Color.RED),
    ORCHIDEE("Orchidée", ImagePath.ORCHIDE_IMG.getUrl(),Color.WHITE);

    private String nom;


    private Color color;
     Personnage(String nom, String imageUrl,Color color) {
        this.nom = nom;
        this.imageUrl = imageUrl;
        this.color=color;
    }
    Personnage(String nom) {
        this.nom = nom;
    }

    public Color getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private String imageUrl;


    public String getNom() {
        return nom;
    }

    @Override
    public void ImageUrl(String url) {
        this.imageUrl=url;
    }


    @Override
    public String toString() {
        return nom;
    }
}
