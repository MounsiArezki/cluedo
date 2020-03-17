package client.client.vue;

import client.client.controleur.ConnexionControleur;
import client.client.modele.entite.io.FxmlUrl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class View<T> {



    private Stage stage;

    @FXML
    private Pane root;


    private T controleur;

    public T getControleur() {
        return controleur;
    }

    public void setControleur(T controleur) {
        this.controleur = controleur;
    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static View creerInstance(Stage stage,URL url){
        URL location = url ;
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root =null;
        try{
            root=(Parent) fxmlLoader.load() ;

        } catch (IOException e) {
            e.printStackTrace();
        }
        View vue =fxmlLoader.getController();
        vue.setStage(stage);
        return vue;

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

    public void deconnectionAction(ActionEvent actionEvent) {
    }

    public void fermerAction(ActionEvent actionEvent) {
    }






}
