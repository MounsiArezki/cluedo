package client.client.vue;

import client.client.controleur.HypotheseControleur;
import client.client.modele.entite.carte.*;
import client.client.modele.entite.io.ImagePath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HypotheseView extends View<HypotheseControleur> {

    @FXML
    public HBox personnages;
    @FXML
    public HBox armes;
    @FXML
    public HBox lieux;
    @FXML
    public HBox hypotheseEnCours;

    private Lieu lieu;

    private List<String> hypothese;

    @Override
    public void refresh() throws IOException, InterruptedException {

    }

    public void setAllCards(){
        List<ICarte> listePersonnages = new ArrayList<>(List.of(Personnage.values()));
        List<ICarte> listeArmes = new ArrayList<>(List.of(Arme.values()));

        initBox(personnages, listePersonnages);
        initBox(armes, listeArmes);
        initBox(lieux, List.of(lieu));

        hypothese = new ArrayList<>();

    }

    public void initBox(HBox box, List<ICarte> liste){
        ObservableList<Button> observableListeCartes = FXCollections.observableArrayList();
        for(ICarte carte : liste){
            Image image = new Image(ImagePath.getImagePath(carte.getNom()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            Button buttonImg = new Button();
            buttonImg.setGraphic(imageView);
            buttonImg.setId(carte.getNom());

            buttonImg.addEventHandler(
                    MouseEvent.MOUSE_CLICKED, (event) -> {
                        this.hypothese.add(carte.getNom());
                        hypotheseEnCours.getChildren().add(buttonImg);
                        for (Button b : observableListeCartes){
                            b.setDisable(true);
                        }
                    }
            );
            observableListeCartes.add(buttonImg);
        }
        box.getChildren().addAll(observableListeCartes);
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public void emettreHypotheseAction(ActionEvent actionEvent) {
        getControleur().emettreHypothese(hypothese);
    }
}
