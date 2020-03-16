package client.client.controleur;

import client.client.modele.entite.User;
import client.client.modele.entite.io.FxmlUrl;
import client.client.vue.Login;
import client.client.vue.View;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class ConnexionControleur {
    //simulation API
    Collection<User> UsersBD = new ArrayList<>();


    Stage connexionStage;

    Login login;

    public ConnexionControleur(Stage primaryStage) {
        this.connexionStage=primaryStage;
        login= (Login) View.creerInstance(this,connexionStage, FxmlUrl.LOGIN.getUrl());
        login.show("Login");
       // addObserver(principal);
     //   login.show("connexion");
    }

    public void loginCntrl(String email,String password){
        User user =new User(email,password);

    }



}
