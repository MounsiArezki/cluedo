package client.client.modele.entite;

public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int[] getLocation() {
        return new int[] {x, y};
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }
}
