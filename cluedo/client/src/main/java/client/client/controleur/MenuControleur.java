package client.client.controleur;

import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import client.client.modele.entite.io.FxmlUrl;
import client.client.vue.Login;
import client.client.vue.Menu;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MenuControleur  {

    //simulation invitation database;
    ArrayList<Invitation> invitationsList =new ArrayList<>();

    Stage menuStage;

    Menu menu;

    public MenuControleur(Stage menuStage) {
            invitationsList.add(  new Invitation("1",new User("user1","userPsw"))   );
            invitationsList.add(  new Invitation("2",new User("user2","userPsw2"))   );
        menu = (Menu)Menu.creerInstance(menuStage , FxmlUrl.MENU.getUrl());
        menu.setControleur(this);
        menu.drawTable(invitationsList);
        menu.show("menu");
    }


}
