package client.client.vue.cluedoPlateau.plateau;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Board<T extends Rectangle> extends Pane {


    protected T[][] grid;

    public T[][] getGrid() {
        return grid;
    }


    public void draw() {

        if(grid == null)
            return;
        for(int y = 0; y < grid.length; y++) {
            if(grid[y] == null)
                continue;
            for(int x = 0; x < grid[y].length; x++) {
                if(grid[y][x] == null)
                    continue;
                grid[y][x].setHeight(this.getHeight()/grid.length);
                grid[y][x].setWidth(this.getWidth()/grid[0].length);
                grid[y][x].setX(grid[y][x].getWidth()*x);
                grid[y][x].setY(grid[y][x].getHeight()*y);
                this.getChildren().add(grid[y][x]);
            }
        }
    }









}
