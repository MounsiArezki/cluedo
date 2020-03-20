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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<ICarte> getListeCartes() {
        return listeCartes;
    }

    public void setListeCartes(List<ICarte> listeCartes) {
        this.listeCartes = listeCartes;
    }

    public ActionCourante getActionCourante() {
        return actionCourante;
    }

    public void setActionCourante(ActionCourante actionCourante) {
        this.actionCourante = actionCourante;
    }
}
