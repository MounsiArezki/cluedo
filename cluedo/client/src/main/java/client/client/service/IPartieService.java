package client.client.service;

import client.client.modele.entite.Partie;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;

public interface IPartieService {

    public void sauvegarderPartie(String idPartie) throws HttpClientErrorException, HttpServerErrorException;

    public Partie restaurerPartie(String idPartie);

    public Partie getPartieById(String idPartie);

    public Partie emettreHypothese(String idPartie, String idJoueur, Map<TypeCarte, ICarte> hypothese);
}
