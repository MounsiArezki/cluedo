package client.client.service;

import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;

import java.io.IOException;
import java.util.Collection;

public interface IProxyV2 {

    //user

    public Collection<User> getAllUsers() throws IOException, InterruptedException;

    public Collection<User>  getAllUsersWithFiltre(String filtre);

    public User connexion(String login, String pwd) throws IOException, InterruptedException, MdpIncorrectOuNonInscritException;

    public void deconnexion();

    public User insciption(String login, String pwd) throws DejaInscritException;

    public void desinscrition();

    public Collection<Invitation> getAllInvitationsRecues();

    public Collection<Invitation> getAllInvitationsEmises();









}
