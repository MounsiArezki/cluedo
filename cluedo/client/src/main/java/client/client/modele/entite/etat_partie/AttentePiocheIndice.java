package client.client.modele.entite.etat_partie;


import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

import java.util.List;
import java.util.Map;


public class AttentePiocheIndice implements IEtatPartie {


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
        return null;
    }

    @Override
    public List<Actions> obtenirActionsPossibles() {
        return null;
    }
}
