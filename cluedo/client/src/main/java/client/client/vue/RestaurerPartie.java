package client.client.vue;

import client.client.controleur.RestaurerPartieControleur;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class RestaurerPartie extends View<RestaurerPartieControleur>{


    public TableView PartieEncourqTable;


    public void retourAction(ActionEvent actionEvent) {
        getControleur().goToMenu();
    }

    @Override
    public void refresh() {

    }
}
