package webservice.webservice.modele.fabrique;

import webservice.webservice.modele.entite.Invitation;
import webservice.webservice.modele.entite.User;

import java.util.List;

public class FactoryInvitation {

    private static FactoryInvitation facI = new FactoryInvitation();

    private FactoryInvitation() { }

    public Invitation createInvitation(String idP, String idH, List<User> invites) { return new Invitation(idP, idH, invites); }

    public static FactoryInvitation getFacI() { return facI; }
}
