package client.client.modele.entite.etat_partie;

import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

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
    public Joueur obtenirJoueurCourant() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public Joueur obtenirJoueurAtif() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public Map<TypeCarte, ICarte> obtenirHypothese() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public List<Integer> obtenirDes() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public List<ICarte> obtenirIndices() throws UnsupportedOperationException {
        return null;
    }

    @Override
    public String obtenirTexte() {
        String texte;
        if(gagnant==null){
            texte="Aucun des joueurs n'a accepté l'invitation, la partie n'a pas pu être lancée.";
        }
        else {
            texte=gagnant.toString()+" a gagné, le crimier était "+combinaisonGagnante.get(TypeCarte.PERSONNAGE).getNom()+" avec "+combinaisonGagnante.get(TypeCarte.ARME).getNom()+" dans "+combinaisonGagnante.get(TypeCarte.LIEU).getNom()+".";
        }
        return texte;
    }
}
