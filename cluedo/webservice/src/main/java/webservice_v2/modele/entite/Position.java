package webservice_v2.modele.entite;


import webservice_v2.modele.entite.carte.Lieu;

public class Position {

    int x;
    int y;

    public Position() {
    }

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
