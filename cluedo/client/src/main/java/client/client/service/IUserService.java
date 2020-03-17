package client.client.service;

import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    public ResponseEntity<User[]> getAllUsers(String login);
}
