package client.client.vue;

import client.client.controleur.PlateauControleur;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.etat_partie.Actions;
import client.client.modele.entite.etat_partie.IEtatPartie;
import client.client.modele.entite.io.ImagePath;
import client.client.vue.cluedoPlateau.plateau.CluedoBoard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.Collection;
import java.util.List;

import static client.client.modele.entite.etat_partie.Actions.LANCER_DES;

public class PlateauView extends View<PlateauControleur> {

    @FXML
    public HBox Cartes;
    public Accordion feuilleDetective;
    public ImageView IconejoueurJ;
    public Label nomJoueurJ;
    public TextField desResultat;

    public CluedoBoard board;
    public Label etatPartie;
    public Button lancerBtn;
    public Button hypoBtn;
    public Button accusBtn;


    public ObservableList<Button> getObservableListCard() {
        return observableListCard;
    }

    ObservableList<Button> observableListCard=FXCollections.observableArrayList();


    public void distribuerCartes(){
        Collection<ICarte> listeCartes = getControleur().getMyCard();

        for(ICarte carte : listeCartes){
            Image image = new Image(ImagePath.getImagePath(carte.getNom()));
            Button buttonImg = new Button();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(67);
            imageView.setFitWidth(69);
            buttonImg.setGraphic(imageView);
            buttonImg.setId(carte.getNom());


            buttonImg.addEventHandler(
                    MouseEvent.MOUSE_CLICKED, (event) -> getControleur().getPlayer()
                            .passCard(
                                    event.getPickResult().getIntersectedNode().getId()
                            )
            );

            observableListCard.add(buttonImg);
        }
        Cartes.getChildren().removeAll();
        Cartes.getChildren().addAll(observableListCard);
    }


    public void lancerDesAction(ActionEvent actionEvent) {
        if(getControleur().getPlayer().isMY_TURN()){
            int res = getControleur().roll();
            this.desResultat.setText(Integer.toString(res));
        }else {
            this.showMessage("Not your turn !", Alert.AlertType.WARNING);
        }
    }

    public void goHypotheseAction(ActionEvent actionEvent){
        getControleur().goToHypothese();
    }

    public void goAccusationAction(ActionEvent actionEvent) {getControleur().goToAccusation();}

    public void passerAction(ActionEvent actionEvent) {
    }

    public void fermerAction(ActionEvent actionEvent){
        getControleur().retourMenu();
    }

    @Override
    public void refresh() {
        IEtatPartie etat = getControleur().getPartie().getEtatPartie();
        try {
            distribuerCartes();
            getControleur().createCharacters();
        } catch (UnsupportedOperationException e){
            System.out.println("La partie n'est pas dans un état permettant la distribution des cartes");
        }
        etatPartie.setText(etat.obtenirTexte());
    }





    public void drawCluedoBoard(){
        board.initializeGrid();
        board.calcAdjacent();
        board.draw();
    }

    public CluedoBoard getBoard() {
        return board;
    }

    public void desableButtons(boolean ok){
            lancerBtn.setDisable(ok);
            hypoBtn.setDisable(ok);
            accusBtn.setDisable(ok);
            Cartes.setDisable(ok);
    }

    public void desableDes(boolean ok){lancerBtn.setDisable(ok);}
    public void desableHypBtn(boolean ok){ hypoBtn.setDisable(ok);}
    public void desableAcuss(boolean ok){ hypoBtn.setDisable(ok); }
    public void desableCartes(Boolean ok){Cartes.setDisable(ok);}



}
