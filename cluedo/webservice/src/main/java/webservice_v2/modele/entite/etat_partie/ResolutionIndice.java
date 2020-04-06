package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;

import java.util.List;
import java.util.Map;

public class ResolutionIndice implements IEtatPartie{

    private Joueur joueurCourant;
    private List<Integer> des;
    private List<ICarte> indices;

    public ResolutionIndice() {
    }

    public ResolutionIndice(Joueur joueurCourant, List<ICarte> indices, List<Integer> des) {
        this.joueurCourant = joueurCourant;
        this.des = des;
        this.indices = indices;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    public List<Integer> getDes() {
        return des;
    }

    public void setDes(List<Integer> des) {
        this.des = des;
    }

    public List<ICarte> getIndices() {
        return indices;
    }

    public void setIndices(List<ICarte> indices) {
        this.indices = indices;
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
        return des;
    }

    @Override
    public List<ICarte> obtenirIndices() throws UnsupportedOperationException {
        return indices;
    }

    @Override
    public void changerJoueurActif(Joueur actifSuivant) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
