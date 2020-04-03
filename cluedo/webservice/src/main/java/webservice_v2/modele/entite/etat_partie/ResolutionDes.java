package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;

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
    public IEtatPartie lancerDe(List<Integer> des) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie piocherIndice(List<ICarte> indices, List<Integer> des) throws UnsupportedOperationException {
        return new ResolutionIndice(joueurCourant, indices, des);
    }

    @Override
    public IEtatPartie deplacer() throws UnsupportedOperationException {
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
    public IEtatPartie faireHypothese(Joueur joueurActif, Map<TypeCarte, ICarte> hypothese) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie resoudreHypothese() throws UnsupportedOperationException {
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

    @Override
    public Joueur getJoueurCourant() throws UnsupportedOperationException {
        return joueurCourant;
    }


    @Override
    public Joueur getJoueurAtif() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<TypeCarte, ICarte> getHypothese() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Integer> getDes() throws UnsupportedOperationException {
        return des;
    }


    @Override
    public List<ICarte> getIndices() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
