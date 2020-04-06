package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;

import java.util.List;
import java.util.Map;

public class PartieFinie implements IEtatPartie {

    private Joueur gagnant;
    private Map<TypeCarte, ICarte> combinaisonGagnante;

    public PartieFinie(){}

    public PartieFinie(Joueur gagnant, Map<TypeCarte, ICarte> combinaisonGagnante) {
        this.gagnant = gagnant;
        this.combinaisonGagnante = combinaisonGagnante;
    }

    public Joueur getGagnant() {
        return gagnant;
    }

    public void setGagnant(Joueur gagnant) {
        this.gagnant = gagnant;
    }

    public Map<TypeCarte, ICarte> getCombinaisonGagnante() {
        return combinaisonGagnante;
    }

    public void setCombinaisonGagnante(Map<TypeCarte, ICarte> combinaisonGagnante) {
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
    public Joueur obtenirJoueurCourant() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
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
