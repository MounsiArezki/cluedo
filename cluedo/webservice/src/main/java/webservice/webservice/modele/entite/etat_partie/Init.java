package webservice.webservice.modele.entite.etat_partie;

public class Init extends IEtatPartie {

    public Init() {
        texte="La partie commence";
    }

    @Override
    public IEtatPartie finir() {
        return null;
    }

    @Override
    public IEtatPartie init() {
        return null;
    }
}
