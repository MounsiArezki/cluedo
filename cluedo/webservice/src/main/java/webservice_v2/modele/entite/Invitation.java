package webservice_v2.modele.entite;

import java.util.List;
import java.util.Objects;

public class Invitation {

    private String id;
    private String idPartie;
    private User hote;
    private List<User> invites;

    public Invitation(String id, String idPartie, User hote, List<User> invites) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invitation that = (Invitation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
