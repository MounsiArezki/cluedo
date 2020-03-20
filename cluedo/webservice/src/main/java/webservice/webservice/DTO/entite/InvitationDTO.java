package webservice.webservice.DTO.entite;

import webservice.webservice.modele.entite.Invitation;
import java.util.List;

public class InvitationDTO {

    private String id;
    private String idPartie;
    private String idHote;
    private List<String> invites;

    public InvitationDTO(){}

    public static InvitationDTO creer(Invitation invitation) {
        InvitationDTO invitationDTO = new InvitationDTO();
        invitationDTO.setId(invitation.getId());
        invitationDTO.setIdPartie(invitation.getIdPartie());
        invitationDTO.setIdHote(invitation.getIdHote());
        invitationDTO.setInvites(invitation.getInvites());
        return invitationDTO;
    }

    public String getId() { return id; }

    public String getIdPartie() { return idPartie; }

    public String getIdHote() { return idHote; }

    public List<String> getInvites() { return invites; }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdPartie(String idPartie) {
        this.idPartie = idPartie;
    }

    public void setIdHote(String idHote) {
        this.idHote = idHote;
    }

    public void setInvites(List<String> invites) {
        this.invites = invites;
    }
}
