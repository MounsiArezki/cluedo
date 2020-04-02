package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.carte.ICarte;
import webservice_v2.modele.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;

import java.util.List;
import java.util.Map;

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
