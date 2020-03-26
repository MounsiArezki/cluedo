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

import java.util.HashSet;
import java.util.Set;

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

        test();
        System.out.println("caca ");

        moveTo(actPlace, true);
        System.out.println("caca fin");
    }


    public void test(){
        System.out.println("test");
        this.setVisible(true);
     //   this.actPlace = place;
       // Position position = actPlace.getCenter();
        System.out.println("i m in");
        this.setCenterX(443);
        this.setCenterY(607);
      //  place.setOccupied(true);
        this.toFront();
    }

    public void moveTo(Place place) {
        this.moveTo(place, false);
    }

    public void moveTo(Place place, boolean forceMove) {
        if(place instanceof Teleportable)
            place = (Place) plateau.getControleur().getCluedoBoard().getItemFromCoordinate(((Teleportable) place).teleportTo());

        // deplacer to void
        if(place == null) {
            System.out.println("i m ,not in");
            this.setVisible(false);
            this.setCenterX(Integer.MAX_VALUE);
            this.setCenterY(Integer.MAX_VALUE);
            return;
        }
        this.actPlace = place;
        Position position = actPlace.getCenter();
        System.out.println("i m in");
        this.setCenterX(position.getX());
        this.setCenterY(position.getY());
        place.setOccupied(true);
        this.toFront();

    }
/*
    public Set<Place> calcPosMoves(Place loc, int distance, int maxSpread) {
        if(maxSpread < 0)
            return new HashSet<>();
        Set<Place> moves = new HashSet<>();
        for(Place place : loc.getAdjacent()) {
            if(place == null || place.isOccupied())
                continue;
            if(distance - place.getMoveCost() < 0)
                continue;
            moves.addAll(calcPosMoves(place, distance - place.getMoveCost(), maxSpread-1));
            moves.add(place);
        }
        return moves;
    }
    */




    private void display() {
        Board board = this.plateau.getControleur().getCluedoBoard();
        this.setRadius(Math.min(board.getGrid()[0][0].getWidth(), board.getGrid()[0][0].getHeight())/1.66);
        this.setFill(personnage.getColor());
        this.setVisible(true);
    }

}
