package client.client.modele.entite.etat_partie;

public class PasCommencee extends IEtatPartie{

    public PasCommencee() {
        texte="En attente des joueurs";
    }

    @Override
    public IEtatPartie next() {
        return null;
    }
}
