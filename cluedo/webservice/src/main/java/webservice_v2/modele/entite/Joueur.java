package webservice_v2.modele.entite;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.util.Pair;
import webservice_v2.modele.entite.carte.ICarte;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Joueur {

    User user;
    ICarte personnage;
    Position position;

    @JsonIgnore
    List<ICarte> listeCartes;

    @JsonIgnore
    Set<Pair<ICarte,Joueur>> ficheEnquete;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return Objects.equals(user, joueur.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, personnage, position, listeCartes);
    }
}
