package client.client.service;

import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface IJoueurService {

    public List<ICarte> getCartesJoueurs(String idPartie) throws HttpStatusCodeException, JsonProcessingException;
    public void subscribeFluxFichePartie(String idPartie, String idJoueur, Consumer<Map<ICarte, Joueur>> consumer)throws HttpStatusCodeException, JsonProcessingException;

}
