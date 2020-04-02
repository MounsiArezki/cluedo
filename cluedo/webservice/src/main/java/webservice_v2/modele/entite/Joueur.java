package webservice_v2.modele.entite;


import webservice_v2.modele.entite.carte.ICarte;

import java.util.ArrayList;
import java.util.List;

public class Joueur {

    User user;
    ICarte personnage;
    Position position;
    List<ICarte> listeCartes;

    public Joueur() {
    }

    public Joueur(User user) {
        this.user = user;
        listeCartes=new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ICarte getPersonnage() {
        return personnage;
    }

    public void setPersonnage(ICarte personnage) {
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

    public void ajouterCarte(ICarte carte){
        listeCartes.add(carte);
    }

}
