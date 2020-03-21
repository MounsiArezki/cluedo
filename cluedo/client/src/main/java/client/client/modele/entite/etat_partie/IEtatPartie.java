package client.client.modele.entite.etat_partie;


import client.client.modele.entite.Joueur;

public abstract class IEtatPartie {

    protected Joueur joueurCourant;
    protected String texte;

    public abstract IEtatPartie next();

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
