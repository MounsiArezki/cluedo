package client.client.service;

import client.client.modele.entite.Partie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public interface IPartieService {

    public void sauvegarderPartie(String idPartie) throws HttpClientErrorException, HttpServerErrorException;

    public Partie restaurerPartie(String idPartie);

    public Partie getPartieById(String idPartie);
}
