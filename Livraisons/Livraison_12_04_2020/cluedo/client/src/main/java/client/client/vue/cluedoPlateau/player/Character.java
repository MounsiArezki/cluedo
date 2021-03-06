package client.client.vue.cluedoPlateau.player;

import client.client.modele.entite.carte.Personnage;
import client.client.modele.entite.Position;
import client.client.vue.PlateauView;
import client.client.vue.cluedoPlateau.plateau.Board;
import client.client.vue.cluedoPlateau.player.PlayerException.ImpossibleDeplacementException;
import client.client.vue.place.Place;
import client.client.vue.place.Teleportable;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.Circle;

import java.util.HashSet;
import java.util.Set;

public abstract class Character extends Circle {

   // private static final int  DES_NUM =1;


    public int getLancerNum() {
        return lancerNum;
    }

    protected int lancerNum;
    protected final PlateauView plateau;

    public Personnage getPersonnage() {
        return personnage;
    }

    protected final Personnage personnage;

    public boolean isMY_TURN() {
        return MY_TURN;
    }

    public void setMY_TURN(boolean MY_TURN) {
        this.MY_TURN = MY_TURN;
    }

    protected  boolean MY_TURN ;

    public Place getActPlace() {
        return actPlace;
    }

    protected Place actPlace;
    protected Set<Place> posMoves = new HashSet<>();
    boolean moveAuthorisation ;
    public boolean isMoveAuthorisation() {
        return moveAuthorisation;
    }

    public void setMoveAuthorisation(boolean moveAuthorisation) {
        this.moveAuthorisation = moveAuthorisation;
    }


    protected Character(PlateauView plat, Personnage personnage, Place departPlace) {
        this.plateau = plat;
        this.personnage = personnage;
        this.actPlace =departPlace;
        if(plateau != null) {
            plateau.getControleur().getCluedoBoard().getChildren().add(this);
            display();
        }
        this.actPlace = departPlace;
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(6.0);
        dropShadow.setOffsetY(4.0);
        this.setEffect(dropShadow);
        this.setCenterX(actPlace.getCenter().getX());
        this.setCenterY(actPlace.getCenter().getY());
        departPlace.setOccupied(true);
        this.toFront();
        /*moveTo(actPlace, true);*/
        this.MY_TURN =true;

    }
    //  lancer d??s
    public int lancerDes(int num) {
         /*   int NumTmp = 0;
            for(int i = 0; i < DES_NUM; i++)
                NumTmp += Math.random() * 6 + 1;
         */

        this.lancerNum = num;
        delHighlightPosMoves();
        this.posMoves = calcPosMoves();
      //  posMoves.stream().forEach(x-> x.addHighlight(Color.DARKGREEN));
        delHighlightPosMoves();
        highlightPosMoves();
        this.setMoveAuthorisation(true);
        return lancerNum;
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

    public void moveTo(Place place) throws ImpossibleDeplacementException {
        this.moveTo(place, false);
    }
    public void moveFromServer(Place place)  {
        this.moveTo(place, false);
    }

    public void moveTo(Place place, boolean forceMove) {
        if(place instanceof Teleportable)
            place = (Place) plateau.getControleur().getCluedoBoard().getItemFromCoordinate(((Teleportable) place).teleportTo());
        if(actPlace != null) {
            //Marquer place pr??c??dente comme inoccup??
            actPlace.setOccupied(false);
            if(!forceMove)
                //D??cr??menter d??s resultat
                this.lancerNum -= actPlace.getDistance(place);
        }
            // deplacer to void
        if(place == null) {
            System.out.println("i m ,not in");
            this.setVisible(false);
            this.setCenterX(Integer.MAX_VALUE);
            this.setCenterY(Integer.MAX_VALUE);
        } else {
            this.actPlace = place;
            Position position = actPlace.getCenter();
            System.out.println("i m in");
            this.setCenterX(position.getX());
            this.setCenterY(position.getY());
            place.setOccupied(true);
            this.toFront();
            // Calc new possible moves
        //    this.posMoves = calcPosMoves();
        }

    }

    public Set<Place> calcPosMoves() {return this.calcPosMoves(this.getActPlace(), this.getLancerNum());}

    public Set<Place> calcPosMoves(Place loc, int distance) {
        return this.calcPosMoves(loc, distance, distance);
    }

    public Set<Place> calcPosMoves(Place loc, int distance, int maxSp) {
        if(maxSp < 0)
            return new HashSet<>();
        Set<Place> moves = new HashSet<>();
        for(Place place : loc.getAdjacent()) {
            if(place == null || place.isOccupied()  )
                continue;
            if(distance - place.getMoveCost() < 0)
                continue;
            moves.addAll(calcPosMoves(place, distance - place.getMoveCost(), maxSp-1));
            moves.add(place);
        }


        return moves;
    }


    public Set<Place> getPosMoves() {
        if(posMoves == null)
            this.posMoves = calcPosMoves();
        return posMoves;
    }


    private void display() {
        Board board = this.plateau.getControleur().getCluedoBoard();
        this.setRadius(Math.min(board.getGrid()[0][0].getWidth()-8, board.getGrid()[0][0].getHeight())/1.66);
        this.setFill(personnage.getColor());
        this.setVisible(true);
    }
    protected void highlightPosMoves() {
        for(Place place : posMoves) {
            place.addHighlight(this.getPersonnage().getColor());
        }
    }

    protected void delHighlightPosMoves() {
        for(Place posMove : posMoves) {
            posMove.delHighlight();
        }
    }


}
