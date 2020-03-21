package client.client.controleur;

        import client.client.global.VariablesGlobales;
        import client.client.modele.entite.Invitation;
        import client.client.modele.entite.io.FxmlPath;
        import client.client.service.Facade;
        import client.client.service.IUserService;
        import client.client.vue.Menu;
        import javafx.stage.Stage;
        import java.util.Arrays;
        import java.util.List;

public class MenuControleur  {

    Stage menuStage;
    IUserService userService;
    Menu menu;

    public MenuControleur(Stage menuStage) {
        this.menuStage =menuStage;
        userService = new Facade();
        menu = (Menu)Menu.creerInstance(menuStage , FxmlPath.MENU.getUrl());
        menu.setControleur(this);
        menu.setTimer(5);
        menu.show("menu");
    }

    public List<Invitation> getAllInvitationsRecues(){
        Invitation[] invitationsRecues = userService.getAllInvitationsRecues();
        return Arrays.asList(invitationsRecues);
    }


    public void goToCreerPartie(){
        menu.stopTimer();
        new CreerPartieControleur(menuStage);
    }

    public void goToRestaurerPartie() {
        menu.stopTimer();
        new RestaurerPartieControleur(menuStage);
    }

    public void deconnexion(){
        menu.stopTimer();
        userService.deconnexion();
        VariablesGlobales.setUser(null);
        new ConnexionControleur(menuStage);
    }

}
