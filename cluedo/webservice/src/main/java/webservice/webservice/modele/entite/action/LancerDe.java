package webservice.webservice.modele.entite.action;

import webservice.webservice.modele.entite.action.ActionCourante;

public class LancerDe extends ActionCourante {

    @Override
    public ActionCourante jouer() {
        return new Deplacer();
    }
}
