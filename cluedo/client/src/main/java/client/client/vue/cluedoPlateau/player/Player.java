package client.client.vue.cluedoPlateau.player;

import client.client.modele.entite.carte.Personnage;
import client.client.vue.PlateauView;
import client.client.vue.cluedoPlateau.player.PlayerException.ImpossibleDeplacementException;
import client.client.vue.place.Place;
import javafx.scene.control.Alert;

public class Player extends Character {

    public Player(PlateauView plat, Personnage personnage, Place departPlace) {
        super(plat, personnage, departPlace);
    }


    public void moveTo(Place place) throws ImpossibleDeplacementException {

        // If move is impossible, send impossible move message
        if(!posMoves.contains(place ) || !moveAuthorisation   ) {

                plateau.showMessage("impossible move !", Alert.AlertType.WARNING);
            throw new ImpossibleDeplacementException();
        }
        System.out.println("not move excep");
        super.moveTo(place);
        delHighlightPosMoves();
    //    highlightPosMoves();
      //  MY_TURN =false;

    }

    @Override
    public void moveFromServer(Place place )  {
            super.moveFromServer(place);
    }

    public void guess(){

        // guess logic

    }


    public void passCard(String carte){
        System.out.println("envoie carte via web serv :"+carte);
    }



}
