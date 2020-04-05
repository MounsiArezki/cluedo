package client.client.modele.entite.etat_partie;

import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

import java.util.List;
import java.util.Map;

public class DebutTour implements IEtatPartie {

    private Joueur joueurCourant;

    public DebutTour(){}

    public DebutTour(Joueur joueurCourant) {
        this.joueurCourant=joueurCourant;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
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
    public String getTexte() {
        return "Début du tour de "+joueurCourant.toString();
    }

}
