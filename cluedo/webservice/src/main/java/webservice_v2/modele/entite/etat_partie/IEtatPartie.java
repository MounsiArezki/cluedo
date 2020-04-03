package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;

import java.util.List;
import java.util.Map;

public interface IEtatPartie {

    public IEtatPartie lancerDe(List<Integer> des) throws UnsupportedOperationException;

    public IEtatPartie piocherIndice(List<ICarte> indices, List<Integer> des) throws UnsupportedOperationException;

    public IEtatPartie deplacer() throws UnsupportedOperationException;

    public IEtatPartie revelerCarte(Joueur joueurActif) throws UnsupportedOperationException;

    public IEtatPartie passerRevelerCarte(Joueur joueurActif) throws UnsupportedOperationException;

    public IEtatPartie faireHypothese(Joueur joueurActif, Map<TypeCarte, ICarte> hypothese) throws UnsupportedOperationException;

    public IEtatPartie resoudreHypothese() throws UnsupportedOperationException;

    public IEtatPartie debuterTour(Joueur joueurSuivant) throws UnsupportedOperationException;

    public IEtatPartie finirPartie(Joueur gagnant, Map<TypeCarte, ICarte> combinaisonGagante) throws UnsupportedOperationException;

    public Joueur getJoueurCourant() throws UnsupportedOperationException;

    public Joueur getJoueurAtif() throws UnsupportedOperationException;

    public Map<TypeCarte, ICarte> getHypothese() throws UnsupportedOperationException;

    public List<Integer> getDes() throws UnsupportedOperationException;

    public List<ICarte> getIndices() throws UnsupportedOperationException;


}
