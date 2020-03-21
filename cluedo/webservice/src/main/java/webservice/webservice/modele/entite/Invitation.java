package webservice.webservice.modele.entite;

import java.util.List;

public class Invitation {

    private String id;
    private String idPartie;
    private User hote;
    private List<User> invites;

    private static long lastId = 0L;

    public Invitation(String idPartie, User hote, List<User> invites) {
        this.id = String.valueOf(++lastId);
        this.idPartie = idPartie;
        this.hote = hote;
        this.invites = invites;
    }

    public String getId() { return id; }

    public String getIdPartie() { return idPartie; }

    public void setId(String id) {
        this.id = id;
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

    public void setInvites(List<User> invites) {
        this.invites = invites;
    }

    public List<User> getInvites() { return invites; }
}
