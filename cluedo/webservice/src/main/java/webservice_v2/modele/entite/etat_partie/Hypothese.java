package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;

import java.util.List;
import java.util.Map;

public class Hypothese implements IEtatPartie {

    private Joueur joueurCourant;
    private Joueur joueurActif;
    private Map<TypeCarte, ICarte> hypothese;

    public Hypothese(Joueur joueurCourant, Joueur joueurActif, Map<TypeCarte, ICarte> hypothese) {
        this.joueurCourant = joueurCourant;
        this.joueurActif = joueurActif;
        this.hypothese = hypothese;
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
        return new Hypothese(joueurCourant, joueurActif, hypothese);
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
        return new FinTour(joueurCourant);
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
        return joueurActif;
    }

    @Override
    public Map<TypeCarte, ICarte> getHypothese() throws UnsupportedOperationException {
        return hypothese;
    }

    @Override
    public List<Integer> getDes() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ICarte> getIndices() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
