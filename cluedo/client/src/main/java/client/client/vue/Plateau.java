package client.client.vue;

import client.client.controleur.PlateauControleur;
import client.client.modele.entite.ICarte;
import client.client.vue.cluedoPlateau.CluedoBoard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Collection;

public class Plateau extends View<PlateauControleur> {

    @FXML
    public HBox Cartes;
    public Accordion feuilleDetective;
    public ImageView IconejoueurJ;
    public Label nomJoueurJ;
    public TextField desResultat;
    public CluedoBoard board;
    public Label etatPartie;



    ObservableList<Button> observableListCard=FXCollections.observableArrayList();


    public void distribuerCartes(){
        Collection<ICarte> listeCartes =getControleur().getCarteJoueur();
        for(ICarte carte : listeCartes){

            Image image = new Image(carte.getImageUrl());

            Button buttonImg = new Button();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(67);
            imageView.setFitWidth(69);
            buttonImg.setGraphic(imageView);
            observableListCard.add(buttonImg);
        }

        Cartes.getChildren().addAll(observableListCard);
    }


    public void lancerDesAction(ActionEvent actionEvent) {
    }

    public void passerAction(ActionEvent actionEvent) {
    }

    public void fermerAction(ActionEvent actionEvent){
        getControleur().retourMenu();
    }

    @Override
    public void refresh() {
        etatPartie.setText(getControleur().getPartie().getEtatPartie().getTexte());
    }

    public void drawCluedoBoard(){

        board.initializeGrid();
        board.draw();

    }




}
