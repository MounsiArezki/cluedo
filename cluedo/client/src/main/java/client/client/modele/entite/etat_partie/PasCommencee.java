package client.client.modele.entite.etat_partie;

public class PasCommencee extends IEtatPartie{

    public PasCommencee() {
        texte="En attente des joueurs";
    }

    @Override
    public IEtatPartie finir() {
        return new Finie();
    }

    @Override
    public IEtatPartie init() {
        return new Init();
    }
}
