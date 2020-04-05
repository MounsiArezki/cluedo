package client.client.modele.entite.etat_partie;

import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hypothese implements IEtatPartie {

    private Joueur joueurCourant;
    private Joueur joueurActif;
    private Map<TypeCarte, ICarte> hypothese;

    public Hypothese(){}

    public Hypothese(Joueur joueurCourant, Joueur joueurActif, Map<TypeCarte, ICarte> hypothese) {
        this.joueurCourant = joueurCourant;
        this.joueurActif = joueurActif;
        this.hypothese = hypothese;
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

    public Map<TypeCarte, ICarte> getHypothese() {
        return hypothese;
    }

    public void setHypothese(Map<TypeCarte, ICarte> hypothese) {
        this.hypothese = hypothese;
    }


    @Override
    public Joueur obtenirJoueurCourant() throws UnsupportedOperationException {
        return joueurCourant;
    }

    @Override
    public Joueur obtenirJoueurAtif() throws UnsupportedOperationException {
        return joueurActif;
    }

    @Override
    public Map<TypeCarte, ICarte> obtenirHypothese() throws UnsupportedOperationException {
        return hypothese;
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
    public String obtenirTexte() {
        return joueurCourant.toString()+" a fait l'hypothèse : le crimier est "+hypothese.get(TypeCarte.PERSONNAGE).getNom()+" avec "+hypothese.get(TypeCarte.ARME).getNom()+" dans "+hypothese.get(TypeCarte.LIEU).getNom()
                +". "+joueurActif.toString()+" doit dévoiler une carte." ;
    }

    @Override
    public List<Actions> obtenirActionsPossibles() {
        return new ArrayList<>(List.of(Actions.REVELER_CARTE));
    }
}
