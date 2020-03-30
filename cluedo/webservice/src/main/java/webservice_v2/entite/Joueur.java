package webservice_v2.entite;

import webservice_v2.Position;
import webservice_v2.carte.ICarte;
import webservice_v2.carte.Personnage;
import webservice_v2.entite.User;

import java.util.List;

public class Joueur {

    User user;
    Personnage personnage;
    Position position;
    List<ICarte> listeCartes;

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

}
