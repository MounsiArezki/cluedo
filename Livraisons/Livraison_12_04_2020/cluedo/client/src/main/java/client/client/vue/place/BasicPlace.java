package client.client.vue.place;

import client.client.modele.entite.Position;
import client.client.vue.cluedoPlateau.Key.DirectionKey;

public class BasicPlace extends Place {


    public BasicPlace(Position position) {
        setPositionOnGrid(position);
    }
}
