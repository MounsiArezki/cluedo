package client.client.controleur;

import client.client.global.VariablesGlobales;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IInvitationService;
import client.client.service.IUserService;
import client.client.vue.CreerPartieView;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreerPartieControleur {

    IInvitationService invitationService;
    IUserService userService;

    List<User> usersCo;

    CreerPartieView creerPartieView;
    Stage creerPartieStage;


    public CreerPartieControleur(Stage creerPartieStage) {
        invitationService = Facade.getInstance();
        userService= Facade.getInstance();
        usersCo=new ArrayList<>();

        try {
            userService.subscribeFluxUsersConnectes(this::consumeFluxUsers);
        }
        catch (JsonProcessingException e) {
            creerPartieView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            creerPartieView.showMessage("Erreur "+e.getStatusCode(), Alert.AlertType.ERROR);
        }

        this.creerPartieStage = creerPartieStage;

        creerPartieView = (CreerPartieView) CreerPartieView.creerInstance(creerPartieStage , FxmlPath.CREER_PARTIE.getUrl());
        creerPartieView.setControleur(this);
        creerPartieView.refresh();

        creerPartieView.show("Creation Partie");
    }

    public Collection<User> getAllUsers(String recherche) {
       return usersCo;
    }

    public void lancerInvitation(List<User> invites){
        try {
            Invitation invitation =invitationService.creerInvitation(VariablesGlobales.getUser(),invites);
            new PlateauControleur(creerPartieStage, invitation.getIdPartie());
        }
        catch (JsonProcessingException e) {
            creerPartieView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            creerPartieView.showMessage("Erreur " + e.getStatusCode(), Alert.AlertType.ERROR);
        }
    }

    public void goToMenu() {
        new MenuControleur(creerPartieStage);
    }

    public void consumeFluxUsers(User[] users){
        Platform.runLater( () ->{
            if (users != null) {
                usersCo=new ArrayList<>(List.of(users));
                usersCo.remove(VariablesGlobales.getUser());
                creerPartieView.refresh();
            }
        });
    }

}
