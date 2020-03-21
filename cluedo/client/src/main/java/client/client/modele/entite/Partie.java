package client.client.modele.entite;

import client.client.modele.entite.etat_partie.IEtatPartie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Partie {
    private String id;

    private User hote;

    private HashMap<String, Joueur> joueurs;

    private IEtatPartie etatPartie;

    public Partie() {
    }

    public String getId() { return id; }

    public User getHote() { return hote; }

    public HashMap<String, Joueur> getJoueurs() { return joueurs; }

    public void setId(String id) {
        this.id = id;
    }

    public void setHote(User hote) {
        this.hote = hote;
    }

    public void setJoueurs(HashMap<String, Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public IEtatPartie getEtatPartie() {
        return etatPartie;
    }

    public void setEtatPartie(IEtatPartie etatPartie) {
        this.etatPartie = etatPartie;
    }
}
