package client.client.vue;

import client.client.controleur.ConnexionControleur;
import client.client.modele.entite.io.FxmlUrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Login extends View   {


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






}
