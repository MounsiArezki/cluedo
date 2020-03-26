package client.client.vue.place;

import client.client.modele.entite.Position;
import client.client.vue.cluedoPlateau.Key.DirectionKey;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;

public class Place extends Rectangle {



    private boolean isOccupied;
    private final boolean isReachable;
    private Place[] adjacent;
    private static final int MAX_SP = 12;

    public Place[] getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(Place[] adjacent) {
        this.adjacent = adjacent;
    }


    public void addHighlight(Paint fill) {
        addHighlight(fill, 0.4);
    }

    public void addHighlight(Paint fill, double opacity) {
        this.opacityProperty().set(opacity);
        this.setFill(fill);
    }


    public Place() {
        this(DirectionKey.ALL, true, 1);
    }


    public Place(DirectionKey direction, boolean isReachable, int moveCost) {
        super();
        // TODORemove
     /*   this.setOnMouseClicked(event -> {
            addHighlight(Color.RED);
            Alert a=new Alert(Alert.AlertType.INFORMATION,this.getCenter().toString(), ButtonType.OK);
            a.show();});

      */
        this.isReachable=isReachable;
        this.setOpacity(0);
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isReachable() {
        return isReachable;
    }

    public Position getCenter() {
        return new Position((int) (this.getX() + (this.getWidth()/2)), (int) (this.getY() + (this.getHeight()/2)));
    }




}
