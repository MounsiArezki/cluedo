package client.client.modele.entite.carte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Arme.class, name = "Arme"),
        @JsonSubTypes.Type(value = Lieu.class, name = "Lieu"),
        @JsonSubTypes.Type(value = Personnage.class, name = "Personnage"),
        @JsonSubTypes.Type(value = Speciale.class, name = "Speciale"),
})

public interface ICarte {

    String getNom();

    public TypeCarte getTypeCarte();

}
