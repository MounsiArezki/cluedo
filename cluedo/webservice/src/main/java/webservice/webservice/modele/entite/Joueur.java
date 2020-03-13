package webservice.webservice.modele.entite;

import webservice.webservice.modele.entite.action.ActionCourante;
import webservice.webservice.modele.entite.action.LancerDe;
import webservice.webservice.modele.entite.carte.ICarte;
import webservice.webservice.modele.entite.carte.Personnage;

import java.util.List;

public class Joueur {

    User user;
    Personnage personnage;
    Position position;
    List<ICarte> listeCartes;

    ActionCourante actionCourante;

    public void jouer(){
        actionCourante.jouer();
    }

    public void commencerTour(){
        if(actionCourante==null){
            actionCourante=new LancerDe();
        }
    }


}
