package client.client.controleur;

import client.client.modele.entite.User;
import client.client.modele.entite.io.FxmlUrl;
import client.client.vue.Login;
import client.client.vue.Menu;
import client.client.vue.View;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.*;

public class ConnexionControleur {
    //simulation API
    Collection<User> UsersBD = new ArrayList();


    Stage connexionStage;


    Login login;
    Menu menu;

    public ConnexionControleur(Stage primaryStage) {
        UsersBD.add(new User("user@gmail.com","user"));
        this.connexionStage=primaryStage;
        login= (Login) View.creerInstance(connexionStage, FxmlUrl.LOGIN.getUrl());
        login.setControleur(this);
        login.show("Login");
       // addObserver(principal);
     //   login.show("connexion");
    }

    public void loginCntrl(String email,String password) throws LoginException {

        Optional<User> user = UsersBD.stream().
                filter(p -> p.getPseudo().equals(email) && p.getPassword().equals(password)).
                findFirst();
       if (!user.isPresent()){
           throw new LoginException();
       }

        GoToMenu(connexionStage);
    }

    public void InscrireCntrl(String email,String password){


    }


    private void GoToMenu(Stage menuStage) {

        new MenuControleur(menuStage);
    }
}
