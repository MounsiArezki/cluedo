package client.client.controleur;

import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IPartieService;
import client.client.vue.AccusationView;
import javafx.stage.Stage;

import java.util.Map;

public class AccusationControleur {

    private Stage accusationStage;
    private IPartieService partieService;
    private AccusationView accusationView;

    public AccusationControleur(){
        this.accusationStage = new Stage();
        this.partieService = Facade.getInstance();

        this.accusationView = (AccusationView) accusationView.creerInstance(accusationStage, FxmlPath.ACCUSATION.getUrl());
        this.accusationView.setControleur(this);

        this.accusationView.setAllCards();
        this.accusationView.show("accusation");
    }

    public void accuser(Map<String, ICarte> accusation){

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
