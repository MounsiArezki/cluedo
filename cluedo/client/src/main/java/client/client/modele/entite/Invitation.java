package client.client.modele.entite;

import java.util.List;

public class Invitation {
    private String idPartie;

    public User getHote() {
        return hote;
    }

    public void setHote(User hote) {
        this.hote = hote;
    }

    private User hote;
    private List<User> personnesInvitees;

    public Invitation(String idPartie, User hote, List<User> personnesInvitees) {
        this.idPartie = idPartie;
        this.hote = hote;
        this.personnesInvitees = personnesInvitees;
    }
    public Invitation(String idPartie, User hote) {
        this.idPartie = idPartie;
        this.hote = hote;

    }

    public String getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(String idPartie) {
        this.idPartie = idPartie;
    }



    public List<User> getPersonnesInvitees() {
        return personnesInvitees;
    }

    public void setPersonnesInvitees(List<User> personnesInvitees) {
        this.personnesInvitees = personnesInvitees;
    }
}
