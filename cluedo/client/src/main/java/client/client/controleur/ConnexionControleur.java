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
import javafx.scene.control.Alert;
import javafx.stage.Stage;

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

    public void loginCntrl(String login,String pwd) {
        try {
            User user = userService.connexion(login, pwd);
            VariablesGlobales.setUser(user);
            goToMenu(connexionStage);
        }
        catch (JsonProcessingException e) {
            loginView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (DejaConnecteException dejaConnecteE) {
            loginView.showMessage("L'utilisateur est déjà connecté", Alert.AlertType.WARNING);
        }
        catch (MdpIncorrectOuNonInscritException infoIncoherenteE){
            loginView.showMessage("Les informations renseignées sont erronées ou vous n'êtes pas encore inscrit", Alert.AlertType.WARNING);
        }
    }

    public void inscrireCntrl(String login,String password) {
        try {
                userService.inscription(login, password);
                loginView.showMessage("Inscription réussie, vous pouvez vous connecter", Alert.AlertType.INFORMATION);
            } catch (JsonProcessingException e) {
                loginView.showMessage("Erreur lors de votre inscription", Alert.AlertType.ERROR);
            } catch (DejaInscritException dejaInscritE){
                loginView.showMessage("Les informations renseignées correspondent déjà à un utilisateur inscrit", Alert.AlertType.WARNING);
        }

    }

    private void goToMenu(Stage menuStage) {
        new MenuControleur(menuStage);
    }
}
