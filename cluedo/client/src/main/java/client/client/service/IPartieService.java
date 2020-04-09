package client.client.service;

import client.client.modele.entite.Partie;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.function.Consumer;

import java.util.Map;

public interface IPartieService {

    public void sauvegarderPartie(String idPartie) throws HttpStatusCodeException, JsonProcessingException;

    public Partie restaurerPartie(String idPartie) throws HttpStatusCodeException, JsonProcessingException;

    public void subscribeFluxPartie(String idPartie, Consumer<Partie> consumer)throws HttpStatusCodeException, JsonProcessingException;;

    public Partie emettreHypothese(String idPartie, String idJoueur, Map<TypeCarte, ICarte> hypothese);

}
