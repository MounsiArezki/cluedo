package client.client.modele.entite;

import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.Personnage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Joueur  {

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


    @Override
    public String toString() {
        return personnage.toString()+" ("+user.toString()+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return user.equals(joueur.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
