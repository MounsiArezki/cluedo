package client.client.vue;

import client.client.controleur.ConnexionControleur;
import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.DejaInscritException;
import client.client.exception.connexionException.InscriptionException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import java.io.IOException;


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
        if (user.getText().isEmpty() || user.getText().isEmpty()){
            this.showMessage("Veuillez remplire les champs svp", Alert.AlertType.INFORMATION);
        }else {
            try {
                this.getControleur().loginCntrl(user.getText(), password.getText());
            } catch (IOException | InterruptedException e) {

               this.showMessage(e.getMessage(), Alert.AlertType.ERROR);
            }  catch (MdpIncorrectOuNonInscritException e) {

                this.showMessage(e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }



    @FXML
    public void inscrireAction(ActionEvent event){
        if (user.getText().isEmpty() || user.getText().isEmpty()){
            this.showMessage("Veuillez remplire les champs svp", Alert.AlertType.INFORMATION);
        }try {
            getControleur().inscrireCntrl(user.getText(),password.getText());
            showMessage("Inscription réussie, vous pouvez vous connecter", Alert.AlertType.INFORMATION);
        } catch (InscriptionException | DejaInscritException e) {
            showMessage(e.getMessage(), Alert.AlertType.ERROR);
        }

    }


    @Override
    public void refresh() {

    }
}
