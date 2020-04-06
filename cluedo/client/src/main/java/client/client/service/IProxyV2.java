package client.client.service;

import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.exception.connexionException.NonInscritException;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;

import java.io.IOException;
import java.util.Collection;

public interface IProxyV2 {

    //user

    public Collection<User> getAllUsers() throws IOException, InterruptedException;

    public Collection<User>  getAllUsersWithFiltre(String filtre);

    public User connexion(String login, String pwd) throws IOException, InterruptedException, MdpIncorrectOuNonInscritException;

    public void deconnexion() throws IOException, InterruptedException, NonInscritException;

    public User insciption(String login, String pwd) throws DejaInscritException;

    public void desinscription() throws IOException, InterruptedException, NonInscritException;

    public Collection<Invitation> getAllInvitationsRecues() throws IOException, InterruptedException;

    public Collection<Invitation> getAllInvitationsEmises() throws IOException, InterruptedException;









}
