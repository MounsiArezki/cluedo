package webservice.webservice.modele.entite;

import webservice.webservice.modele.entite.carte.ICarte;
import webservice.webservice.modele.entite.etat_partie.IEtatPartie;
import webservice.webservice.modele.entite.etat_partie.PasCommencee;
import webservice.webservice.modele.fabrique.CarteFabrique;

import java.util.*;

public class Partie {

    private String id;

    private User hote;
    private HashMap<String, Joueur> joueurs;

    private Map<String, ICarte> combinaisonGagante;

    private static long lastId = 0L;

    private IEtatPartie etatPartie;

    public Partie(User hote) {
        this.id = String.valueOf(++lastId);
        this.hote = hote;
        this.joueurs = new HashMap<>();
        combinaisonGagante = new HashMap<>();
        tirageCombinaison();
        etatPartie=new PasCommencee();
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

    public String getId() { return id; }

    public User getHote() { return hote; }

    public HashMap<String, Joueur> getJoueurs() { return joueurs; }

    public Map<String, ICarte> getCombinaisonGagante() { return combinaisonGagante; }

    public void setId(String id) {
        this.id = id;
    }

    public void setHote(User hote) {
        this.hote = hote;
    }

    public void setJoueurs(HashMap<String, Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public void setCombinaisonGagante(Map<String, ICarte> combinaisonGagante) {
        this.combinaisonGagante = combinaisonGagante;
    }

    public static long getLastId() {
        return lastId;
    }

    public static void setLastId(long lastId) {
        Partie.lastId = lastId;
    }

    public IEtatPartie getEtatPartie() {
        return etatPartie;
    }

    public void setEtatPartie(IEtatPartie etatPartie) {
        this.etatPartie = etatPartie;
    }
}
