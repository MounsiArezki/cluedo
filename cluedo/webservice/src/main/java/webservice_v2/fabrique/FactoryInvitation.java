package webservice_v2.fabrique;

import webservice_v2.entite.Invitation;
import webservice_v2.entite.User;

import java.util.List;

public class FactoryInvitation {

    private static FactoryInvitation facI = new FactoryInvitation();
    private static long lastId = 0L;

    private FactoryInvitation() { }

    public Invitation createInvitation(String idP, User hote, List<User> invites) {
        String id=String.valueOf(++lastId);
        return new Invitation(id, idP, hote, invites); }

    public static FactoryInvitation getFacI() { return facI; }
}
