package client.client.global;

import client.client.modele.entite.User;
import client.client.service.IProxyV2;
import client.client.service.ProxyV2;

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
