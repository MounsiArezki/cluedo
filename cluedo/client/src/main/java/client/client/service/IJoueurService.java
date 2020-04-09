package client.client.service;

import client.client.modele.entite.Joueur;
import client.client.modele.entite.Position;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface IJoueurService {

    public List<ICarte> getCartesJoueurs(String idPartie) throws HttpStatusCodeException, JsonProcessingException;

    public void subscribeFluxFichePartie(String idPartie, Consumer<Map<ICarte, Joueur>> consumer)throws HttpStatusCodeException, JsonProcessingException;

    public void emettreHypothese(String idPartie, Map<TypeCarte, ICarte> hypothese)throws HttpStatusCodeException, JsonProcessingException;

    public List<Integer> lancerDes(String idPartie) throws HttpStatusCodeException, JsonProcessingException;

    public void emettreAccusation(String idPartie, Map<TypeCarte, ICarte> accusation) throws HttpStatusCodeException, JsonProcessingException;

    public void passerTour(String idPartie) throws HttpStatusCodeException, JsonProcessingException;

    public void seDeplacer(String idPartie, Position position)  throws HttpStatusCodeException, JsonProcessingException;

    public void revelerCarte(String idPartie, ICarte carte) throws HttpStatusCodeException, JsonProcessingException;

    public List<ICarte> piocherIndices(String idPartie) throws HttpStatusCodeException, JsonProcessingException;
}
