package webservice_v2;

import webservice_v2.entite.User;

public class FactoryPartie {

    private static FactoryPartie facP = new FactoryPartie();

    private FactoryPartie() { }

    public Partie createPartie(User u) { return new Partie(u); }

    public static FactoryPartie getFacP() { return facP; }
}
