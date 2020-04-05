package webservice_v2.modele.entite;



import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.etat_partie.EnAttenteDesJoueurs;
import webservice_v2.modele.entite.etat_partie.IEtatPartie;

import java.util.*;

public class Partie {

    private String id;

    private User hote;

    //String: id user
    private Map<String, Joueur> joueurs;

    //int : ordre du joueur (début 1)
    private Map<String, Integer> ordres;

    private Map<TypeCarte, ICarte> combinaisonGagante;

    private IEtatPartie etatPartie;

    private Stack<ICarte> indices;

    private List<String> logs;

    public Partie() {
    }

    public Partie(String id, User hote) {
        this.id = id;
        this.hote = hote;
        this.joueurs = new HashMap<>();
        this.joueurs.put(hote.getId(), new Joueur(hote));
        combinaisonGagante = new HashMap<>();
        indices=new Stack<>();
        etatPartie=new EnAttenteDesJoueurs();
        logs=new ArrayList<>();
    }



    public String getId() { return id; }

    public User getHote() { return hote; }

    public Map<String, Joueur> getJoueurs() { return joueurs; }

    public void setJoueurs(Map<String, Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Map<TypeCarte, ICarte> getCombinaisonGagante() { return combinaisonGagante; }

    public void setId(String id) {
        this.id = id;
    }

    public void setHote(User hote) {
        this.hote = hote;
    }

    public void setCombinaisonGagante(Map<TypeCarte, ICarte> combinaisonGagante) {
        this.combinaisonGagante = combinaisonGagante;
    }

    public IEtatPartie getEtatPartie() {
        return etatPartie;
    }

    public void setEtatPartie(IEtatPartie etatPartie) {
        this.etatPartie = etatPartie;
    }

    public Stack<ICarte> getIndices() {
        return indices;
    }

    public void setIndices(Stack<ICarte> indices) {
        this.indices = indices;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public Map<String, Integer> getOrdres() {
        return ordres;
    }

    public void setOrdres(Map<String, Integer> ordres) {
        this.ordres = ordres;
    }


}
