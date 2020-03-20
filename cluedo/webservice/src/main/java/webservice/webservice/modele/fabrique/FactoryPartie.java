package webservice.webservice.modele.fabrique;

import webservice.webservice.modele.entite.Partie;

public class FactoryPartie {

    private static FactoryPartie facP = new FactoryPartie();

    private FactoryPartie() { }

    public Partie createPartie(String idH) { return new Partie(idH); }

    public static FactoryPartie getFacP() { return facP; }
}
