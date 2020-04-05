package client.client.modele.entite.etat_partie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import client.client.modele.entite.Joueur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DebutTour.class, name="DebutTour"),
        @JsonSubTypes.Type(value = EnAttenteDesJoueurs.class, name="EnAttenteDesJoueurs"),
        @JsonSubTypes.Type(value = FinTour.class, name="FinTour"),
        @JsonSubTypes.Type(value = Hypothese.class, name="Hypothese"),
        @JsonSubTypes.Type(value = PartieFinie.class, name="PartieFinie"),
        @JsonSubTypes.Type(value = ResolutionDes.class, name="ResolutionDes"),
        @JsonSubTypes.Type(value = ResolutionIndice.class, name="ResolutionIndice"),
        @JsonSubTypes.Type(value = Supputation.class, name="Supputation")

})
public interface IEtatPartie {

    public Joueur obtenirJoueurCourant() throws UnsupportedOperationException;

    public Joueur obtenirJoueurAtif() throws UnsupportedOperationException;

    public Map<TypeCarte, ICarte> obtenirHypothese() throws UnsupportedOperationException;

    public List<Integer> obtenirDes() throws UnsupportedOperationException;

    public List<ICarte> obtenirIndices() throws UnsupportedOperationException;

    public String obtenirTexte();


}
