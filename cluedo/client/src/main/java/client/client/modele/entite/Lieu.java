package client.client.modele.entite;

public enum Lieu implements ICarte{

    HALL("Hall",  'h', new Position(11, 3)),
    LOUNGE("Lounge", 'l', new Position(20, 2)),
    DINING_ROOM("Dining Room", 'd', new Position(20, 11)),
    KITCHEN("Kitchen", 'k', new Position(22, 23)),
    BALL_ROOM("Ball Room",  'a', new Position(11, 20)),
    CONSERVATORY("Conservatory",  'c', new Position(2, 20)),
    BILLIARD_ROOM("Billiard Room",  'b', new Position(2, 14)),
    LIBRARY("Library",  'i', new Position(3, 8)),
    STUDY("Study",  's', new Position(3, 1)),
    EXIT("Exit",  'e', new Position(11, 11))
    ;

    private String nom;
    private char key;
    private Position center;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public Position getCenter() {
        return center;
    }
    Lieu(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public void ImageUrl(String url) {
        this.imageUrl=url;
    }


    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }



    Lieu(String name,  char key, Position centre) {

        this.nom = name;
        this.key = key;
        this.center = centre;
    }

    public static Lieu getLieu(char key) {
        for(Lieu lieu : Lieu.values()) {
            if(lieu.getKey() == key || (char) (key + 32) == lieu.getKey())
                return lieu;
        }
        return null;
    }


}
