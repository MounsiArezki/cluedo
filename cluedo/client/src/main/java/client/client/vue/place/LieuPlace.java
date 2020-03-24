package client.client.vue.place;

import client.client.modele.entite.Lieu;
import client.client.vue.cluedoPlateau.Key.DirectionKey;

public class LieuPlace extends Place {




    private final Lieu room;
    private boolean isCenter;

    public LieuPlace(Lieu room) {
        this(room ,true, 0);
    }

    public LieuPlace(Lieu room, boolean isReachable, int moveCost) {
        this(DirectionKey.ALL, room, isReachable, moveCost);
    }

    public LieuPlace(DirectionKey directionKey, Lieu room, boolean isReachable, int moveCost) {
        super(directionKey, isReachable, moveCost);

        this.room = room;
    }

    public Lieu getRoom() {
        return room;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public void setCenter(boolean center) {
        isCenter = center;
    }

}
