package client.client.vue;

import client.client.controleur.CreerPartieControleur;
import client.client.modele.entite.Invitation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    ObservableList<Invitation> dataObservable = FXCollections.observableArrayList();

    private void setTableappearance() {
        joueurInviteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        joueurInviteTable.setPrefWidth(300);
        joueurInviteTable.setPrefHeight(300);
    }





    public void lancerPartieAction(ActionEvent actionEvent) {
        getControleur().goToPlateau();
    }

    public void retourAction(ActionEvent actionEvent) {
       getControleur().goToMenu();
    }

    @Override
    public void refresh() {

    }
}
