package client.client.vue.cluedoPlateau.player;

import client.client.controleur.PlateauControleur;
import client.client.modele.entite.Personnage;
import client.client.modele.entite.Position;
import client.client.vue.Plateau;
import client.client.vue.cluedoPlateau.plateau.Board;
import client.client.vue.cluedoPlateau.plateau.CluedoBoard;
import client.client.vue.place.Place;
import client.client.vue.place.Teleportable;
import javafx.scene.shape.Circle;

public abstract class Character extends Circle {


    protected final int DES_NUM =6;
    protected int lancerNum;
    protected final Plateau plateau;
    protected final Personnage personnage;
    protected Place actPlace;

    protected Character( Plateau plat, Personnage personnage,Place departPlace) {
        this.plateau = plat;
        this.personnage = personnage;
        this.actPlace =departPlace;
     if(plateau != null) {
            plateau.getControleur().getCluedoBoard().getChildren().add(this);
            display();
        }



       // moveTo(departPlace, true);

    }


    public void moveTo(Place place) {
        this.moveTo(place, false);
    }

    public void moveTo(Place place, boolean forceMove) {
           if(place instanceof Teleportable)
            place = (Place) plateau.getControleur().getCluedoBoard().getItemFromCoordinate(((Teleportable) place).teleportTo());

        this.setVisible(true);

        // deplacer to void
        if(place == null) {
            this.setVisible(false);
            this.setCenterX(Integer.MAX_VALUE);
            this.setCenterY(Integer.MAX_VALUE);
            return;
        }
        this.actPlace = place;
        Position position = actPlace.getCenter();
        this.setCenterX(position.getX());
        this.setCenterY(position.getY());
        place.setOccupied(true);
        this.toFront();

    }


    private void display() {
        Board board = this.plateau.getControleur().getCluedoBoard();
        this.setRadius(Math.min(board.getGrid()[0][0].getWidth(), board.getGrid()[0][0].getHeight())/1.66);
        this.setFill(personnage.getColor());
        this.setVisible(true);
    }

}
