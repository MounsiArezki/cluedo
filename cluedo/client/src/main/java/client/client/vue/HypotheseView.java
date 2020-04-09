package client.client.vue;

import client.client.controleur.HypotheseControleur;
import client.client.modele.entite.carte.Arme;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.Lieu;
import client.client.modele.entite.carte.Personnage;
import client.client.modele.entite.io.ImagePath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HypotheseView extends View<HypotheseControleur> {

    @FXML
    public HBox personnages;
    @FXML
    public HBox armes;
    @FXML
    public HBox lieux;
    @FXML
    public HBox hypotheseEnCours;

    private Map<String, ICarte> hypothese;

    @Override
    public void refresh() throws IOException, InterruptedException {

    }

    public void setAllCards(){
        List<ICarte> listePersonnages = new ArrayList<>(List.of(Personnage.values()));
        List<ICarte> listeArmes = new ArrayList<>(List.of(Arme.values()));
        List<ICarte> listeLieux = new ArrayList<>(List.of(Lieu.values()));
        listeLieux.remove(Lieu.EXIT);

        initBox(personnages, listePersonnages);
        initBox(armes, listeArmes);
        initBox(lieux, listeLieux);

        hypothese = new HashMap<>();

    }

    public void initBox(HBox box, List<ICarte> liste){
        ObservableList<Button> observableListeCartes = FXCollections.observableArrayList();
        for(ICarte carte : liste){
            Image image = new Image(ImagePath.getImagePath(carte.getNom()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(67);
            imageView.setFitWidth(69);
            Button buttonImg = new Button();
            buttonImg.setGraphic(imageView);
            buttonImg.setId(carte.getNom());

            buttonImg.addEventHandler(
                    MouseEvent.MOUSE_CLICKED, (event) -> {
                        this.hypothese.put(carte.getNom(), carte);
                        hypotheseEnCours.getChildren().add(buttonImg);
                    }
            );
            observableListeCartes.add(buttonImg);
        }
        box.getChildren().addAll(observableListeCartes);
    }
}
