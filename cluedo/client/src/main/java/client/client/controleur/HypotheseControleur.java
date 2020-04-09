package client.client.controleur;

import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IPartieService;
import client.client.vue.HypotheseView;
import javafx.stage.Stage;

public class HypotheseControleur {

    private Stage hypotheseStage;
    private IPartieService partieService;
    private HypotheseView hypotheseView;

    public HypotheseControleur(){
        this.hypotheseStage = new Stage();
        this.partieService = Facade.getInstance();

        this.hypotheseView = (HypotheseView) HypotheseView.creerInstance(hypotheseStage, FxmlPath.HYPOTHESE.getUrl());
        this.hypotheseView.setControleur(this);

        this.hypotheseView.setAllCards();
        this.hypotheseView.show("hypothese");
    }

    public Stage getHypotheseStage() {
        return hypotheseStage;
    }

    public void setHypotheseStage(Stage hypotheseStage) {
        this.hypotheseStage = hypotheseStage;
    }

    public IPartieService getPartieService() {
        return partieService;
    }

    public void setPartieService(IPartieService partieService) {
        this.partieService = partieService;
    }

    public HypotheseView getHypotheseView() {
        return hypotheseView;
    }

    public void setHypotheseView(HypotheseView hypotheseView) {
        this.hypotheseView = hypotheseView;
    }
}
