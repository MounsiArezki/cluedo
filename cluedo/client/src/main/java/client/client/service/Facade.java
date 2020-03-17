package client.client.service;

import client.client.config.ServiceConfig;
import client.client.modele.entite.User;
import org.springframework.web.client.RestTemplate;

public class Facade implements IUserService {

    RestTemplate restTemplate;

    public Facade() {
        restTemplate=new RestTemplate();
    }

    @Override
    public User getUserByLogin(String login) {
        //User res=restTemplate.getForEntity(ServiceConfig.URL_USER)
        return null;
    }
}
