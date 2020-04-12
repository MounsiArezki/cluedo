package client.client.controleur;

import client.client.modele.entite.Partie;
import client.client.modele.entite.carte.Lieu;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IJoueurService;
import client.client.service.IPartieService;
import client.client.vue.HypotheseView;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.stage.Stage;

import java.util.List;

public class HypotheseControleur {

    private Stage hypotheseStage;
    private IPartieService partieService;
    private IJoueurService joueurService;
    private HypotheseView hypotheseView;
    private Partie partie;

    public HypotheseControleur(Lieu l, Partie partie){
        this.hypotheseStage = new Stage();
        this.partieService = Facade.getInstance();
        this.joueurService = Facade.getInstance();
        this.partie = partie;

        this.hypotheseView = (HypotheseView) HypotheseView.creerInstance(hypotheseStage, FxmlPath.HYPOTHESE.getUrl());
        this.hypotheseView.setControleur(this);
        this.hypotheseView.setLieu(l);

        this.hypotheseView.setAllCards();
        this.hypotheseView.show("hypothese");
    }

    public void emettreHypothese(List<String> hypothese){
        try {
            joueurService.emettreHypothese(partie.getId(), hypothese);
            hypotheseStage.close();
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    //GETTERS et SETTERS

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
