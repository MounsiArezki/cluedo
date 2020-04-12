package client.client.application;

import client.client.controleur.ConnexionControleur;
import client.client.global.VariablesGlobales;
import client.client.service.Facade;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //start
    @Override
    public void start(Stage primaryStage) throws Exception {

       ConnexionControleur connexionControleur = new ConnexionControleur(primaryStage);
    //    primaryStage.setScene(new Scene(new Pane(new TextArea("dd")),800,600));
      //  primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        Facade facade=new Facade();
        if(VariablesGlobales.getIdPartie()!=null){
            facade.quitterPartie(VariablesGlobales.getIdPartie());
        }
        if(VariablesGlobales.getUser()!=null){
            facade.deconnexion(VariablesGlobales.getUser().getId());
        }
        super.stop();

    }
}