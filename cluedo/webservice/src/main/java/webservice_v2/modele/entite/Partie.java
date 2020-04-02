package webservice_v2.modele.entite;



import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.etat_partie.EnAttenteDesJoueurs;
import webservice_v2.modele.entite.etat_partie.IEtatPartie;

import java.util.*;

public class Partie {

    private String id;

    private User hote;

    //int : ordre du joueur (début 1)
    private Map<Integer, Joueur> joueurs;

    private Map<TypeCarte, ICarte> combinaisonGagante;

    private IEtatPartie etatPartie;

    private Stack<ICarte> indices;

    public Partie(String id, User hote) {
        this.id = id;
        this.hote = hote;
        this.joueurs = new HashMap<>();
        this.joueurs.put(-1, new Joueur(hote));
        combinaisonGagante = new HashMap<>();
        indices=new Stack<>();
        etatPartie=new EnAttenteDesJoueurs();
    }



    public String getId() { return id; }

    public User getHote() { return hote; }

    public Map<Integer, Joueur> getJoueurs() { return joueurs; }

    public Map<TypeCarte, ICarte> getCombinaisonGagante() { return combinaisonGagante; }

    public void setId(String id) {
        this.id = id;
    }

    public void setHote(User hote) {
        this.hote = hote;
    }

    public void setJoueurs(Map<Integer, Joueur> joueurs) {
        this.joueurs = joueurs;
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
}
