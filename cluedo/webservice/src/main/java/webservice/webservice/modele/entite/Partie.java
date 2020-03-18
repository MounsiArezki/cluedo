package webservice.webservice.modele.entite;

import webservice.webservice.modele.entite.carte.ICarte;
import webservice.webservice.modele.fabrique.CarteFabrique;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Random;

public class Partie {

    private long id;

    private long idHote;
    private List<Long> idJoueurs;

    private Map<String, ICarte> combinaisonGagante;

    private static long lastId = 0L;

    public Partie(long idHote) {
        this.id = ++lastId;
        this.idHote = idHote;
        this.idJoueurs = new ArrayList<>();
        tirageCombinaison();
    }

    private void tirageCombinaison() {
        List<ICarte> persos = CarteFabrique.getAllCartesPersonnage();
        List<ICarte> armes = CarteFabrique.getAllCartesArme();
        List<ICarte> lieux = CarteFabrique.getAllCartesLieu();
        Random random = new Random();
        int randP = random.nextInt(persos.size());
        int randA = random.nextInt(armes.size());
        int randL = random.nextInt(lieux.size());
        combinaisonGagante.put("Personnage", persos.get(randP));
        combinaisonGagante.put("Arme", armes.get(randA));
        combinaisonGagante.put("Lieu", lieux.get(randL));
    }

    public long getId() { return id; }

    public long getIdHote() { return idHote; }

    public List<Long> getIdJoueurs() { return idJoueurs; }

    public Map<String, ICarte> getCombinaisonGagante() { return combinaisonGagante; }
}
