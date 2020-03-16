package client.client.controleur;

import client.client.modele.entite.io.FxmlUrl;
import client.client.vue.Login;
import client.client.vue.Menu;
import javafx.stage.Stage;

public class MenuControleur  {


    Stage menuStage;

    Menu menu;

    public MenuControleur(Stage menuStage) {
        menu = (Menu)Menu.creerInstance(menuStage , FxmlUrl.MENU.getUrl());
        menu.setControleur(this);
        menu.show("menu");
    }




}
