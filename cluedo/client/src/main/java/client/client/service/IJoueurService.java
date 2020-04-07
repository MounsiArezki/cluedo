package client.client.service;

import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;

import java.util.Map;

public interface IJoueurService {

    public ICarte[] getCartesJoueurs(String idPartie);
    public Map<ICarte, Joueur> getFicheJoueur(String idPartie);

}
