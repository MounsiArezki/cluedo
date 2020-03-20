package client.client;

import client.client.config.ServiceConfig;
import client.client.modele.entite.User;
import client.client.service.Facade;
import org.junit.Test;
import org.junit.internal.Classes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Facade.class)
public class ClientApplicationTests {

    Facade facade =new Facade();



    @Test
    public void GetAllUsersTest(){
       facade.getAllUsers();

    }



}
