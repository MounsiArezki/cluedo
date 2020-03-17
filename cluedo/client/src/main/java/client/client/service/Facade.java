package client.client.service;

import client.client.config.ServiceConfig;
import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Facade implements IUserService {

    RestTemplate restTemplate;

    public Facade() {
        restTemplate=new RestTemplate();
    }

    @Override
    public ResponseEntity<User> getUserByLogin(String login) {
        ResponseEntity<User> res=restTemplate.getForEntity(ServiceConfig.URL_USER, User.class);
        return res;
    }
}
