package client.client.controleur;

import client.client.vue.Login;
import client.client.vue.Menu;
import javafx.stage.Stage;

public class MenuControleur  {


    Stage menuStage;

    Menu menu;

    public MenuControleur(Stage menuStage) {
        this.menuStage=menuStage;
        menu=Menu.creerInstance(this,menuStage);
       // menu.show("Menu");
        // addObserver(principal);
        //   login.show("connexion");
    }




}
