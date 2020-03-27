package client.client.vue.cluedoPlateau.player;

import client.client.modele.entite.Personnage;
import client.client.vue.Plateau;
import client.client.vue.place.Place;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

public class Player extends Character {


    public Player(Plateau plat, Personnage personnage, Place departPlace) {
        super(plat, personnage, departPlace);
    }





    @Override
    public void moveTo(Place place) {

        // If move is impossible, send impossible move message
        if(!posMoves.contains(place) ) {
                plateau.showMessage("impossible move !", Alert.AlertType.WARNING);
            return;
        }else if (!isMY_TURN() ){
            plateau.showMessage("NOT YOUR TURN", Alert.AlertType.WARNING);
            return;
        }

        delHighlightPosMoves();

        super.moveTo(place);

        highlightPosMoves();
        MY_TURN =false;

    }


}
