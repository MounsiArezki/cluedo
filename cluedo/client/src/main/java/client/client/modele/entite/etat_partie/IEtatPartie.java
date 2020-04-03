package client.client.modele.entite.etat_partie;


import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IEtatPartie {

    public IEtatPartie lancerDe(Joueur joueurCourant, List<Integer> des) throws UnsupportedOperationException;

    public IEtatPartie piocherIndice(Joueur jouerCourant, List<ICarte> indices, List<Integer> des) throws UnsupportedOperationException;

    public IEtatPartie deplacer(Joueur joueurCourant) throws UnsupportedOperationException;

    public IEtatPartie revelerCarte(Joueur joueurActif) throws UnsupportedOperationException;

    public IEtatPartie passerRevelerCarte(Joueur joueurActif) throws UnsupportedOperationException;

    public IEtatPartie faireHypothese(Joueur joueurCourant, Joueur joueurActif, Map<TypeCarte, ICarte> hypothese) throws UnsupportedOperationException;

    public IEtatPartie resoudreHypothese(Joueur joueurCourant) throws UnsupportedOperationException;

    public IEtatPartie debuterTour(Joueur joueurSuivant) throws UnsupportedOperationException;

    public IEtatPartie finirPartie(Joueur gagnant, Map<TypeCarte, ICarte> combinaisonGagante) throws UnsupportedOperationException;

}
