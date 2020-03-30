package webservice_v2.etat_partie;


import webservice_v2.entite.Joueur;

public class EtatPartie {

    protected Joueur joueurCourant;
    protected String texte;

    public EtatPartie(){
        texte = "En attente des autres joueurs";
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public void setJoueurCourant(Joueur joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void finir(){
        texte = "La partie est terminée";
    }

    public void init(){
        texte = "La partie commence";
    }
}
