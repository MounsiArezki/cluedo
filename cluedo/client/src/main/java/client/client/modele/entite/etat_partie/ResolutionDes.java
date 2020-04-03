package client.client.modele.entite.etat_partie;



import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

import java.util.List;
import java.util.Map;

public class ResolutionDes implements IEtatPartie {

    private Joueur joueurCourant;
    private List<Integer> des;

    public ResolutionDes(Joueur joueurCourant, List<Integer> des) {
        this.joueurCourant = joueurCourant;
        this.des = des;
    }

    @Override
    public IEtatPartie lancerDe(Joueur joueurCourant, List<Integer> des) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie piocherIndice(Joueur jouerCourant, List<ICarte> indices, List<Integer> des) throws UnsupportedOperationException {
        return new ResolutionIndice(jouerCourant, indices, des);
    }

    @Override
    public IEtatPartie deplacer(Joueur joueurCourant) throws UnsupportedOperationException {
        return new Supputation(joueurCourant);
    }

    @Override
    public IEtatPartie revelerCarte(Joueur joueurActif) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie passerRevelerCarte(Joueur joueurActif) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie faireHypothese(Joueur joueurCourant, Joueur joueurActif, Map<TypeCarte, ICarte> hypothese) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie resoudreHypothese(Joueur joueurCourant) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie debuterTour(Joueur joueurSuivant) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie finirPartie(Joueur gagnant, Map<TypeCarte, ICarte> combinaisonGagante) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

}
