package client.client.service;

import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    public ResponseEntity<User> getUserByLogin(String login);
}
