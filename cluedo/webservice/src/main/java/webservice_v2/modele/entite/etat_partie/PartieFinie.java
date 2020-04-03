package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;

import java.util.List;
import java.util.Map;

public class PartieFinie implements IEtatPartie {

    private Joueur gagnant;
    private Map<TypeCarte, ICarte> combinaisonGagnante;

    public PartieFinie(Joueur gagnant, Map<TypeCarte, ICarte> combinaisonGagnante) {
        this.gagnant = gagnant;
        this.combinaisonGagnante = combinaisonGagnante;
    }


    @Override
    public IEtatPartie lancerDe(List<Integer> des) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie piocherIndice(List<ICarte> indices, List<Integer> des) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie deplacer() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
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
        return null;
    }

    @Override
    public Joueur getJoueurAtif() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public Map<TypeCarte, ICarte> getHypothese() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public List<Integer> getDes() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public List<ICarte> getIndices() throws UnsupportedOperationException {
        return null;
    }
}
