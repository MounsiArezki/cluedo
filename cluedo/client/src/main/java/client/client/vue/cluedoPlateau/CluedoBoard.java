package client.client.vue.cluedoPlateau;


import client.client.modele.entite.Lieu;
import client.client.modele.entite.io.TextStream;
import client.client.vue.cluedoPlateau.Key.DirectionKey;
import client.client.vue.cluedoPlateau.Key.PlaceKey;
import client.client.vue.place.BasicPlace;
import client.client.vue.place.DepartPlace;
import client.client.vue.place.Place;
import client.client.vue.place.TeleportPlace;
import javafx.geometry.Insets;

import java.io.InputStream;
import java.util.Scanner;


public class CluedoBoard extends Board<Place> {


    public CluedoBoard() {


        super();
        this.setWidth(750);
        this.setHeight(620);


        initializeGrid();
        draw();


    }

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




}
