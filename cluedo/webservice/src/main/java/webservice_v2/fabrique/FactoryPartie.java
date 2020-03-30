package webservice_v2.fabrique;

import webservice_v2.entite.Partie;
import webservice_v2.entite.User;

public class FactoryPartie {

    private static FactoryPartie facP = new FactoryPartie();
    private static long lastId = 0L;

    private FactoryPartie() { }

    public Partie createPartie(User u) {
        String id=String.valueOf(++lastId);
        return new Partie(id, u);
    }

    public static FactoryPartie getFacP() { return facP; }
}
