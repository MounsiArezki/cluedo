package client.client.vue;

import client.client.controleur.MenuControleur;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MenuView extends View<MenuControleur> {


    @FXML
    public TableView<Invitation> table;

     ObservableList<Invitation> dataObservable = FXCollections.observableArrayList();

    private void setTableappearance() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(300);
        table.setPrefHeight(300);
    }

    public void drawTable(List<Invitation> dt){
        dataObservable.setAll(dt);
        table.setItems(dataObservable);

        TableColumn<Invitation, String > partieId = new TableColumn<>("ID");
        partieId.setCellValueFactory(new PropertyValueFactory<>("idPartie"));

        TableColumn<Invitation, User> colHost = new TableColumn<>("host");
        colHost.setCellValueFactory(new PropertyValueFactory<>("hote"));

        table.getColumns().setAll(partieId, colHost);

        addButtonToTable();

    }


    private void addButtonToTable() {
        TableColumn<Invitation, Void> colBtnRejoindre = new TableColumn(" ");

        Callback<TableColumn<Invitation, Void>, TableCell<Invitation, Void>> cellFactoryRejoindre = new Callback<>() {
            @Override
            public TableCell<Invitation, Void> call(final TableColumn<Invitation, Void> param) {
                final TableCell<Invitation, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("rejoindre");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Invitation invitation = getTableView().getItems().get(getIndex());
                            getControleur().rejoindrePartie(invitation);
                            System.out.println("Partie "+invitation.getIdPartie()+" rejointe avec succès");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        TableColumn<Invitation, Void> colBtnRefuser = new TableColumn(" ");

        Callback<TableColumn<Invitation, Void>, TableCell<Invitation, Void>> cellFactoryRefuser = new Callback<>() {
            @Override
            public TableCell<Invitation, Void> call(final TableColumn<Invitation, Void> param) {
                final TableCell<Invitation, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("refuser");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Invitation invitation = getTableView().getItems().get(getIndex());
                            getControleur().refuserInvitation(invitation);
                            System.out.println("L'invitation "+invitation.getId()+" a été refusée" );
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtnRejoindre.setCellFactory(cellFactoryRejoindre);
        colBtnRefuser.setCellFactory(cellFactoryRefuser);

        table.getColumns().addAll(colBtnRejoindre,colBtnRefuser);
    }

    @FXML
    public void creerPartieAction(ActionEvent actionEvent) {
        // pour le momment juste la navigation
        getControleur().goToCreerPartie();
    }
    @FXML
    public void restaurerPartieAction(ActionEvent actionEvent) {
        getControleur().goToRestaurerPartie();

    }

    public void deconnexionAction(){
        this.getControleur().deconnexion();
    }

    @Override
    public void refresh() {
        List<Invitation> invitationsRecues = this.getControleur().getInvitationsRecues();
        drawTable(invitationsRecues);
    }
}
