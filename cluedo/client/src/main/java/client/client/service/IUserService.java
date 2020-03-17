package client.client.service;

import client.client.modele.entite.User;

public interface IUserService {

    public User getUserByLogin(String login);
}
