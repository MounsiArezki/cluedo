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
    private final DirectionKey direction;
    private final int moveCost;


    public Place[] getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(Place[] adjacent) {
        this.adjacent = adjacent;
    }

    public DirectionKey getDirection() {return direction; }

    public void addHighlight(Paint fill) {
        addHighlight(fill, 0.4);
    }
    public void delHighlight() {
        this.opacityProperty().set(0);
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
        this.moveCost = moveCost;
        this.isReachable=isReachable;
        this.direction=direction;
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
    public int getMoveCost() {   return moveCost; }

    public Position getCenter() { return new Position((int) (this.getX() + (this.getWidth()/2)), (int) (this.getY() + (this.getHeight()/2))); }


    private int getDistance(Place place, int maxSpread) {
        Queue<Pair<Place, Integer>> tree = new LinkedList<>();
        tree.add(new Pair<>(place, 0));
        return getDistance(tree, maxSpread);
    }

    public int getDistance(Place place) {  return this.getDistance(place, MAX_SP); }

    private int getDistance(Queue<Pair<Place, Integer>> tree, int maxSpread) {
        Pair<Place, Integer> top = tree.peek();

        // point de sortie
        if(top == null || top.getValue() == maxSpread)
            return -1;

        // point de sortie
        if(top.getKey() == this)
            return top.getValue();

        // Supprimer lorsque la valeur n'est po la cible
        tree.remove();

        // Ajouter des adjacents qui ne sont pas déjà dans tree
        for(Place adj : top.getKey().getAdjacent()) {
            if(adj == null)
                continue;

            boolean shouldContinue = false;
            for(Pair<Place, Integer> val : tree) {
                if(adj == val.getKey()) {
                    shouldContinue = true;
                    break;
                }
            }
            if(shouldContinue)
                continue;

            tree.add(new Pair<>(adj, top.getValue() + adj.getMoveCost()));
        }

        // Continuer .. tree
        return getDistance(tree, maxSpread);
    }


}
