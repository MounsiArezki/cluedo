package client.client.vue.cluedoPlateau;
import client.client.modele.entite.Lieu;
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

            }
            y+=1;
        }
    }





}
