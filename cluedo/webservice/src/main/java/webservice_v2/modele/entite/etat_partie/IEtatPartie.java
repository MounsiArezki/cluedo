package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.entite.Joueur;

public abstract class IEtatPartie {

    protected Joueur joueurCourant;

    protected Joueur joueurActif;

    public IEtatPartie(Joueur joueurCourant, Joueur joueurActif){
        this.joueurCourant=joueurCourant;
        this.joueurActif=joueurActif;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    public Joueur getJoueurActif() {
        return joueurActif;
    }

    public void setJoueurActif(Joueur joueurActif) {
        this.joueurActif = joueurActif;
    }

    public abstract IEtatPartie next();
}
