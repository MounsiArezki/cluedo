package client.client.service;

import client.client.modele.entite.Partie;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.function.Consumer;

public interface IPartieService {

    public void sauvegarderPartie(String idPartie) throws HttpStatusCodeException, JsonProcessingException;

    public Partie restaurerPartie(String idPartie) throws HttpStatusCodeException, JsonProcessingException;

    public void subscribeFluxPartie(String idPartie, Consumer<Partie> consumer)throws HttpStatusCodeException, JsonProcessingException;;
}
