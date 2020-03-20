package webservice.webservice.DTO.entite;

import webservice.webservice.modele.entite.Position;
import webservice.webservice.modele.entite.carte.Lieu;

public class PositionDTO {

    Lieu lieu;
    int x;
    int y;

    public PositionDTO(){}

    public static PositionDTO creer(Position position){
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setLieu(position.getLieu());
        positionDTO.setX(position.getX());
        positionDTO.setY(position.getY());
        return positionDTO;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
