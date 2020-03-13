package webservice.webservice.modele.entite.action;

public class Deplacer extends ActionCourante {
    @Override
    public ActionCourante jouer() {
        return new JouerCarte();
    }
}
