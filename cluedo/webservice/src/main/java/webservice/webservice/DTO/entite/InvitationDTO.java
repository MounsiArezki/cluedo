package webservice.webservice.DTO.entite;

import webservice.webservice.modele.entite.Invitation;
import java.util.List;

public class InvitationDTO {

    private long id;
    private long idPartie;
    private long idHote;
    private List<Long> invites;

    public InvitationDTO(){}

    public static InvitationDTO creer(Invitation invitation) {
        InvitationDTO invitationDTO = new InvitationDTO();
        invitationDTO.setId(invitation.getId());
        invitationDTO.setIdPartie(invitation.getIdPartie());
        invitationDTO.setIdHote(invitation.getIdHote());
        invitationDTO.setInvites(invitation.getInvites());
        return invitationDTO;
    }

    public long getId() { return id; }

    public long getIdPartie() { return idPartie; }

    public long getIdHote() { return idHote; }

    public List<Long> getInvites() { return invites; }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdPartie(long idPartie) {
        this.idPartie = idPartie;
    }

    public void setIdHote(long idHote) {
        this.idHote = idHote;
    }

    public void setInvites(List<Long> invites) {
        this.invites = invites;
    }
}
