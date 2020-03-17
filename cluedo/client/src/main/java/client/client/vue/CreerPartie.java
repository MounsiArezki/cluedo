package client.client.vue;

import client.client.controleur.CreerPartieControleur;
import client.client.modele.entite.Invitation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CreerPartie extends View<CreerPartieControleur> {
    @FXML
    public TextField invite;
    @FXML
    public TextField nomPartie;
    
    @FXML
    public TableView joueurInviteTable;
    @FXML
    public TableView joueurTable;


    public void lancerPartieAction(ActionEvent actionEvent) {
    }


    public void retourAction(ActionEvent actionEvent) {

       getControleur().goToMenu();

    }
}
