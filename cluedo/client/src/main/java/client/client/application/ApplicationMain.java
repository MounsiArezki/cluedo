package client.client.application;

import client.client.controleur.ConnexionControleur;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ConnexionControleur connexionControleur = new ConnexionControleur(primaryStage);

    }
}