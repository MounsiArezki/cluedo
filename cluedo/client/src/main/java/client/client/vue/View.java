package client.client.vue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public abstract class View<T> {

    private Stage stage;
    private Timeline timeline;

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
    public void showMessage(String message, Alert.AlertType type){
        Alert a=new Alert(type,message, ButtonType.OK);
        a.show();
    }

    public void deconnectionAction(ActionEvent actionEvent) {
    }

    public void fermerAction(ActionEvent actionEvent) {
    }

    public abstract void refresh() throws IOException, InterruptedException;

    public void setTimer(int seconds){

        timeline = new Timeline(new KeyFrame(Duration.seconds(seconds), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    refresh();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void stopTimer(){
        timeline.stop();
        //timeline = null;
    }

}
