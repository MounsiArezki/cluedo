package client.client.global;

import client.client.modele.entite.User;

public class VariablesGlobales {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        VariablesGlobales.user = user;
    }



}
