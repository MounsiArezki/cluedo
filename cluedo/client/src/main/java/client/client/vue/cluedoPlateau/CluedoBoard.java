package client.client.vue.cluedoPlateau;


import client.client.modele.entite.io.TextStream;
import client.client.vue.place.Place;


public class CluedoBoard extends Board<Place> {


    public CluedoBoard() {


        super();
        this.setWidth(750);
        this.setHeight(470);
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
                super.grid[y][x] = new Place();

            }
            y+=1;
        }

    }




}
