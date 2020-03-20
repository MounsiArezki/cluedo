package webservice.webservice.modele.fabrique;

import webservice.webservice.modele.entite.Invitation;

import java.util.List;

public class FactoryInvitation {

    private static FactoryInvitation facI = new FactoryInvitation();

    private FactoryInvitation() { }

    public Invitation createInvitation(String idP, String idH, List<String> joueurs) { return new Invitation(idP, idH, joueurs); }

    public static FactoryInvitation getFacI() { return facI; }
}
