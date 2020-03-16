package client.client.vue;

import client.client.controleur.ConnexionControleur;
import client.client.modele.entite.io.FxmlUrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;


public class Login extends View<ConnexionControleur>   {



    @FXML
    private TextField user ;

    @FXML
    private TextField password;


    public Login() {
    }


   // ConnexionControleur controleur;




    @FXML
    public void loginAction(ActionEvent event){
        this.getControleur().loginCntrl(user.getText(),password.getText());

    }
    @FXML
    public void inscrireAction(ActionEvent event){


    }


}
