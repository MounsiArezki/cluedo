package webservice.webservice.modele.fabrique;

import webservice.webservice.modele.entite.User;

public class FactoryUser {

    private static FactoryUser facU = new FactoryUser();

    private FactoryUser() { }

    public User createUser(String login, String pwd) { return new User(login, pwd); }

    public static FactoryUser getFacU() { return facU; }
}
