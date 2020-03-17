package client.client.service;

import client.client.config.ServiceConfig;
import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Facade implements IUserService {

    RestTemplate restTemplate;

    public Facade() {
        restTemplate=new RestTemplate();
    }

   @Override
    public ResponseEntity<User[]> getAllUsers(String login) {
        ResponseEntity<User[]> res=restTemplate.getForEntity(ServiceConfig.URL_USER, User[].class);
        return res;
    }
}
