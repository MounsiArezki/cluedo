package client.client.modele.entite.io;

import client.client.modele.entite.carte.Arme;
import client.client.modele.entite.carte.Lieu;
import client.client.modele.entite.carte.Personnage;

import java.util.HashMap;
import java.util.Map;

public class ImagePath {

    private static Map<String, String> imagePaths = new HashMap<>();
    static {

        imagePaths.put("Plateau","/image/cluedo.png");

        imagePaths.put(Arme.COUTEAU.getNom(), "/image/Arme/COUTEAU.jpg");
        imagePaths.put(Arme.CHANDELIER.getNom(), "/image/Arme/CHANDELIER.jpg");
        imagePaths.put(Arme.CLE_ANGLAISE.getNom(), "/image/Arme/CLE.jpg");
        imagePaths.put(Arme.CORDE.getNom(), "/image/Arme/CORDE.jpg");
        imagePaths.put(Arme.TUYAU_DE_PLOMB.getNom(), "/image/Arme/TUYAU_EN_PLOMB.jpg");
        imagePaths.put(Arme.REVOLVER.getNom(), "/image/Arme/REVOLVER.jpg");

        imagePaths.put(Lieu.HALL.getNom(), "/image/Lieu/hall.jpg");
        imagePaths.put(Lieu.CONSERVATORY.getNom(), "/image/Lieu/conservatory.jpg");
        imagePaths.put(Lieu.KITCHEN.getNom(), "/image/Lieu/kitchen.jpg");
        imagePaths.put(Lieu.DINING_ROOM.getNom(), "/image/Lieu/diningroom.jpg");
        imagePaths.put(Lieu.BALL_ROOM.getNom(), "/image/Lieu/ballroom.jpg");
        imagePaths.put(Lieu.BILLIARD_ROOM.getNom(), "/image/Lieu/billiardroom.jpg");
        imagePaths.put(Lieu.LIBRARY.getNom(), "/image/Lieu/library.jpg");
        imagePaths.put(Lieu.LOUNGE.getNom(), "/image/Lieu/lounge.jpg");
        imagePaths.put(Lieu.STUDY.getNom(), "/image/Lieu/study.jpg");

        imagePaths.put(Personnage.ROSE.getNom(), "/image/Personnage/ROSE.jpg");
        imagePaths.put(Personnage.MOUTARDE.getNom(), "/image/Personnage/MOUTARDE.jpg");
        imagePaths.put(Personnage.OLIVE.getNom(), "/image/Personnage/OLIVE.jpg");
        imagePaths.put(Personnage.ORCHIDEE.getNom(), "/image/Personnage/ORCHIDEE.jpg");
        imagePaths.put(Personnage.PERVENCHE.getNom(), "/image/Personnage/PERVENCHE.jpg");
        imagePaths.put(Personnage.VIOLET.getNom(), "/image/Personnage/VIOLET.jpg");

    }

    public static String getImagePath(String nomCarte){
        return imagePaths.get(nomCarte);
    }

    private String url;

    ImagePath(String  url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
