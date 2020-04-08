package client.client;

import client.client.modele.entite.Partie;
import client.client.modele.entite.User;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

    public static void main(String[] args) throws IOException {
        ObjectMapper o= new ObjectMapper();

        Partie p=new Partie("1", new User("1","la","la"));

        String ps= "{\"id\":\"4\",\"hote\":{\"id\":\"2\",\"pseudo\":\"la\",\"pwd\":\"la\"},\"joueurs\":{\"2\":{\"user\":{\"id\":\"2\",\"pseudo\":\"la\",\"pwd\":\"la\"},\"personnage\":null,\"position\":null,\"listeCartes\":[]}},\"ordres\":null,\"combinaisonGagante\":{},\"etatPartie\":{\"@type\":\"EnAttenteDesJoueurs\"},\"indices\":[],\"logs\":[]}";
        o.readValue(ps, Partie.class);
    }
}
