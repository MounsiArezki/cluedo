package client.client.modele.entite.etat_partie;


import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResolutionDes implements IEtatPartie {

    private Joueur joueurCourant;
    private List<Integer> des;

    public ResolutionDes() {
    }

    public ResolutionDes(Joueur joueurCourant, List<Integer> des) {
        this.joueurCourant = joueurCourant;
        this.des = des;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public String obtenirTexte() {
        String de1= des.get(0)==1 ? " une loupe " : "un "+des.get(0);
        String de2=des.get(1)==1 ? " une loupe " : "un "+des.get(1);
        return joueurCourant.toString()+" a obtenu "+de1+" et "+de2;
    }

    @Override
    public List<Actions> obtenirActionsPossibles() {
        return new ArrayList<>(List.of(Actions.DEPLACER, Actions.PIOCHER_INDICE));
    }
}
