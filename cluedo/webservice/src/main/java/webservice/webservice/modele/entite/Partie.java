package webservice.webservice.modele.entite;

import webservice.webservice.modele.entite.carte.ICarte;
import webservice.webservice.modele.fabrique.CarteFabrique;

import java.util.Map;
import java.util.List;
import java.util.Random;

public class Partie {

    Map<String, ICarte> combinaisonGagante;

    private void tirageCombinaison(){
        List<ICarte> persos= CarteFabrique.getAllCartesPersonnage();
        Random random=new Random();
        int rand=random.nextInt(persos.size());
        combinaisonGagante.put("Personnage",persos.get(rand));
    }
}
