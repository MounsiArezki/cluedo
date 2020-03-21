package client.client.controleur;

        import client.client.modele.entite.Invitation;
        import client.client.modele.entite.io.FxmlPath;
        import client.client.service.Facade;
        import client.client.service.IUserService;
        import client.client.vue.Menu;
        import javafx.stage.Stage;

        import java.util.ArrayList;
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
        new CreerPartieControleur(menuStage);
    }

    public void goToRestaurerPartie() {
        new RestaurerPartieControleur(menuStage);
    }

}
