package webservice_v2.modele.fabrique;

import webservice_v2.modele.entite.carte.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FactoryCarte {

    private static FactoryCarte facC = new FactoryCarte();

    private FactoryCarte() { }

    public static List<ICarte> getAllCartesPersonnage() {
        return new ArrayList<>(Arrays.asList(
                Personnage.ROSE,
                Personnage.MOUTARDE,
                Personnage.ORCHIDEE,
                Personnage.OLIVE,
                Personnage.PERVENCHE,
                Personnage.VIOLET

        ));
    }

    public static List<ICarte> getAllCartesLieu() {
        return new ArrayList<>(Arrays.asList(
            Lieu.HALL,
            Lieu.BALL_ROOM,
            Lieu.BILLIARD_ROOM,
            Lieu.CONSERVATORY,
            Lieu.DINING_ROOM,
            Lieu.LIBRARY,
            Lieu.LOUNGE,
            Lieu.STUDY,
            Lieu.KITCHEN
        ));
    }

    public static List<ICarte> getAllCartesArme() {
        return new ArrayList<>(Arrays.asList(
                Arme.CHANDELIER,
                Arme.COUTEAU,
                Arme.TUYAU_DE_PLOMB,
                Arme.REVOLVER,
                Arme.CORDE,
                Arme.CLE_ANGLAISE
        ));
    }

    public static List<ICarte> getAllCartesSpeciales(){
        return new ArrayList<>(Arrays.asList(
                Speciale.DEPLACER_ALL_LIEU,
                Speciale.MONTRER_CARTE_JOUEUR_GAUCHE,
                Speciale.NOMER_ARME,
                Speciale.NOMER_LIEU,
                Speciale.NOMER_PERSONNAGE,
                Speciale.PASSAGE_SECRET,
                Speciale.REVELER_CARTE_ALL,
                Speciale.REVELER_CARTE_JOUEUR_CHOISIT
        ));
    }

    public static FactoryCarte getFacU() { return facC; }

}
