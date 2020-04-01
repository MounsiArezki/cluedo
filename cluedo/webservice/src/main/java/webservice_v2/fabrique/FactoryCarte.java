package webservice_v2.fabrique;

import webservice_v2.entite.carte.*;

import java.util.Arrays;
import java.util.List;

public class FactoryCarte {

    private static FactoryCarte facC = new FactoryCarte();

    private FactoryCarte() { }

    public static List<ICarte> getAllCartesPersonnage() {
        return Arrays.asList(
                Personnage.MOUTARDE,
                Personnage.OLIVE,
                Personnage.ORCHIDEE,
                Personnage.PERVENCHE,
                Personnage.VIOLET,
                Personnage.ROSE
        );
    }

    public static List<ICarte> getAllCartesLieu() {
        return Arrays.asList(
            Lieu.SALLE_RECEPTION,
            Lieu.SALLE_BILLARD,
            Lieu.JARDIN_HIVERS,
            Lieu.SALLE_MANGER,
            Lieu.ENTREE,
            Lieu.BIBLIOTHEQUE,
            Lieu.SALON,
            Lieu.BUREAU,
            Lieu.CUISINE
        );
    }

    public static List<ICarte> getAllCartesArme() {
        return Arrays.asList(
                Arme.CHANDELIER,
                Arme.POIGNARD,
                Arme.BARRE_DE_FER,
                Arme.REVOLVER,
                Arme.CORDE,
                Arme.CLE_ANGLAISE
        );
    }

    public static List<ICarte> getAllCartesSpeciales(){
        return Arrays.asList(
                Speciale.DEPLACER_ALL_LIEU,
                Speciale.MONTRER_CARTE_JOUEUR_GAUCHE,
                Speciale.NOMER_ARME,
                Speciale.NOMER_LIEU,
                Speciale.NOMER_PERSONNAGE,
                Speciale.PASSAGE_SECRET,
                Speciale.REVELER_CARTE_ALL,
                Speciale.REVELER_CARTE_JOUEUR_CHOISIT
        );
    }

    public static FactoryCarte getFacU() { return facC; }

}
