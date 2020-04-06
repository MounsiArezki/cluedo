package client.client;

import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.exception.connexionException.NonInscritException;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.User;
import client.client.service.Facade;
import client.client.service.IProxyV2;
import client.client.service.IUserService;
import client.client.service.ProxyV2;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class PartialTest {



    private IProxyV2 proxyV2;

    @Before
    public void initialisation(){
        this.proxyV2 = new ProxyV2();
    }

    @Test
    public void testGetAllInvitationsRecuesOk() throws InterruptedException, MdpIncorrectOuNonInscritException, IOException, DejaInscritException {
        proxyV2.insciption("user","user");
       User u = proxyV2.connexion("user","user");
        VariablesGlobales.setUser(u);
        System.out.println(u.getId());
        proxyV2.getAllInvitationsRecues();
    }

    @Test
    public void testGetAllInvitationsEmisesOk() throws InterruptedException, MdpIncorrectOuNonInscritException, IOException, DejaInscritException {
        proxyV2.insciption("user","user");
       User u = proxyV2.connexion("user","user");
        VariablesGlobales.setUser(u);
        proxyV2.getAllInvitationsEmises();
    }
    @Test
    public void testDeconnexionOk() throws InterruptedException, MdpIncorrectOuNonInscritException, IOException, DejaInscritException, NonInscritException {
        proxyV2.insciption("user","user");
        User u = proxyV2.connexion("user","user");
        VariablesGlobales.setUser(u);

        proxyV2.deconnexion();
    }
}
