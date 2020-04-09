package client.client.vue;

import client.client.controleur.PlateauControleur;
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

public class PlateauView extends View<PlateauControleur> {

    @FXML
    public HBox Cartes;
    public Accordion feuilleDetective;
    public ImageView IconejoueurJ;
    public Label nomJoueurJ;
    public TextField desResultat;

    public CluedoBoard board;
    public Label etatPartie;

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
        Partie partie= getControleur().getPartie();

        if(!init){
            if(!(partie.getEtatPartie() instanceof EnAttenteDesJoueurs)){
                IEtatPartie etat = getControleur().getPartie().getEtatPartie();
                try {
                    etat.obtenirJoueurCourant();
                    distribuerCartes();
                    getControleur().createCharacters();
                } catch (UnsupportedOperationException e){
                    System.out.println("La partie n'est pas dans un état permettant la distribution des cartes");
                }
                etatPartie.setText(getControleur().getPartie().getEtatPartie().obtenirTexte());
                init=true;
            }
        }

        //manque le refresh de certaines parties du plateau, position perso si etat instance of supputation, ...

        //desactive les boutons sauf le bouton quitter
        desactiverToutesActions();

        //active les boutons des actions disponibles
        activerActions(partie);

    }

    private void desactiverToutesActions(){

    }

    private void activerActions(Partie partie){
        for(Actions actions: partie.getEtatPartie().obtenirActionsPossibles()){
            switch (actions){
                case PASSER:
                    break;
                case ACCUSER:
                    break;
                case DEPLACER:
                    break;
                case LANCER_DES:
                    break;
                case JOUER_INDICE:
                    break;
                case REVELER_CARTE:
                    break;
                case PIOCHER_INDICE:
                    break;
                case EMETTRE_HYPOTHESE:
                    break;
            }
        }
    }





    public void drawCluedoBoard(){
        board.initializeGrid();
        board.calcAdjacent();
        board.draw();
    }

    public CluedoBoard getBoard() {
        return board;
    }


}