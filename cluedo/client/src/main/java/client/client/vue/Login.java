package client.client.vue;

import client.client.controleur.ConnexionControleur;
import client.client.exception.ConnexionException;
import client.client.exception.InscriptionException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javax.security.auth.login.LoginException;


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
        try {
            this.getControleur().loginCntrl(user.getText(),password.getText());

        } catch (ConnexionException e) {
            showMessage(e.getMessage(), Alert.AlertType.ERROR);
        }

    }



    @FXML
    public void inscrireAction(ActionEvent event){
        try {
            getControleur().inscrireCntrl(user.getText(),password.getText());
            showMessage("Inscription réussie, vous pouvez vous connecter", Alert.AlertType.INFORMATION);
        } catch (InscriptionException e) {
            showMessage(e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    @Override
    public void refresh() {

    }
}
