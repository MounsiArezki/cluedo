package client.client.controleur;

import client.client.modele.entite.Partie;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.TypeCarte;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IJoueurService;
import client.client.service.IPartieService;
import client.client.vue.AccusationView;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class AccusationControleur {

    private Stage accusationStage;
    private IPartieService partieService;
    private IJoueurService joueurService;
    private AccusationView accusationView;
    private Partie partie;

    public AccusationControleur(Partie partie){
        this.accusationStage = new Stage();
        this.partieService = Facade.getInstance();
        this.joueurService = Facade.getInstance();
        this.partie = partie;

        this.accusationView = (AccusationView) accusationView.creerInstance(accusationStage, FxmlPath.ACCUSATION.getUrl());
        this.accusationView.setControleur(this);

        this.accusationView.setAllCards();
        this.accusationView.show("accusation");
    }

    public void accuser(List<String> accusation){
        try {
            joueurService.emettreAccusation(partie.getId(), accusation);
            accusationStage.close();
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    //GETTERS et SETTERS

    public Stage getAccusationStage() {
        return accusationStage;
    }

    public void setAccusationStage(Stage accusationStage) {
        this.accusationStage = accusationStage;
    }

    public IPartieService getPartieService() {
        return partieService;
    }

    public void setPartieService(IPartieService partieService) {
        this.partieService = partieService;
    }

    public AccusationView getAccusationView() {
        return accusationView;
    }

    public void setAccusationView(AccusationView accusationView) {
        this.accusationView = accusationView;
    }
}
