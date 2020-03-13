package webservice.webservice.modele.entite.action;

public class JouerCarte extends ActionCourante {
    @Override
    public ActionCourante jouer() {
        return new FaireHypothese();
    }
}
