package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;

import java.util.List;
import java.util.Map;

public class RevelationIndice implements IEtatPartie {

    private Joueur joueurCourant;
    private Joueur joueurActif;
    private ICarte carte;

    public RevelationIndice() { }

    public RevelationIndice(Joueur joueurCourant, Joueur joueurActif, ICarte carte) {
        this.joueurCourant = joueurCourant;
        this.joueurActif = joueurActif;
        this.carte = carte;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    public Joueur getJoueurActif() { return joueurActif; }

    public void setJoueurActif(Joueur joueurActif) { this.joueurActif = joueurActif; }

    public ICarte getCarte() { return carte; }

    public void setCarte(ICarte carte) { this.carte = carte; }

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
        return new Supputation(joueurCourant);
    }

    @Override
    public IEtatPartie revelerCarte(Joueur joueurActif) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie attentePiocheIndice(Joueur joueurCourant, List<Integer> des) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie revelationIndice(Joueur joueurCourant, Joueur joueurActif, ICarte carte) throws UnsupportedOperationException {
        return new RevelationIndice(joueurCourant, joueurActif, carte);
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
    public Joueur obtenirJoueurCourant() throws UnsupportedOperationException {
        return joueurCourant;
    }

    @Override
    public Joueur obtenirJoueurAtif() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<TypeCarte, ICarte> obtenirHypothese() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Integer> obtenirDes() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ICarte> obtenirIndices() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changerJoueurActif(Joueur actifSuivant) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

}
