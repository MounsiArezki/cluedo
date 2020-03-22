package client.client.vue.place;

import client.client.modele.entite.Lieu;
import client.client.modele.entite.Position;
import client.client.vue.cluedoPlateau.Key.DirectionKey;

public class TeleportPlace extends LieuPlace implements Teleportable {
/*
    public TeleportPlace(Lieu room) {
        this(room, true, 1);
    }

    public TeleportPlace(Lieu room, boolean isReachable, int moveCost) {
        super(room, isReachable, moveCost);
    }
*/


    @Override
    public Position teleportTo() {
        return null;
    }
}
