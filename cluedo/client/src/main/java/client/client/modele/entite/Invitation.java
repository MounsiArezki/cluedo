package client.client.modele.entite;

import java.util.List;

public class Invitation {

    private String id;
    private String idPartie;
    private User hote;
    private List<User> invites;

    public Invitation() {
    }

    public Invitation(User hote, List<User> invites) {
        this.hote = hote;
        this.invites = invites;
    }

    public Invitation(String idPartie, User hote) {
        this.idPartie = idPartie;
        this.hote = hote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public User getHote() {
        return hote;
    }

    public void setHote(User hote) {
        this.hote = hote;
    }

    public String getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(String idPartie) {
        this.idPartie = idPartie;
    }

    public List<User> getInvites() {
        return invites;
    }

    public void setInvites(List<User> invites) {
        this.invites = invites;
    }
}
