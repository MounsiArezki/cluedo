package client.client.modele.entite.carte;

import java.util.HashMap;
import java.util.Map;

public class DicoCartes {

    private static Map<String, ICarte> dico = new HashMap<>();
    static {

        dico.put(Arme.COUTEAU.getNom(), Arme.COUTEAU);
        dico.put(Arme.CHANDELIER.getNom(), Arme.CHANDELIER);
        dico.put(Arme.CLE_ANGLAISE.getNom(), Arme.CLE_ANGLAISE);
        dico.put(Arme.CORDE.getNom(), Arme.CORDE);
        dico.put(Arme.TUYAU_DE_PLOMB.getNom(), Arme.TUYAU_DE_PLOMB);
        dico.put(Arme.REVOLVER.getNom(), Arme.REVOLVER);

        dico.put(Lieu.HALL.getNom(), Lieu.HALL);
        dico.put(Lieu.CONSERVATORY.getNom(), Lieu.CONSERVATORY);
        dico.put(Lieu.KITCHEN.getNom(), Lieu.KITCHEN);
        dico.put(Lieu.DINING_ROOM.getNom(), Lieu.DINING_ROOM);
        dico.put(Lieu.BALL_ROOM.getNom(), Lieu.BALL_ROOM);
        dico.put(Lieu.BILLIARD_ROOM.getNom(), Lieu.BILLIARD_ROOM);
        dico.put(Lieu.LIBRARY.getNom(), Lieu.LIBRARY);
        dico.put(Lieu.LOUNGE.getNom(), Lieu.LOUNGE);
        dico.put(Lieu.STUDY.getNom(), Lieu.STUDY);

        dico.put(Personnage.ROSE.getNom(), Personnage.ROSE);
        dico.put(Personnage.MOUTARDE.getNom(), Personnage.MOUTARDE);
        dico.put(Personnage.OLIVE.getNom(), Personnage.OLIVE);
        dico.put(Personnage.ORCHIDEE.getNom(), Personnage.ORCHIDEE);
        dico.put(Personnage.PERVENCHE.getNom(), Personnage.PERVENCHE);
        dico.put(Personnage.VIOLET.getNom(), Personnage.VIOLET);
    }

    public static ICarte get(String nom){
        return dico.get(nom);
    }
}
