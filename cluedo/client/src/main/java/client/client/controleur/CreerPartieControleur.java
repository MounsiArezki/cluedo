package client.client.controleur;

import client.client.global.VariablesGlobales;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IInvitationService;
import client.client.service.IUserService;
import client.client.vue.CreerPartie;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CreerPartieControleur {

    IInvitationService invitationService;
//    IUserService userService;


    CreerPartie creerPartie;
    Stage creerPartieStage;


    public CreerPartieControleur(Stage creerPartieStage) {
        invitationService = new Facade();
     //   userService=new Facade();
        this.creerPartieStage = creerPartieStage;
        creerPartie = (CreerPartie)CreerPartie.creerInstance(creerPartieStage , FxmlPath.CREER_PARTIE.getUrl());
        creerPartie.setControleur(this);
        creerPartie.refresh();
        creerPartie.setTimer(5);

        creerPartie.show("Creation Partie");
    }

    public Collection<User> getAllUsers(String recherche) {
       // User[] users;
        Collection<User> users;
        if("".equals(recherche)){
            users= VariablesGlobales.getProxyV2().getAllUsers();
        }
        else{
            users= VariablesGlobales.getProxyV2().getAllUsersWithFiltre(recherche);
        }
        return users;
    }

    public void lancerInvitation(List<User> invites){
        Invitation invitation = invitationService.creerInvitation(VariablesGlobales.getUser(),invites);
        creerPartie.stopTimer();
        new PlateauControleur(creerPartieStage, invitation.getIdPartie());
    }

    public void goToMenu() {
        creerPartie.stopTimer();
        new MenuControleur(creerPartieStage);
    }

}
