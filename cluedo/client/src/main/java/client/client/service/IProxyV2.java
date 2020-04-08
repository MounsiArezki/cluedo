package client.client.service;

import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.exception.connexionException.NonInscritException;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface IProxyV2 {

    //user

    public Collection<User> getAllUsers();

    public Collection<User>  getAllUsersWithFiltre(String filtre);

    public User connexion(String login, String pwd) throws IOException, InterruptedException, MdpIncorrectOuNonInscritException;

    public void deconnexion() throws NonInscritException;

    public User insciption(String login, String pwd) throws DejaInscritException;

    public void desinscription() throws  NonInscritException;

    public Collection<Invitation> getAllInvitationsRecues();

    public Collection<Invitation> getAllInvitationsEmises() ;







}
