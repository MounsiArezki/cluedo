package client.client;

import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.exception.connexionException.NonInscritException;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.User;
import client.client.service.Facade;
import client.client.service.IUserService;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PartialTest {



    private Facade proxyV2;

    @Before
    public void initialisation(){
        this.proxyV2 = new Facade();
    }


    @Test
    public void testGetAllInvitationsEmisesOk() throws InterruptedException, MdpIncorrectOuNonInscritException, IOException, DejaInscritException, DejaConnecteException {
        proxyV2.inscription("user","user");
       User u = proxyV2.connexion("user","user");
        VariablesGlobales.setUser(u);
        proxyV2.getAllInvitationsEmises();
    }
    @Test
    public void testDeconnexionOk() throws InterruptedException, MdpIncorrectOuNonInscritException, IOException, DejaInscritException, NonInscritException, DejaConnecteException {
        proxyV2.inscription("user","user");
        User u = proxyV2.connexion("user","user");
        VariablesGlobales.setUser(u);


    }
}
