package webservice.webservice.modele.fabrique;

import webservice.webservice.modele.entite.Partie;
import webservice.webservice.modele.entite.User;

public class FactoryPartie {

    private static FactoryPartie facP = new FactoryPartie();

    private FactoryPartie() { }

    public Partie createPartie(User u) { return new Partie(u); }

    public static FactoryPartie getFacP() { return facP; }
}
