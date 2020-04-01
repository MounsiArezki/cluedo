package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.entite.Joueur;

public class EnAttenteDesJoueurs extends IEtatPartie {


    public EnAttenteDesJoueurs(Joueur joueurCourant, Joueur joueurActif) {
        super(joueurCourant, joueurActif);
    }

    @Override
    public IEtatPartie next() {
        return new Initialisation(joueurCourant);
    }


}
