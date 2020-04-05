package client.client.modele.entite.etat_partie;


import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

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
    public String getTexte() {
        return joueurCourant.toString()+" a tiré "+indices.size()+ "carte indice";
    }
}
