package client.client.vue;

import client.client.controleur.PlateauControleur;
import client.client.global.VariablesGlobales;
import client.client.modele.entite.Partie;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.etat_partie.Actions;
import client.client.modele.entite.etat_partie.EnAttenteDesJoueurs;
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

public class PlateauView extends View<PlateauControleur> {

    @FXML
    public HBox cartes;
    public HBox cartesIndices;
    public Accordion feuilleDetective;
    public ImageView iconeJoueur;
    public Label nomJoueurJ;
    public TextField desResultat;
    public Button accusationButton;
    public Button hypotheseButton;
    public CluedoBoard board;
    public Label etatPartieLabel;
    public Button lancerDesButton;
    public Button passerButton;
    public Button piocherIndiceButton;

    private boolean init=false;

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
        cartes.getChildren().removeAll();
        cartes.getChildren().addAll(observableListCard);
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
        getControleur().passerTour();
    }

    public void piocherIndiceAction(ActionEvent actionEvent) {
        System.out.println(getControleur().getPartie().getEtatPartie());
        System.out.println("avant pioche");
        List<ICarte> indices = getControleur().piocherIndice();
        System.out.println("après pioche");
        for (ICarte c : indices){
            ImageView img = new ImageView(ImagePath.getImagePath(c.getNom()));
            img.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    (event) -> cartesIndices.getChildren().remove(img));
            cartesIndices.getChildren().add(img);
        }
    }

    public void fermerAction(ActionEvent actionEvent){
        getControleur().retourMenu();
    }

    @Override
    public void refresh() {
        //desactive les boutons sauf le bouton quitter
        disableAll(true);

        Partie partie= getControleur().getPartie();
        IEtatPartie etat = partie.getEtatPartie();
        System.out.println(etat);

        if(!init){

            if(!(etat instanceof EnAttenteDesJoueurs)){
                distribuerCartes();
                getControleur().createCharacters();
                nomJoueurJ.setText(getControleur().getPlayer().getPersonnage().getNom()+" ("+ VariablesGlobales.getUser().getPseudo()+" )");
                init=true;
            }
        }
        getControleur().gestionAction();
        etatPartieLabel.setText(etat.obtenirTexte());
    }

    public void drawCluedoBoard(){
        board.initializeGrid();
        board.calcAdjacent();
        board.draw();
    }

    public CluedoBoard getBoard() {
        return board;
    }

    public void disableDes(boolean ok){ lancerDesButton.setDisable(ok); }
    public void disableHypothese(boolean ok){ hypotheseButton.setDisable(ok); }
    public void disableAccusation(boolean ok){ accusationButton.setDisable(ok); }
    public void disableCartes(Boolean ok){ cartes.setDisable(ok); }
    public void disablePasser(Boolean ok){ passerButton.setDisable(ok); }
    public void disablePiocheIndice(Boolean ok){ piocherIndiceButton.setDisable(ok); }
    public void disableCartesIndice(Boolean ok){ cartesIndices.setDisable(ok); }

    public  void disableAll(boolean ok ){
        disableDes(ok);
        disableHypothese(ok);
        disableAccusation(ok);
        disableCartes(ok);
        disablePasser(ok);
        disablePiocheIndice(ok);
        disableCartesIndice(ok);

    }

}
