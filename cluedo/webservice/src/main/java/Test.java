import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import webservice_v2.config.ServiceConfig;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;
import webservice_v2.modele.entite.carte.Arme;
import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.Lieu;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.etat_partie.*;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws JsonProcessingException {
        IRien rien=new Rien("lala");

        ObjectMapper o= new ObjectMapper();

        Map<TypeCarte, ICarte> m = new HashMap<>();
        m.put(TypeCarte.LIEU, Lieu.HALL);

        System.out.println(o.writeValueAsString(m));
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.PROPERTY)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Rien.class, name="Rien"),
            @JsonSubTypes.Type(value = QQchose.class, name="QQchose")

    })
    interface IRien{

        public void rien();
    }

    static class Rien implements IRien{

        private String r;

        public Rien(){}

        public Rien(String r) {
            this.r = r;
        }

        public String getR() {
            return r;
        }

        public void setR(String r) {
            this.r = r;
        }

        @Override
        public void rien() {

        }
    }

    static class QQchose implements IRien{

        private String q;

        public QQchose(){}

        public String getQ() {
            return q;
        }

        public void setQ(String q) {
            this.q = q;
        }

        @Override
        public void rien() {

        }
    }








}
