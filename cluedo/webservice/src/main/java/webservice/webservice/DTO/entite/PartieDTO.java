package webservice.webservice.DTO.entite;

import webservice.webservice.modele.entite.Partie;
import webservice.webservice.modele.entite.carte.ICarte;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartieDTO {

    private String id;
    private String idHote;
    private List<String> idJoueurs;
    private Map<String, ICarte> combinaisonGagante;

    public PartieDTO() {}

    public static PartieDTO creer(Partie partie) {
        PartieDTO partieDTO = new PartieDTO();
        partieDTO.setId(partie.getId());
        partieDTO.setIdHote(partie.getHote().getId());
        partieDTO.setIdJoueurs(new ArrayList<>(partie.getJoueurs().keySet()));
        partieDTO.setCombinaisonGagante(partie.getCombinaisonGagante());
        return partieDTO;
    }

    public String getId() { return id; }

    public String getIdHote() { return idHote; }

    public List<String> getIdJoueurs() { return idJoueurs; }

    public Map<String, ICarte> getCombinaisonGagante() { return combinaisonGagante; }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdHote(String idHote) {
        this.idHote = idHote;
    }

    public void setIdJoueurs(List<String> idJoueurs) {
        this.idJoueurs = idJoueurs;
    }

    public void setCombinaisonGagante(Map<String, ICarte> combinaisonGagante) { this.combinaisonGagante = combinaisonGagante; }
}
