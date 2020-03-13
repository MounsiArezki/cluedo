package webservice.webservice.modele.fabrique;

import webservice.webservice.modele.entite.carte.ICarte;
import webservice.webservice.modele.entite.carte.Personnage;

import java.util.Arrays;
import java.util.List;

public class CarteFabrique {

    public static List<ICarte> getAllCartesPersonnage(){
        List<ICarte> res= Arrays.asList(
                Personnage.MOUTARDE,
                Personnage.OLIVE,
                Personnage.ORCHIDEE,
                Personnage.PERVENCHE,
                Personnage.VIOLET,
                Personnage.ROSE
        );
        return res;
    }
}
