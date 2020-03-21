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
import java.util.List;

public class CreerPartie extends View<CreerPartieControleur> {
    @FXML
    public TextField invite;
    @FXML
    public TextField nomPartie;
    
    @FXML
    public TableView joueurInviteTable;
    @FXML
    public TableView joueurTable;

    private List<User> joueursInvitesList=new ArrayList<>();

    private void setTableappearance() {
        joueurInviteTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        joueurInviteTable.setPrefWidth(300);
        joueurInviteTable.setPrefHeight(150);

        joueurTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        joueurTable.setPrefWidth(300);
        joueurTable.setPrefHeight(150);
    }

    private ObservableList<User> arrayToObserbableList(List<User> array) {
        ObservableList<User> data = FXCollections.observableArrayList();
        data.addAll(array);
        return data;
    }

    public void drawTableJoueur(List<User> dt){
        joueurTable.setItems(arrayToObserbableList(dt));

        TableColumn<User, String> colPseudo = new TableColumn<>("Pseudo");
        colPseudo.setCellValueFactory(new PropertyValueFactory<>("pseudo"));

        joueurTable.getColumns().addAll(colPseudo);

        addButtonToTableJoueur();
    }

    public void drawTableJoueurInvite(List<User> dt){
        joueurInviteTable.setItems(arrayToObserbableList(dt));

        TableColumn<User, String> colPseudo = new TableColumn<>("Pseudo");
        colPseudo.setCellValueFactory(new PropertyValueFactory<>("pseudo"));

        joueurInviteTable.getColumns().addAll(colPseudo);
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
                            joueursInvitesList.add(user);
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
        joueurTable.getColumns().addAll(colBtnInviter);
    }

    public void lancerPartieAction(ActionEvent actionEvent) {
        getControleur().goToPlateau();
    }

    public void retourAction(ActionEvent actionEvent) {
       getControleur().goToMenu();
    }

    @Override
    public void refresh() {
        List<User> listUsers= Arrays.asList(getControleur().getAllUsers());
        System.out.println(listUsers);
        drawTableJoueur(listUsers);
        drawTableJoueur(joueursInvitesList);
    }
}
