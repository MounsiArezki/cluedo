package client.client.service;

import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

public interface IUserService {

    public ResponseEntity<User[]> getAllUsers()throws HttpClientErrorException, HttpServerErrorException;

    public ResponseEntity<User[]> getAllUsersWithFiltre(String filtre)throws HttpClientErrorException, HttpServerErrorException;

    public ResponseEntity<User> connexion(String login, String pwd)throws HttpClientErrorException, HttpServerErrorException;

    public void deconnexion()throws HttpClientErrorException, HttpServerErrorException;

    public ResponseEntity<User> insciption(String login, String pwd)throws HttpClientErrorException, HttpServerErrorException;

    public void desinscrition()throws HttpClientErrorException, HttpServerErrorException;

    public ResponseEntity<Invitation[]> getAllInvitationsRecues()throws HttpClientErrorException, HttpServerErrorException;

    public ResponseEntity<Invitation[]> getAllInvitationsEmises()throws HttpClientErrorException, HttpServerErrorException;

}
