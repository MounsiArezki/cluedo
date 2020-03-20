package webservice.webservice.modele.entite;

import java.util.List;

public class Invitation {

    private String id;
    private String idPartie;
    private String idHote;
    private List<String> invites;

    private static long lastId = 0L;

    public Invitation(String idPartie, String idHote, List<String> invites) {
        this.id = String.valueOf(++lastId);
        this.idPartie = idPartie;
        this.idHote = idHote;
        this.invites = invites;
    }

    public String getId() { return id; }

    public String getIdPartie() { return idPartie; }

    public String getIdHote() { return idHote; }

    public List<String> getInvites() { return invites; }
}
