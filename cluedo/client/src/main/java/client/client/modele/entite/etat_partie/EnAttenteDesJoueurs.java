package client.client.modele.entite.etat_partie;

import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

import java.util.List;
import java.util.Map;

public class EnAttenteDesJoueurs implements IEtatPartie {

    public EnAttenteDesJoueurs() {
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
    public String obtenirTexte() {
        return "En attente, les joueurs doivent répondre à l'invitation";
    }
}
