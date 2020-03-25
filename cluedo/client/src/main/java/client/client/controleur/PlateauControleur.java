package client.client.controleur;

import client.client.modele.entite.*;
import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IPartieService;
import client.client.vue.Plateau;
import client.client.vue.cluedoPlateau.plateau.Board;
import client.client.vue.cluedoPlateau.player.Character;
import client.client.vue.cluedoPlateau.player.Player;
import client.client.vue.place.DepartPlace;
import client.client.vue.place.Place;
import javafx.stage.Stage;

import java.util.*;

public class PlateauControleur {

    Stage plateauStage;

    Plateau plateau;

    Partie partie;
    Player player ;

    IPartieService partieService;
    List<Character> characters ;
    //simulation facade api
    Collection<ICarte> cartesJ ;


    public PlateauControleur(Stage plateauStage, String idPartie) {
        this.cartesJ= List.of(Personnage.MOUTARDE,Personnage.OLIVE,Arme.CORDE,Arme.CLE,Arme.COUTEAU);
        this.plateauStage = plateauStage;
        this.partieService = new Facade();
        this.partie = partieService.getPartieById(idPartie);
        plateau = (Plateau)Plateau.creerInstance(plateauStage, FxmlPath.PLATEAU.getUrl());
        plateau.setControleur(this);
        plateau.refresh();
        plateau.setTimer(5);
        plateau.distribuerCartes();
        plateau.drawCluedoBoard();
        createCharacters();
        plateau.show("plateau");
    }


    public Collection<ICarte> getCarteJoueur() {
        return cartesJ;
    }


    public void afficherListeJoueurs(){

    }

    public Stage getPlateauStage() {
        return plateauStage;
    }

    public void setPlateauStage(Stage plateauStage) {
        this.plateauStage = plateauStage;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public Partie getPartie() {
        this.partie = partieService.getPartieById(partie.getId());
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

    public Collection<ICarte> getCartesJ() {
        return cartesJ;
    }

    public void setCartesJ(Collection<ICarte> cartesJ) {
        this.cartesJ = cartesJ;
    }

    public Board<Place> getCluedoBoard(){
        return plateau.getBoard();
    }

    public void retourMenu(){
        plateau.stopTimer();
        new MenuControleur(plateauStage);
    }

    private void createCharacters() {
        this.characters = new ArrayList<>();


        // recuperer les departplaces
        List<DepartPlace> startPlaces = new ArrayList<>();
        Place[][] grid = this.getCluedoBoard().getGrid();
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[y].length; x++) {
                if(!(grid[y][x] instanceof DepartPlace))
                    continue;
                startPlaces.add((DepartPlace) grid[y][x]);
            }
        }

        List<Lieu> rooms = new LinkedList<>(Arrays.asList(Lieu.values()));
        rooms.remove(Lieu.EXIT);
        List<Arme> weapons = new LinkedList<>(Arrays.asList(Arme.values()));
        List<Personnage> suspects = new LinkedList<>(Arrays.asList(Personnage.values()));

        // Generer une liste de joueurs
        List<Personnage> characters = new ArrayList<>(suspects);

        // Randomize rooms, weapons, and suspects
        Collections.shuffle(rooms);
        Collections.shuffle(weapons);
        Collections.shuffle(suspects);
        // TO DO chosen suspect


        // creer joueur
        this.player = new Player(
                this.plateau,
                suspects.get(0),
                startPlaces.get(0)
        );

        this.characters.add(
                this.player
        );

        // TO DO ajout autres joueurs


    }




}
