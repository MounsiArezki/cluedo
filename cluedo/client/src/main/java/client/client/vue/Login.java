package client.client.vue;

import client.client.controleur.ConnexionControleur;
import client.client.modele.entite.io.FxmlUrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

public class Login extends View   {



    @FXML
    private TextField user ;

    @FXML
    private TextField password;


    public Login(ConnexionControleur controleur) {
        this.controleur = controleur;
    }

    public Login() {
    }

    public ConnexionControleur getControleur() {
        return controleur;
    }

    ConnexionControleur controleur;

    public void setControleur(ConnexionControleur controleur) {
        this.controleur = controleur;
    }

    public static Login creerInstance(ConnexionControleur controleur, Stage stage){
        URL location = FxmlUrl.LOGIN.getUrl();
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root =null;
        try{
            root=(Parent) fxmlLoader.load() ;

        } catch (IOException e) {
            e.printStackTrace();
        }
        Login vue =fxmlLoader.getController();
        vue.setStage(stage);
        vue.setControleur(controleur);
        return vue;

    }

    public void loginAction(ActionEvent actionEvent){

    }

    public void quitterAction(ActionEvent actionEvent){

    }


}
