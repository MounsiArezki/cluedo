package client.client.vue.cluedoPlateau.player;

import client.client.modele.entite.Personnage;
import client.client.vue.Plateau;
import client.client.vue.place.Place;

public class Player extends Character {


    public Player(Plateau plat, Personnage personnage, Place departPlace) {
        super(plat, personnage, departPlace);
    }

    //  lancer dés
    public int lancerDes() {
        int NumTmp = 0;
        for(int i = 0; i < DES_NUM; i++)
            NumTmp += Math.random() * 6 + 1;
        this.lancerNum = NumTmp;

        return lancerNum;
    }


    @Override
    public void moveTo(Place place, boolean forceMove) {

    }
}
