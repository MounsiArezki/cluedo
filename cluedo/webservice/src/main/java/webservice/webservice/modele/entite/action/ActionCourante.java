package webservice.webservice.modele.entite.action;

public abstract class ActionCourante {

    private ActionCourante suivante;

    public abstract ActionCourante jouer();

}
