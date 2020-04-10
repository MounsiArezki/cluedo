package webservice_v2.modele.entite.etat_partie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DebutTour.class, name="AttentePiocheIndice"),
        @JsonSubTypes.Type(value = DebutTour.class, name="DebutTour"),
        @JsonSubTypes.Type(value = EnAttenteDesJoueurs.class, name="EnAttenteDesJoueurs"),
        @JsonSubTypes.Type(value = FinTour.class, name="FinTour"),
        @JsonSubTypes.Type(value = Hypothese.class, name="Hypothese"),
        @JsonSubTypes.Type(value = PartieFinie.class, name="PartieFinie"),
        @JsonSubTypes.Type(value = ResolutionDes.class, name="ResolutionDes"),
        @JsonSubTypes.Type(value = ResolutionIndice.class, name="ResolutionIndice"),
        @JsonSubTypes.Type(value = RevelationIndice.class, name="RevelationIndice"),
        @JsonSubTypes.Type(value = Supputation.class, name="Supputation")

})
public interface IEtatPartie {

    public IEtatPartie lancerDe(List<Integer> des) throws UnsupportedOperationException;

    public IEtatPartie piocherIndice(List<ICarte> indices, List<Integer> des) throws UnsupportedOperationException;

    public IEtatPartie deplacer() throws UnsupportedOperationException;

    public IEtatPartie revelerCarte(Joueur joueurActif) throws UnsupportedOperationException;

    public IEtatPartie attentePiocheIndice(Joueur joueurCourant, List<Integer> des) throws UnsupportedOperationException;

    public IEtatPartie revelationIndice(Joueur joueurCourant, Joueur joueurActif, ICarte carte) throws UnsupportedOperationException;

    public IEtatPartie passerRevelerCarte(Joueur joueurActif) throws UnsupportedOperationException;

    public IEtatPartie faireHypothese(Joueur joueurActif, Map<TypeCarte, ICarte> hypothese) throws UnsupportedOperationException;

    public IEtatPartie resoudreHypothese() throws UnsupportedOperationException;

    public IEtatPartie debuterTour(Joueur joueurSuivant) throws UnsupportedOperationException;

    public IEtatPartie finirPartie(Joueur gagnant, Map<TypeCarte, ICarte> combinaisonGagante) throws UnsupportedOperationException;

    public Joueur obtenirJoueurCourant() throws UnsupportedOperationException;

    public Joueur obtenirJoueurAtif() throws UnsupportedOperationException;

    public Map<TypeCarte, ICarte> obtenirHypothese() throws UnsupportedOperationException;

    public List<Integer> obtenirDes() throws UnsupportedOperationException;

    public List<ICarte> obtenirIndices() throws UnsupportedOperationException;

    public void changerJoueurActif(Joueur actifSuivant) throws UnsupportedOperationException;

}
