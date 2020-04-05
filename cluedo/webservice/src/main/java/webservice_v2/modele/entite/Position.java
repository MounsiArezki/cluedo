package webservice_v2.modele.entite;


import webservice_v2.modele.entite.carte.Lieu;

public class Position {

    Lieu lieu;
    int x;
    int y;

    public Position() {
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
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
