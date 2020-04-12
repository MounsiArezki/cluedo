package client.client.global;

import client.client.modele.entite.User;

public class VariablesGlobales {

    private static User user;

    private static String idPartie;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        VariablesGlobales.user = user;
    }

    public static String getIdPartie() {
        return idPartie;
    }

    public static void setIdPartie(String idPartie) {
        VariablesGlobales.idPartie = idPartie;
    }
}
