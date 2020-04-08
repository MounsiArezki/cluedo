package client.client.service;

import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;

import java.util.List;
import java.util.Map;

public interface IJoueurService {

    public List<ICarte> getCartesJoueurs(String idPartie);
    public Map<ICarte, Joueur> getFicheJoueur(String idPartie);

}
