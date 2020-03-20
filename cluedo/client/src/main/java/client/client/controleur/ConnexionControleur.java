package client.client.controleur;

import client.client.exception.ConnexionException;
import client.client.exception.InscriptionException;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.User;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IUserService;
import client.client.vue.Login;
import client.client.vue.Menu;
import client.client.vue.View;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.LoginException;

public class ConnexionControleur {

    Stage connexionStage;

    IUserService userService;

    Login login;
    Menu menu;

    public ConnexionControleur(Stage primaryStage) {
        userService=new Facade();

        this.connexionStage=primaryStage;
        login= (Login) View.creerInstance(connexionStage, FxmlPath.LOGIN.getUrl());
        login.setControleur(this);
        login.show("Login");
       // addObserver(principal);
     //   login.show("connexion");
    }

    public void loginCntrl(String login,String pwd) throws ConnexionException {
       User user=new User(login, pwd);
       VariablesGlobales.setUser(user);
       goToMenu(connexionStage);
    }

    public void inscrireCntrl(String login,String password) throws InscriptionException {

    }

    private void goToMenu(Stage menuStage) {
        new MenuControleur(menuStage);
    }
}
