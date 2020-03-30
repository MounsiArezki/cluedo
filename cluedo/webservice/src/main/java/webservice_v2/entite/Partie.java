package webservice_v2.entite;

import webservice_v2.carte.ICarte;
import webservice_v2.etat_partie.EtatPartie;
import webservice_v2.fabrique.CarteFabrique;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Partie {

    private String id;

    private User hote;
    private HashMap<String, Joueur> joueurs;

    private Map<String, ICarte> combinaisonGagante;



    private EtatPartie etatPartie;

    public Partie(String id, User hote) {
        this.id = id;
        this.hote = hote;
        this.joueurs = new HashMap<>();
        this.joueurs.put(hote.getId(), new Joueur());
        combinaisonGagante = new HashMap<>();
        tirageCombinaison();
        etatPartie=new EtatPartie();
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

    public EtatPartie getEtatPartie() {
        return etatPartie;
    }

    public void setEtatPartie(EtatPartie etatPartie) {
        this.etatPartie = etatPartie;
    }
}
