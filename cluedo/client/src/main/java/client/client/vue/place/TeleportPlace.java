package client.client.vue.place;

import client.client.modele.entite.carte.Lieu;
import client.client.modele.entite.Position;
import client.client.vue.cluedoPlateau.Key.DirectionKey;

public class TeleportPlace extends LieuPlace implements Teleportable {

    public TeleportPlace(DirectionKey direction, Lieu room,Position p) {
        this(direction, room, true, 1,p);
  /*      this.setOnMouseClicked(event -> {
            addHighlight(Color.RED);
            Alert a=new Alert(Alert.AlertType.INFORMATION,"teleportPlace"+direction.toString(), ButtonType.OK);
            a.show();});

   */
    }

    public TeleportPlace(DirectionKey direction, Lieu room, boolean isReachable, int moveCost,Position p) {
        super(direction, room, isReachable, moveCost, p);
    }


    @Override
    public Position teleportTo() {
        return super.getRoom().getCenter();
    }

}
