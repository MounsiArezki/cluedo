package webservice_v2.modele.fabrique;

import webservice_v2.modele.entite.User;

public class FactoryUser {

    private static FactoryUser facU = new FactoryUser();
    private static long lastId = 0L;

    private FactoryUser() { }

    public User createUser(String login, String pwd) {
        String id=String.valueOf(++lastId);
        return new User(id, login, pwd); }

    public static FactoryUser getFacU() { return facU; }
}
