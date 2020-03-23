package client.client;


import client.client.modele.entite.User;
import client.client.service.IProxyV2;
import client.client.service.ProxyV2;
import org.junit.*;
import org.mockserver.junit.MockServerRule;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpResponse;

import java.io.IOException;
import java.util.Collection;

import static org.mockserver.model.HttpRequest.request;

public class ClientApplicationTests {

    private IProxyV2 proxyV2;

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, 8080);


    @Before
    public void initialisation(){
        this.proxyV2 = new ProxyV2();
    }



    @Test
    public void TestGetALLusers() throws IOException, InterruptedException {

        mockServerRule.getClient().when(
                request()
                        .withMethod("GET")
                        .withPath("/serv/user/connexion")
                , Times.exactly(1))
                .respond(HttpResponse.response().withStatusCode(200)
                        .withHeader("Content-Type","application/json")
                        .withBody("[{\"id\":1,\"pseudo\":\"moi\",\"pwd\":\"000\"},{\"id\":2,\"pseudo\":\"lui\",\"pwd\":\"111\"}]")
                );

       Collection<User> userCollection =proxyV2.getAllUsers();
        Assert.assertEquals(userCollection.size(),2);



    }




}
