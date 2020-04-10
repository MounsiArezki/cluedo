package client.client.vue;

import client.client.controleur.ConnexionControleur;
import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.InscriptionException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import java.io.IOException;


public class LoginView extends View<ConnexionControleur>   {



    @FXML
    private TextField user ;

    @FXML
    private TextField password;


    public LoginView() {

    }

   // ConnexionControleur controleur;

    @FXML
    public void loginAction(ActionEvent event){
        if (user.getText().isEmpty() || user.getText().isEmpty()){
            this.showMessage("Veuillez remplire les champs svp", Alert.AlertType.INFORMATION);
        }
        else {
            try {
                this.getControleur().loginCntrl(user.getText(), password.getText());
            } catch (MdpIncorrectOuNonInscritException e) {
                showMessage("Non inscrit ou mdp incorect !", Alert.AlertType.WARNING);
            } catch (DejaConnecteException e) {
                showMessage("deja connecté §", Alert.AlertType.WARNING);
            }
        }
    }


    @FXML
    public void inscrireAction(ActionEvent event){
        if (user.getText().isEmpty() || user.getText().isEmpty()){
            this.showMessage("Veuillez remplire les champs svp", Alert.AlertType.INFORMATION);
        }
        try {
            getControleur().inscrireCntrl(user.getText(),password.getText());
            showMessage("Inscription réussie, vous pouvez vous connecter", Alert.AlertType.INFORMATION);
        }
        catch ( DejaInscritException e) {
            showMessage(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @Override
    public void refresh() {

    }

}
