package webservice_v2.modele.entite.etat_partie;

import webservice_v2.modele.carte.ICarte;
import webservice_v2.modele.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.Partie;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Map;

public class EnAttenteDesJoueurs implements IEtatPartie {


    public EnAttenteDesJoueurs() {
    }

    @Override
    public IEtatPartie lancerDe(Joueur joueurCourant, List<Integer> des) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie piocherIndice(Joueur jouerCourant, List<ICarte> indices, List<Integer> des) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie deplacer(Joueur joueurCourant) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie revelerCarte(Joueur joueurActif) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie passerRevelerCarte(Joueur joueurActif) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie faireHypothese(Joueur joueurCourant, Joueur joueurActif, Map<TypeCarte, ICarte> hypothese) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie resoudreHypothese(Joueur joueurCourant) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public IEtatPartie debuterTour(Joueur joueurSuivant) throws UnsupportedOperationException {
        return new DebutTour(joueurSuivant);
    }

    @Override
    public IEtatPartie finirPartie(Joueur gagnant, Map<TypeCarte, ICarte> combinaisonGagante) throws UnsupportedOperationException {
        return new PartieFinie(gagnant, combinaisonGagante);
    }
}
