package webservice.webservice.modele.entite.action;

public class FaireHypothese extends ActionCourante {

    @Override
    public ActionCourante jouer() {
        return new Accuser();
    }
}
