package webservice.webservice.modele.entite;

import java.util.List;

public class Invitation {

    private long id;
    private long idPartie;
    private long idHote;
    private List<Long> invites;

    private static long lastId = 0L;

    public Invitation(long idPartie, long idHote, List<Long> invites) {
        this.id = ++lastId;
        this.idPartie = idPartie;
        this.idHote = idHote;
        this.invites = invites;
    }

    public long getId() { return id; }

    public long getIdPartie() { return idPartie; }

    public long getIdHote() { return idHote; }

    public List<Long> getInvites() { return invites; }
}
