package client.client.vue;

import client.client.controleur.CreerPartieControleur;
import client.client.modele.entite.Invitation;
import client.client.modele.entite.User;
import com.sun.jdi.ArrayReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CreerPartieView extends View<CreerPartieControleur> {

    @FXML
    public TextField recherche;
    @FXML
    public TableView joueurInviteTable;
    @FXML
    public TableView joueurTable;

    private List<User> joueursAInvites=new ArrayList<>();
    private List<User> joueursInvitesList=new ArrayList<>();

    ObservableList<User> observableListJoueurs=FXCollections.observableArrayList();
    ObservableList<User> observableListJoueursInvites=FXCollections.observableArrayList();

    private void setTableappearance() {
        joueurInviteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        joueurInviteTable.setPrefWidth(300);
        joueurInviteTable.setPrefHeight(150);

        joueurTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        joueurTable.setPrefWidth(300);
        joueurTable.setPrefHeight(150);
    }

    public void drawTableJoueur(List<User> dt){
        observableListJoueurs.removeAll(observableListJoueurs);
        observableListJoueurs.addAll(dt);
        joueurTable.setItems(observableListJoueurs);

        TableColumn<User, String> colPseudo = new TableColumn<>("pseudo");
        colPseudo.setCellValueFactory(new PropertyValueFactory<>("pseudo"));
        joueurTable.getColumns().setAll(colPseudo);

        addButtonToTableJoueur();
    }

    public void drawTableJoueurInvite(List<User> dt){
        observableListJoueursInvites.removeAll(observableListJoueursInvites);
        observableListJoueursInvites.addAll(dt);
        joueurInviteTable.setItems(observableListJoueursInvites);

        TableColumn<User, String> colPseudo = new TableColumn<>("Pseudo");
        colPseudo.setCellValueFactory(new PropertyValueFactory<>("pseudo"));

        joueurInviteTable.getColumns().setAll(colPseudo);
        addButtonToTableJoueurInvite();
    }


    private void addButtonToTableJoueur() {
        TableColumn<User, Void> colBtnInviter = new TableColumn(" ");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactoryRejoindre = new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("inviter");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            if(joueursInvitesList.size()<5){
                                joueursInvitesList.add(user);
                            }

                                refresh();

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

        colBtnInviter.setCellFactory(cellFactoryRejoindre);
        joueurTable.getColumns().addAll(colBtnInviter);
    }

    private void addButtonToTableJoueurInvite() {
        TableColumn<User, Void> colBtnInviter = new TableColumn(" ");

        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactoryRejoindre = new Callback<>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("annuler");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            User user = getTableView().getItems().get(getIndex());
                            joueursInvitesList.remove(user);
                            refresh();

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
        colBtnInviter.setCellFactory(cellFactoryRejoindre);
        joueurInviteTable.getColumns().addAll(colBtnInviter);
    }

    public void lancerPartieAction(ActionEvent actionEvent) {
        getControleur().lancerInvitation(joueursInvitesList);
    }

    public void retourAction(ActionEvent actionEvent) {
       getControleur().goToMenu();
    }


    @Override
    public void refresh() {
        joueursAInvites = new ArrayList<>(getControleur().getAllUsers(recherche.getText()));
        joueursAInvites.removeAll(joueursInvitesList);
        drawTableJoueur(joueursAInvites);
        drawTableJoueurInvite(joueursInvitesList);
    }
}
