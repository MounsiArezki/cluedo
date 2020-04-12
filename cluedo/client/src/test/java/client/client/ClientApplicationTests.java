package client.client;


import client.client.config.ServiceConfig;
import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.modele.entite.User;

import client.client.service.Facade;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.*;
import org.junit.Test;
import org.mockserver.junit.MockServerRule;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpResponse;

import java.io.IOException;
import java.util.Collection;

import static org.mockserver.model.HttpRequest.request;

public class ClientApplicationTests {

    private Facade proxyV2;

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, 8080);


    @Before
    public void initialisation(){
        this.proxyV2 = new Facade();
    }


/*
    @Test
    public void TestGetALLusers() throws IOException, InterruptedException {

       mockServerRule.getClient().when(
                request()
                        .withMethod("GET")
                        .withPath("/serv/user")
                , Times.exactly(1))
                .respond(HttpResponse.response().withStatusCode(200)
                        .withHeader("Content-Type","application/json")
                        .withBody("[{\"id\":1,\"pseudo\":\"moi\",\"pwd\":\"000\"},{\"id\":2,\"pseudo\":\"lui\",\"pwd\":\"111\"}]")
                );

       Collection<User> userCollection =proxyV2.();
        Assert.assertEquals(userCollection.size(),2);



    }
*/

    @Test
    public void testInscriptionOk() throws DejaInscritException, JsonProcessingException {

       mockServerRule.getClient().when(
                request()
                        .withMethod("POST")
                        .withPath("/serv/user")
                        .withHeader("Content-Type","application/json")
                        .withBody("{\"id\":null,\"pseudo\":\"moi\",\"pwd\":\"000\"}"), Times.exactly(1))
                .respond(HttpResponse.response().withStatusCode(201).withHeader("Location","/serv/user/2"));

        User user = proxyV2.inscription("moi","000");

    }

    @Test
    public void testConnnexionOk() throws InterruptedException, MdpIncorrectOuNonInscritException, DejaConnecteException, IOException {

        mockServerRule.getClient().when(
                request()
                        .withMethod("POST")
                        .withPath("/serv/user/connexion")
                        .withHeader("Content-Type","application/json")
                        .withBody("{\"id\":null,\"pseudo\":\"moi\",\"pwd\":\"000\"}"), Times.exactly(1))
                .respond(HttpResponse.response().withStatusCode(201).withHeader("Location","/serv/user/2"));

        User user = proxyV2.connexion("moi","000");

    }



}
