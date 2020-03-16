package client.client.controleur;

import client.client.vue.Login;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ConnexionControleur {


    Stage connexionStage;

    Login login;

    public ConnexionControleur(Stage primaryStage) {
        this.connexionStage=primaryStage;
        login=Login.creerInstance(this,connexionStage);
        login.show("Login");
       // addObserver(principal);
     //   login.show("connexion");
    }





}
