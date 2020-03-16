package client.client.vue;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class View {



    private Stage stage;

    @FXML
    private Pane root;


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }



    public void show(String title){
        stage.setTitle(title);
        stage.setScene(new Scene(root));

        stage.show();

    }
    public void close(){
        stage.close();

    }
    public void showMessage(String message){

        Alert a=new Alert(Alert.AlertType.ERROR,message, ButtonType.OK);
        a.show();


    }








}
