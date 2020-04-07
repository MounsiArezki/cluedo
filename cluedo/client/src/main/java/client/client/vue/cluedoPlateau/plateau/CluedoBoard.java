package client.client.vue.cluedoPlateau.plateau;
import client.client.modele.entite.carte.Lieu;
import client.client.modele.entite.Position;
import client.client.modele.entite.io.TextStream;
import client.client.vue.cluedoPlateau.Key.DirectionKey;
import client.client.vue.cluedoPlateau.Key.PlaceKey;
import client.client.vue.place.*;

import java.io.InputStream;
import java.util.Scanner;


public class CluedoBoard extends Board<Place> {

    public CluedoBoard() {
        super();
        this.setWidth(750);
        this.setHeight(620);
    }
/*
    private void initializeGrid() {
        String[] map = TextStream.PARSE.apply(TextStream.CLUEDOBOARD.getInputStream());
        super.grid = new Place[map.length][map[0].length()/2];
        int y = 0;
        for(String line : map) {
            int x = -1;
            for(int i = 0; i < line.length(); i += 2) {
                x+=1;

                super.grid[y][x] = new BasicPlace();

            }
            y+=1;
        }

    }
    */

    public void initializeGrid() {
        InputStream mapStream = TextStream.CLUEDOBOARD.getInputStream();
        Scanner s = new Scanner(mapStream).useDelimiter("\\A");
        String rawMap = s.hasNext() ? s.next() : "";
        String[] map = rawMap.split("\\r?\\n");

        super.grid = new Place[map.length][map[0].length()/2];

        int y = 0;
        for(String line : map) {
            int x = -1;
            for(int i = 0; i < line.length(); i += 2) {
                x+=1;
                switch(PlaceKey.getPlace(line.charAt(i))) {
                    case PATH:
                        super.grid[y][x] = new BasicPlace();
                        break;
                    case UNREACHABLE:
                        super.grid[y][x] = new Place(DirectionKey.ALL, false, 1);
                        break;
                    case START:
                        super.grid[y][x] = new DepartPlace();
                        break;
                }

                //Continuer si la grille est déjà remplie
                if(PlaceKey.getPlace(line.charAt(i)) != PlaceKey.OTHER)
                    continue;

                // Is upper
                if(line.charAt(i) > 64 && line.charAt(i) < 91) {
                    super.grid[y][x] = new TeleportPlace(DirectionKey.getDirection(line.charAt(i+1)), Lieu.getLieu(line.charAt(i)));

                }
                // Is lowercase (lieu/porte)
                else if (line.charAt(i) > 96 && line.charAt(i) < 123) {
                    // Is porte
                    if(line.charAt(i+1) != DirectionKey.ALL.getKey())
                        super.grid[y][x] = new PortePlace(DirectionKey.getDirection(line.charAt(i+1)), Lieu.getLieu(line.charAt(i)));
                    // Is lieu
                    else
                        super.grid[y][x] = new LieuPlace(Lieu.getLieu(line.charAt(i)));
                }

            }
            y+=1;
        }
    }

    public void calcAdjacent() {
        for(int y = 0; y < super.grid.length; y++) {
            for(int x = 0; x < super.grid[y].length; x++) {

                // Possible direction
                Position[] shifts = new Position[] {
                        new Position(0,-1),
                        new Position(1, 0),
                        new Position(0, 1),
                        new Position(-1, 0)
                };

                //Emplacements adjacents possibles
                Place[] adjacents = new Place[5];

                //Ajoute pos téléportation les endroits téléportables
                if(grid[y][x] instanceof Teleportable) {
                    adjacents[4] = super.getItemFromCoordinate(((Teleportable) grid[y][x]).teleportTo());
                }

                int i = -1;
                for(Position shift : shifts) {
                    i++;

                    // Check if it is theoretically possible to move to adjacent location
                    if(!grid[y][x].getDirection().isOpen(i))
                        continue;

                    // Create adjacent location if it exists
                    try {
                        Place adj = super.getItemFromCoordinate(x + shift.getX(), y + shift.getY());

                        if(adj == null)
                            continue;

                        if(!adj.isReachable())
                            continue;


                        //Continuez si LieuPlace ne se connecte pas avec LieuPlace
                        if(!(grid[y][x] instanceof PortePlace) && grid[y][x] instanceof LieuPlace && !(adj instanceof LieuPlace))
                            continue;

                        // Continuez si basicPlace ne se connecte pas avec BasicPlace ou portePlace
                        if(grid[y][x] instanceof BasicPlace && !(adj instanceof BasicPlace || adj instanceof PortePlace))
                            continue;

                        // Ajouter place à l'extérieur de la porte au lieu de la porte elle-même
                        if(grid[y][x] instanceof LieuPlace && adj instanceof PortePlace)
                            adj = super.getItemFromCoordinate(x + shift.getX()*2, y + shift.getY()*2);


                        // Continuez si une porte est adjacente mais pointe dans la mauvaise direction
                        if(adj instanceof PortePlace && !adj.getDirection().isOpen((i + 2) % 4)) {
                            continue;
                        }

                        adjacents[i] = adj;
                    } catch (IndexOutOfBoundsException e) {}
                }

                // Add adjacent places or null
                grid[y][x].setAdjacent(adjacents);
            }
        }
    }



}
