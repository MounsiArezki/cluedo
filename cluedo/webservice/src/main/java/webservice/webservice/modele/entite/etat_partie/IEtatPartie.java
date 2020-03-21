package webservice.webservice.modele.entite.etat_partie;

import webservice.webservice.modele.entite.Joueur;

public abstract class IEtatPartie {

    protected Joueur joueurCourant;
    protected String texte;

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

    public abstract IEtatPartie finir();

    public abstract IEtatPartie init();
}
