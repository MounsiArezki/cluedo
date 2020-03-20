package client.client.vue.place;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Place extends Rectangle {


    public Place() {

        // TODORemove
        this.setOnMouseClicked(event -> addHighlight(Color.RED));
        this.setOpacity(0.5);
        // END
    }



    public void addHighlight(Paint fill) {
        addHighlight(fill, 0.4);
    }

    public void addHighlight(Paint fill, double opacity) {
        this.opacityProperty().set(opacity);
        this.setFill(fill);
    }



}
