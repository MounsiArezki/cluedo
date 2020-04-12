package client.client.vue.place;

import client.client.modele.entite.Position;
import client.client.modele.entite.carte.Lieu;
import client.client.vue.cluedoPlateau.Key.DirectionKey;

public class LieuPlace extends Place {


    private final Lieu room;
    private boolean isCenter;

  /*  public LieuPlace(Lieu room) {
        this(room ,true, 0,null);
    }

   */

    public LieuPlace(Lieu room, boolean isReachable, int moveCost,Position p) {
        this(DirectionKey.ALL, room, isReachable, moveCost,p);
      // TO DO remove
   /*     this.setOnMouseClicked(event -> {
            addHighlight(Color.RED);
            Alert a=new Alert(Alert.AlertType.INFORMATION,"lieuPlace"+this.room.getNom(), ButtonType.OK);
            a.show();});

    */
        //
    }

    public LieuPlace(DirectionKey directionKey, Lieu room, boolean isReachable, int moveCost,Position p) {
        super(directionKey, isReachable, moveCost,p);

        this.room = room;
    }

    public LieuPlace(Lieu lieu, Position position) {
        this(lieu ,true, 0,position);
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
