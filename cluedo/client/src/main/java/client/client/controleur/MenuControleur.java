package client.client.controleur;

import client.client.exception.connexionException.NonInscritException;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IInvitationService;
import client.client.service.IUserService;
import client.client.vue.Menu;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MenuControleur  {

    Stage menuStage;
 //   IUserService userService;
    IInvitationService invitationService;
    Menu menu;

    public MenuControleur(Stage menuStage) {
        this.menuStage =menuStage;
        //userService = new Facade();
        invitationService = new Facade();
        menu = (Menu)Menu.creerInstance(menuStage , FxmlPath.MENU.getUrl());
        menu.setControleur(this);
        menu.setTimer(5);
        menu.show("menu");
    }

    public List<Invitation> getAllInvitationsRecues(){
        List<Invitation> invitationsRecues = (List<Invitation>) VariablesGlobales.getProxyV2().getAllInvitationsRecues();
        return invitationsRecues;
    }


    public void goToCreerPartie()  {
        menu.stopTimer();
        new CreerPartieControleur(menuStage);
    }

    public void goToRestaurerPartie() {
        menu.stopTimer();
        new RestaurerPartieControleur(menuStage);
    }

    public void rejoindrePartie(Invitation invitation){
        menu.stopTimer();
        invitationService.accepterInvitation(invitation.getId());
        new PlateauControleur(menuStage, invitation.getIdPartie());
    }

    public void refuserInvitation(Invitation invitation) {
        invitationService.refuserInvitation(invitation.getId());
    }

    public void deconnexion(){
        menu.stopTimer();
        try {
            VariablesGlobales.getProxyV2().deconnexion();
        } catch (NonInscritException e) {}
        VariablesGlobales.setUser(null);
        new ConnexionControleur(menuStage);
    }

}
