package client.client.controleur;

import client.client.global.VariablesGlobales;
import client.client.modele.entite.Joueur;
import client.client.modele.entite.Partie;
import client.client.modele.entite.Position;
import client.client.modele.entite.carte.ICarte;
import client.client.modele.entite.carte.Lieu;
import client.client.modele.entite.carte.Personnage;
import client.client.modele.entite.etat_partie.Actions;
import client.client.modele.entite.etat_partie.IEtatPartie;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IJoueurService;
import client.client.service.IPartieService;
import client.client.vue.PlateauView;
import client.client.vue.cluedoPlateau.plateau.Board;
import client.client.vue.cluedoPlateau.player.Character;
import client.client.vue.cluedoPlateau.player.Player;
import client.client.vue.place.LieuPlace;
import client.client.vue.place.Place;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.web.client.HttpStatusCodeException;

import java.lang.reflect.Array;
import java.util.*;

public class PlateauControleur {

    Stage plateauStage;
    PlateauView plateauView;
    Partie partie;
    Player player;

    IPartieService partieService;
    IJoueurService joueurService;

    public Player getPlayer() {
        return player;
    }

    List<Character> characters;
    //simulation get autres joueurs partie
    Collection<Joueur> joueursPartie;

    public PlateauControleur(Stage plateauStage, String idPartie) {
        this.plateauStage = plateauStage;
        this.partieService = Facade.getInstance();
        this.joueurService = Facade.getInstance();
        partie=new Partie();

        try {
            partieService.subscribeFluxPartie(idPartie, this::consumeFluxPartie);
        }
        catch (JsonProcessingException e) {
            plateauView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            plateauView.showMessage("Erreur "+e.getStatusCode(), Alert.AlertType.ERROR);
        }

        plateauView = (PlateauView) PlateauView.creerInstance(plateauStage, FxmlPath.PLATEAU.getUrl());
        plateauView.setControleur(this);
        plateauView.drawCluedoBoard();
        plateauView.show("plateau");
    }

    public void afficherListeJoueurs(){

    }

    public Collection<ICarte> getMyCard(){
        Collection<ICarte> cartesJ= new ArrayList<>();
        try {
            cartesJ = joueurService.getCartesJoueurs(partie.getId());
        }
        catch (JsonProcessingException e) {
            plateauView.showMessage("Erreur Json", Alert.AlertType.ERROR);
        }
        catch (HttpStatusCodeException e) {
            plateauView.showMessage("Erreur "+e.getStatusCode(), Alert.AlertType.ERROR);
        }
        System.out.println(cartesJ.toString());
        return cartesJ;
    }

    public Stage getPlateauStage() {
        return plateauStage;
    }

    public void setPlateauStage(Stage plateauStage) {
        this.plateauStage = plateauStage;
    }

    public PlateauView getPlateauView() {
        return plateauView;
    }

    public void setPlateauView(PlateauView plateauView) {
        this.plateauView = plateauView;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public IPartieService getPartieService() {
        return partieService;
    }

    public void setPartieService(IPartieService partieService) {
        this.partieService = partieService;
    }

    public Board<Place> getCluedoBoard(){
        return plateauView.getBoard();
    }

    public void goToHypothese(){
        Position p = partie.getJoueurs().get(VariablesGlobales.getUser().getId()).getPosition();
        Place place = getCluedoBoard().getItemFromCoordinate(p.getX(), p.getY());

        if(!(place instanceof LieuPlace)){
            plateauView.showMessage("Vous devez être dans un lieu pour émettre une hypothèse", Alert.AlertType.ERROR);
        }
        Lieu l = ((LieuPlace) place).getRoom();
        new HypotheseControleur(l, partie);
    /*    Lieu l = Lieu.HALL;
        new HypotheseControleur(l, partie);

     */

    }

    public void goToAccusation(){
        new AccusationControleur(partie);
    }

    public void retourMenu(){
        new MenuControleur(plateauStage);
    }

    public void passerTour(){
        try {
            joueurService.passerTour(partie.getId());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createCharacters() {
        this.characters = new ArrayList<>();

        for (Joueur j : partie.getJoueurs().values()){
            //recup personnage
            Personnage perso = (Personnage) j.getPersonnage();
            //recup position
            Position p = j.getPosition();
            if (j.getUser().getId().equals(VariablesGlobales.getUser().getId())){

                this.player = new Player( this.plateauView, perso, getCluedoBoard().getItemFromCoordinate(p));
                this.characters.add( this.player );
            }else {
                Player adversaire =new Player( this.plateauView, perso, getCluedoBoard().getItemFromCoordinate(p));
                this.characters.add( adversaire );
            }
        }

        for(Place[] places : this.getCluedoBoard().getGrid()) {
            for(Place place : places) {
                place.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                {
                    Place placeTo =(Place) event.getTarget();
                    this.getPlayer().moveTo(placeTo);
                    try {
                        System.out.println("deppppppppppp"+placeTo.getPositionOnGrid());
                        joueurService.seDeplacer(getPartie().getId(),placeTo.getPositionOnGrid());
                    } catch (JsonProcessingException e) {
                        getPlateauView().showMessage("erreur parsing ", Alert.AlertType.WARNING);

                    }

                }

                );
            }
        }
    }

    public void updatePlayersPosition(){
        System.out.println("update pos");
        Collection<Joueur> joueurs = getPartie().getJoueurs().values();
        for (Joueur J : joueurs){
           characters.stream().forEach(player->
           {
               if(player.getPersonnage().getNom().equals(J.getPersonnage().getNom())){
                   System.out.println("moving :"+J.getUser().getPseudo()+"to "+J.getPosition() );

                 player.moveFromServer(getCluedoBoard().getItemFromCoordinate(J.getPosition()));
               }

           });
        }
    }


    public void gestionAction(){

        boolean isActif = false;
        boolean isCourant = false;
        updatePlayersPosition();
        IEtatPartie etat = this.partie.getEtatPartie();
     //   Joueur j = etat.obtenirJoueurCourant();
        getPlateauView().disableAll(true);
        try{
            isActif = this.partie.getJoueurs().get(VariablesGlobales.getUser().getId()).equals(etat.obtenirJoueurAtif());
        } catch (UnsupportedOperationException e){
            System.out.println(e.getMessage());
        }
        try{
            isCourant = this.partie.getJoueurs().get(VariablesGlobales.getUser().getId()).equals(etat.obtenirJoueurCourant());
            getPlayer().setMY_TURN(isCourant);
        } catch (UnsupportedOperationException e){
            System.out.println(e.getMessage());
        }
        if (isActif || isCourant){
            List<Actions> actionsList = etat.obtenirActionsPossibles();
            for (Actions action : actionsList){
                System.out.println(action);
                switch (action){
                    case PASSER:
                        this.plateauView.disablePasser(false);
                        break;
                    case ACCUSER:
                        this.plateauView.disableAccusation(false);
                        break;
                    case DEPLACER:
                        break;
                    case LANCER_DES:
                        this.plateauView.disableDes(false);
                        // activer les bouttons
                        break;
                    case JOUER_INDICE:
                        break;
                    case REVELER_CARTE:
                        this.plateauView.disableCartes(false);
                        break;
                    case PIOCHER_INDICE:
                        break;
                    case EMETTRE_HYPOTHESE:
                        this.plateauView.disableHypothese(false);
                        break;
                }
            }



        }

    }






    public Integer roll() {
        Integer res = 0;
        try {
            res = 0;
            for (Integer i : joueurService.lancerDes(partie.getId())){
                res += i;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return this.player.lancerDes(res);
    }

    public void consumeFluxPartie(Partie partie){
        Platform.runLater( () ->{
            if (partie != null) {
                this.partie=partie;
                plateauView.refresh();
            }
        });
    }
}
