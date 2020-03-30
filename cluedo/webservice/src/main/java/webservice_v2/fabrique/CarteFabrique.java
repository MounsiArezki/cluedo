package webservice_v2.fabrique;

import webservice_v2.entite.carte.Arme;
import webservice_v2.entite.carte.ICarte;
import webservice_v2.entite.carte.Lieu;
import webservice_v2.entite.carte.Personnage;

import java.util.Arrays;
import java.util.List;

public class CarteFabrique {

    private static CarteFabrique facC = new CarteFabrique();

    private CarteFabrique() { }

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

    public static CarteFabrique getFacU() { return facC; }

}
