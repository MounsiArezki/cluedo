package webservice.webservice.DTO.entite;

import webservice.webservice.modele.entite.Joueur;
import webservice.webservice.modele.entite.Position;
import webservice.webservice.modele.entite.User;
import webservice.webservice.modele.entite.action.ActionCourante;
import webservice.webservice.modele.entite.action.LancerDe;
import webservice.webservice.modele.entite.carte.ICarte;
import webservice.webservice.modele.entite.carte.Personnage;

import java.util.List;

public class JoueurDTO {

    User user;
    Personnage personnage;
    Position position;
    List<ICarte> listeCartes;
    ActionCourante actionCourante;

    public JoueurDTO(){}

    public static JoueurDTO creer(Joueur joueur){
        JoueurDTO joueurDTO = new JoueurDTO();
        joueurDTO.setUser(joueur.getUser());
        joueurDTO.setPersonnage(joueur.getPersonnage());
        joueurDTO.setPosition(joueur.getPosition());
        joueurDTO.setListeCartes(joueur.getListeCartes());
        joueurDTO.setActionCourante(joueur.getActionCourante());
        return joueurDTO;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<ICarte> getListeCartes() {
        return listeCartes;
    }

    public void setListeCartes(List<ICarte> listeCartes) {
        this.listeCartes = listeCartes;
    }

    public ActionCourante getActionCourante() {
        return actionCourante;
    }

    public void setActionCourante(ActionCourante actionCourante) {
        this.actionCourante = actionCourante;
    }
}
