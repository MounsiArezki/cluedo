package webservice_v2;

import webservice_v2.entite.User;

import java.util.List;

public class FactoryInvitation {

    private static FactoryInvitation facI = new FactoryInvitation();

    private FactoryInvitation() { }

    public Invitation createInvitation(String idP, User hote, List<User> invites) { return new Invitation(idP, hote, invites); }

    public static FactoryInvitation getFacI() { return facI; }
}
