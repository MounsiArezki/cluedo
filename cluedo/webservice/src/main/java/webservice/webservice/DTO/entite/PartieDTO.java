package webservice.webservice.DTO.entite;

import webservice.webservice.modele.entite.Partie;
import webservice.webservice.modele.entite.carte.ICarte;
import java.util.List;
import java.util.Map;

public class PartieDTO {

    private long id;
    private long idHote;
    private List<Long> idJoueurs;
    private Map<String, ICarte> combinaisonGagante;

    public PartieDTO(){}

    public static PartieDTO creer(Partie partie){
        PartieDTO partieDTO = new PartieDTO();
        partieDTO.setId(partie.getId());
        partieDTO.setIdHote(partie.getIdHote());
        partieDTO.setIdJoueurs(partie.getIdJoueurs());
        partieDTO.setCombinaisonGagante(partie.getCombinaisonGagante());
        return partieDTO;
    }

    public long getId() { return id; }

    public long getIdHote() { return idHote; }

    public List<Long> getIdJoueurs() { return idJoueurs; }

    public Map<String, ICarte> getCombinaisonGagante() { return combinaisonGagante; }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdHote(long idHote) {
        this.idHote = idHote;
    }

    public void setIdJoueurs(List<Long> idJoueurs) {
        this.idJoueurs = idJoueurs;
    }

    public void setCombinaisonGagante(Map<String, ICarte> combinaisonGagante) {
        this.combinaisonGagante = combinaisonGagante;
    }
}
