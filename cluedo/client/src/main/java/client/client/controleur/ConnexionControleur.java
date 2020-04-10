package client.client.controleur;

import client.client.exception.connexionException.*;
import client.client.global.VariablesGlobales;
        import client.client.modele.entite.User;
        import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IUserService;
import client.client.vue.LoginView;
import client.client.vue.View;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

public class ConnexionControleur {

    Stage connexionStage;

    IUserService userService;
    LoginView loginView;

    public ConnexionControleur(Stage primaryStage) {
        userService=Facade.getInstance();
        this.connexionStage=primaryStage;
        loginView = (LoginView) View.creerInstance(connexionStage, FxmlPath.LOGIN.getUrl());
        loginView.setControleur(this);
        loginView.show("Login");
    }

    public void loginCntrl(String login,String pwd) throws MdpIncorrectOuNonInscritException, DejaConnecteException {
        try {
            User user=user = userService.connexion(login, pwd);
            VariablesGlobales.setUser(user);
            goToMenu(connexionStage);
        }
        catch (JsonProcessingException e) {
            loginView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
    }

    public void inscrireCntrl(String login,String password) throws  DejaInscritException {
        try {
            userService.insciption(login, password);
        }
        catch (JsonProcessingException e) {
            loginView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }

    }

    private void goToMenu(Stage menuStage) {
        new MenuControleur(menuStage);
    }
}
