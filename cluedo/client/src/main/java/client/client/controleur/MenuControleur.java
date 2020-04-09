package client.client.controleur;

import client.client.exception.connexionException.NonInscritException;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IInvitationService;
import client.client.service.IUserService;
import client.client.vue.MenuView;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.List;

public class MenuControleur  {

    Stage menuStage;

    IInvitationService invitationService;
    IUserService userService;

    MenuView menuView;

    List<Invitation> invitationsRecues;

    public MenuControleur(Stage menuStage) {
        this.menuStage =menuStage;
        invitationService =Facade.getInstance();
        userService=Facade.getInstance();
        invitationsRecues=new ArrayList<>();

        try {
            userService.subscribeFluxInvitationsRecues(VariablesGlobales.getUser().getId(), this::consumeFluxInvitations);
        }
        catch (JsonProcessingException e) {
            menuView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            menuView.showMessage("Erreur "+e.getStatusCode(), Alert.AlertType.ERROR);
        }

        menuView = (MenuView) MenuView.creerInstance(menuStage , FxmlPath.MENU.getUrl());
        menuView.setControleur(this);
        menuView.show("menu");
    }

    public List<Invitation> getInvitationsRecues(){
        return invitationsRecues;
    }


    public void goToCreerPartie()  {
        new CreerPartieControleur(menuStage);
    }

    public void goToRestaurerPartie() {
        new RestaurerPartieControleur(menuStage);
    }

    public void rejoindrePartie(Invitation invitation){
        try {
            invitationService.accepterInvitation(invitation.getId());
            new PlateauControleur(menuStage, invitation.getIdPartie());
        }
        catch (JsonProcessingException e) {
            menuView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            menuView.showMessage("Erreur "+e.getStatusCode(), Alert.AlertType.ERROR);
        }
    }

    public void refuserInvitation(Invitation invitation) {
        try {
            invitationService.refuserInvitation(invitation.getId());
        }
        catch (JsonProcessingException e) {
            menuView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            menuView.showMessage("Erreur "+e.getStatusCode(), Alert.AlertType.ERROR);
        }
    }

    public void deconnexion(){
        try {
            userService.deconnexion(VariablesGlobales.getUser().getId());
            VariablesGlobales.setUser(null);
            new ConnexionControleur(menuStage);
        }
        catch (JsonProcessingException e) {
            menuView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            menuView.showMessage("Erreur "+e.getStatusCode(), Alert.AlertType.ERROR);
        }
    }

    public void consumeFluxInvitations(Invitation invitation){
        Platform.runLater( () -> {
            if (invitation != null) {
                invitationsRecues.add(invitation);
                menuView.refresh();
            }
        });

    }

}
