package client.client.vue.cluedoPlateau.player;

import client.client.modele.entite.carte.Personnage;
import client.client.vue.PlateauView;
import client.client.vue.place.LieuPlace;
import client.client.vue.place.Place;
import javafx.scene.control.Alert;

public class Player extends Character {

    public Player(PlateauView plat, Personnage personnage, Place departPlace) {
        super(plat, personnage, departPlace);
    }


    public void moveTo(Place place) {

        // If move is impossible, send impossible move message
        if(!posMoves.contains(place )  ) {
                plateau.showMessage("impossible move !", Alert.AlertType.WARNING);
            return;
        }else if (!isMY_TURN() ){
            plateau.showMessage("NOT YOUR TURN", Alert.AlertType.WARNING);
            return;
        }

        super.moveTo(place);
        delHighlightPosMoves();
    //    highlightPosMoves();
      //  MY_TURN =false;

    }

    @Override
    public void moveFromServer(Place place ) {
            super.moveTo(place);
    }

    public void guess(){

        // guess logic

    }


    public void passCard(String carte){
        System.out.println("envoie carte via web serv :"+carte);
    }



}
