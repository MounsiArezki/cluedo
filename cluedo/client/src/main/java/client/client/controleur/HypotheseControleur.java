package client.client.controleur;

import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IPartieService;
import client.client.vue.HypotheseVue;
import javafx.stage.Stage;

public class HypotheseControleur {

    private Stage hypotheseStage;
    private IPartieService partieService;
    private HypotheseVue hypotheseVue;

    public HypotheseControleur(Stage hypotheseStage){
        this.hypotheseStage = hypotheseStage;
        this.partieService = new Facade();

        this.hypotheseVue = (HypotheseVue) HypotheseVue.creerInstance(hypotheseStage, FxmlPath.HYPOTHESE.getUrl());
        this.hypotheseVue.setControleur(this);

        /*this.hypotheseVue.setAllCards();*/
        this.hypotheseVue.show("hypothese");
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

    public HypotheseVue getHypotheseVue() {
        return hypotheseVue;
    }

    public void setHypotheseVue(HypotheseVue hypotheseVue) {
        this.hypotheseVue = hypotheseVue;
    }
}
