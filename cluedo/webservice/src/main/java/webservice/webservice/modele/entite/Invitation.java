package webservice.webservice.modele.entite;

import java.util.List;

public class Invitation {

    String idPartie;
    User hote;
    List<User> personnesInvitees;

    public Invitation(String idPartie, User hote, List<User> personnesInvitees) {
        this.idPartie = idPartie;
        this.hote = hote;
        this.personnesInvitees = personnesInvitees;
    }

    public String getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(String idPartie) {
        this.idPartie = idPartie;
    }

    public User getHote() {
        return hote;
    }

    public void setHote(User hote) {
        this.hote = hote;
    }

    public List<User> getPersonnesInvitees() {
        return personnesInvitees;
    }

    public void setPersonnesInvitees(List<User> personnesInvitees) {
        this.personnesInvitees = personnesInvitees;
    }
}
