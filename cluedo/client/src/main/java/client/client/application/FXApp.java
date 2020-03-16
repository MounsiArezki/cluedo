package client.client.application;

import client.client.controleur.ConnexionControleur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

       ConnexionControleur connexionControleur = new ConnexionControleur(primaryStage);
    //    primaryStage.setScene(new Scene(new Pane(new TextArea("dd")),800,600));
      //  primaryStage.show();

    }
}