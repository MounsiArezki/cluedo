package client.client.controleur;

import client.client.modele.entite.User;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IInvitationService;
import client.client.service.IUserService;
import client.client.vue.CreerPartie;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreerPartieControleur {

    IInvitationService invitationService;
    IUserService userService;

    CreerPartie creerPartie;
    Stage creerPartieStage;


    public CreerPartieControleur(Stage creerPartieStage) {
        invitationService = new Facade();
        userService=new Facade();
        this.creerPartieStage = creerPartieStage;
        creerPartie = (CreerPartie)CreerPartie.creerInstance(creerPartieStage , FxmlPath.CREER_PARTIE.getUrl());
        creerPartie.setControleur(this);
        creerPartie.show("Creation Partie");
    }

    public List<User> getAllUsers(){
        User[] users= userService.getAllUsers().getBody();
        List<User> res= Arrays.asList(users);
        return res;
    }

    public List<User> getAllUsersWithFiltre(String filtre){
        User[] users= userService.getAllUsersWithFiltre(filtre).getBody();
        List<User> res= Arrays.asList(users);
        return res;
    }

    public void goToMenu() {
        new MenuControleur(creerPartieStage);
    }

    public void goToPlateau() {
        new PlateauControleur(creerPartieStage);
    }
}
