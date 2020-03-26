package client.client.vue.place;

import client.client.modele.entite.Lieu;
import client.client.modele.entite.Position;
import client.client.vue.cluedoPlateau.Key.DirectionKey;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

public class TeleportPlace extends LieuPlace implements Teleportable {

    public TeleportPlace(DirectionKey direction, Lieu room) {
        this(direction, room, true, 1);
  /*      this.setOnMouseClicked(event -> {
            addHighlight(Color.RED);
            Alert a=new Alert(Alert.AlertType.INFORMATION,"teleportPlace"+direction.toString(), ButtonType.OK);
            a.show();});

   */
    }

    public TeleportPlace(DirectionKey direction, Lieu room, boolean isReachable, int moveCost) {
        super(direction, room, isReachable, moveCost);
    }


    @Override
    public Position teleportTo() {
        return super.getRoom().getCenter();
    }
}
