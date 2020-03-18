package webservice.webservice.modele.fabrique;

import webservice.webservice.modele.entite.Invitation;

import java.util.List;

public class FactoryInvitation {

    private static FactoryInvitation facI = new FactoryInvitation();

    private FactoryInvitation() { }

    public Invitation createInvitation(long idP, long idH, List<Long> joueurs) { return new Invitation(idP, idH, joueurs); }

    public static FactoryInvitation getFacI() { return facI; }
}
