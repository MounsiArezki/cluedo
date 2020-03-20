package client.client.vue.place;

import client.client.modele.entite.Position;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Place extends Rectangle {


    public Place() {

        // TODORemove
        this.setOnMouseClicked(event -> {
            addHighlight(Color.RED);
            Alert a=new Alert(Alert.AlertType.INFORMATION,this.getCenter().toString(), ButtonType.OK);
            a.show();});
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




    public Position getCenter() {
        return new Position((int) (this.getX() + (this.getWidth()/2)), (int) (this.getY() + (this.getHeight()/2)));
    }


}
