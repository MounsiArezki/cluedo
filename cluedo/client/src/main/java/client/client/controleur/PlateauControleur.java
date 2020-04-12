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
import client.client.modele.entite.io.ImagePath;
import client.client.service.Facade;
import client.client.service.IJoueurService;
import client.client.service.IPartieService;
import client.client.vue.PlateauView;
import client.client.vue.cluedoPlateau.plateau.Board;
import client.client.vue.cluedoPlateau.player.Character;
import client.client.vue.cluedoPlateau.player.ImpossibleDeplacementException;
import client.client.vue.cluedoPlateau.player.Player;
import client.client.vue.place.LieuPlace;
import client.client.vue.place.Place;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.i18n.phonenumbers.AsYouTypeFormatter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;

public class PlateauControleur {

    Stage plateauStage;
    PlateauView plateauView;
    Partie partie;
    Player player;
    List<Character> characters;

    IPartieService partieService;
    IJoueurService joueurService;

    public PlateauControleur(Stage plateauStage, String idPartie) {
        this.plateauStage = plateauStage;
        this.partieService = Facade.getInstance();
        this.joueurService = Facade.getInstance();
        partie=new Partie();

        VariablesGlobales.setIdPartie(idPartie);
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

    public Player getPlayer() {
        return player;
    }

    public List<Character> getCharacters() {return characters;}

    public void goToHypothese(){
        Position p = partie.getJoueurs().get(VariablesGlobales.getUser().getId()).getPosition();
        Place place = getCluedoBoard().getItemFromCoordinate(p.getX(), p.getY());

        if(!(place instanceof LieuPlace)){
            plateauView.showMessage("Vous devez être dans un lieu pour émettre une hypothèse", Alert.AlertType.ERROR);
        }
        Lieu l = ((LieuPlace) place).getRoom();
        new HypotheseControleur(l, partie);
/*        Lieu l = Lieu.HALL;
        new HypotheseControleur(l, partie);*/

    }

    public void goToAccusation(){
        new AccusationControleur(partie);
    }

    public void revelerCarte(ICarte carte){
        try {
            joueurService.revelerCarte(partie.getId(), carte);
        } catch (JsonProcessingException e) {
            plateauView.showMessage("Vous ne pouvez pas révéler cette carte", Alert.AlertType.ERROR);
        }
    }

    public void retourMenu(){

        try {
            joueurService.quitterPartie(partie.getId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        VariablesGlobales.setIdPartie(null);
        new MenuControleur(plateauStage);
    }

    public void passerTour(){
        try {
            joueurService.passerTour(partie.getId());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ICarte> piocherIndice(){
        List<ICarte> indices = new ArrayList<>();
        try {
            indices = joueurService.piocherIndices(partie.getId());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            plateauView.showMessage("Erreur lors de la pioche d'indice", Alert.AlertType.ERROR);
        }
        return indices;
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
                System.out.println(j.getPersonnage().getNom());
                this.plateauView.iconeJoueur.setImage(new Image(ImagePath.getImagePath(j.getPersonnage().getNom())));
                System.out.println(ImagePath.getImagePath(j.getPersonnage().getNom()));
            }else {
                Player adversaire =new Player( this.plateauView, perso, getCluedoBoard().getItemFromCoordinate(p));
                this.characters.add( adversaire );
            }
        }
        for(Place[] places : this.getCluedoBoard().getGrid()) {
            for(Place place : places) {
                place.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                        {
                            boolean erreur =false;
                            Place placeTo =(Place) event.getTarget();

                            try {
                                this.getPlayer().moveTo(placeTo);
                            } catch (ImpossibleDeplacementException e) {
                                erreur=true;
                            }
                            if (!erreur){
                                try {
                                    joueurService.seDeplacer(getPartie().getId(), placeTo.getPositionOnGrid());
                                    getPlayer().setMoveAuthorisation(false);
                                } catch (JsonProcessingException e) {
                                    getPlateauView().showMessage("erreur dep serveur", Alert.AlertType.WARNING);
                                }
                            }

                        }
                );
            }
        }



        /*
        for(Place[] places : this.getCluedoBoard().getGrid()) {
            for(Place place : places) {
                place.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->
                {
                    boolean erreur =false;
                    Place placeTo =(Place) event.getTarget();
                    try {
                        joueurService.seDeplacer(getPartie().getId(), placeTo.getPositionOnGrid());
                    } catch (JsonProcessingException e) {
                        getPlateauView().showMessage("Erreur JSON", Alert.AlertType.WARNING);
                        erreur =true;
                    }catch (HttpStatusCodeException e){
                        erreur =true;
                    }
                    if (!erreur){
                        this.getPlayer().moveTo(placeTo);
                    }
                }
                );
            }
        }

         */
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
        Joueur j = this.partie.getJoueurs().get(VariablesGlobales.getUser().getId());
        getPlateauView().disableAll(true);
        try{
            isActif = j.equals(etat.obtenirJoueurAtif());
        } catch (UnsupportedOperationException e){
            System.out.println(e.getMessage());
        }
        try{
            isCourant = j.equals(etat.obtenirJoueurCourant());
            getPlayer().setMY_TURN(isCourant);
        } catch (UnsupportedOperationException e){
            System.out.println(e.getMessage());
        }
        if (isActif || isCourant){
            System.out.println("actif "+isActif);
            System.out.println("courant "+isCourant);
            List<Actions> actionsList = etat.obtenirActionsPossibles();
            for (Actions action : actionsList){
                System.out.println(action);
                switch (action){
                    case PASSER:
                        this.plateauView.disablePasser(false);
                        break;
                    case ACCUSER:
                        this.plateauView.disableAccusation(!isCourant);
                        break;
                    case DEPLACER:
                        break;
                    case LANCER_DES:
                        this.plateauView.disableDes(!isCourant);
                        break;
                    case JOUER_INDICE:
                        //désactivé en attendant implémentation des cartes indices
                        /*this.plateauView.disableCartesIndice(!isCourant);*/
                        break;
                    case REVELER_CARTE:
                        for (Button b : plateauView.getObservableListCard()){
                            if (partie.getEtatPartie().obtenirHypothese().containsValue(b.getUserData())){
                                System.out.println("Carte reconnue "+b.getUserData());
                                b.setDisable(false);
                            }
                        }
                        break;
                    case PIOCHER_INDICE:
                        //désactivé en attendant implémentation des cartes indices
                        /*this.plateauView.disablePiocheIndice(!isCourant);*/
                        break;
                    case EMETTRE_HYPOTHESE:
                        Place p = getCluedoBoard().getItemFromCoordinate(j.getPosition());
                        this.plateauView.disableHypothese(!isCourant);
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

    public Map<ICarte, Joueur> getFicheDetective(){
        Map<ICarte, Joueur> res = new HashMap<>();
        try {
            res = joueurService.getFicheDetective(partie.getId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
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
