package client.client.global;

import client.client.modele.entite.User;
import client.client.service.IProxyV2;
import client.client.service.ProxyV2;

public class VariablesGlobales {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        VariablesGlobales.user = user;
    }


    private static IProxyV2 proxyV2;

    public static IProxyV2 getProxyV2() {
        if (proxyV2 ==null){
            return new ProxyV2();
        }
        return proxyV2;
    }

}
