package webservice_v2.entite;



import webservice_v2.entite.carte.ICarte;
import webservice_v2.entite.carte.Personnage;
import webservice_v2.entite.etat_partie.EtatPartie;
import webservice_v2.fabrique.FactoryCarte;

import java.util.*;

public class Partie {

    private String id;

    private User hote;

    private HashMap<String, Joueur> joueurs;

    private Map<String, ICarte> combinaisonGagante;

    private EtatPartie etatPartie;

    private Stack<ICarte> indices;

    public Partie(String id, User hote) {
        this.id = id;
        this.hote = hote;
        this.joueurs = new HashMap<>();
        this.joueurs.put(hote.getId(), new Joueur(hote));
        combinaisonGagante = new HashMap<>();
        indices=new Stack<>();
        etatPartie=new EtatPartie();
    }

    public void init(){
        etatPartie.init();

        //répartit aléatoirement les personnages entre les joueurs
        repartirPersonnages();

        // memes listes utilisées par TIRAGECOMBINAISON et DISTRIBUTIONCARTES
        List<ICarte> persos = FactoryCarte.getAllCartesPersonnage();
        List<ICarte> armes = FactoryCarte.getAllCartesArme();
        List<ICarte> lieux = FactoryCarte.getAllCartesLieu();

        //tire aléatoirement une combinaison et SUPPRIME les cartes tirées des listes
        tirageCombinaison(persos, armes, lieux);

        //répartit aléatoirement les cartes aux joueurs
        distributionCartes(persos, armes, lieux);

        //init la pile indice
        aleatoirePileIndice();
    }

    private void repartirPersonnages(){
        List<ICarte> persos=FactoryCarte.getAllCartesPersonnage();
        Stack<ICarte> pile=new Stack<>();
        pile.addAll(persos);
        Collections.shuffle(pile);

        for(Joueur j : joueurs.values()){
            ICarte p= pile.pop();
            j.setPersonnage(p);
        }
    }

    private void tirageCombinaison(List<ICarte> persos, List<ICarte> armes, List<ICarte> lieux) {
        Random random = new Random();
        int randP = random.nextInt(persos.size());
        int randA = random.nextInt(armes.size());
        int randL = random.nextInt(lieux.size());

        ICarte perso=persos.get(randP);
        ICarte arme=armes.get(randA);
        ICarte lieu=lieux.get(randL);

        combinaisonGagante.put("Personnage", perso);
        combinaisonGagante.put("Arme", arme);
        combinaisonGagante.put("Lieu", lieu);

        persos.remove(perso);
        armes.remove(arme);
        lieux.remove(lieu);
    }

    private void distributionCartes(List<ICarte> persos, List<ICarte> armes, List<ICarte> lieux) {
        Stack<ICarte> all = new Stack<>();
        all.addAll(persos);
        all.addAll(armes);
        all.addAll(lieux);
        Collections.shuffle(all);
        int i=0;
        while(!all.empty()){
            ICarte carte=all.pop();
            joueurs.get(i).ajouterCarte(carte);
            i+=1 % joueurs.size();
        }
    }

    private void aleatoirePileIndice(){
        List<ICarte> qui = FactoryCarte.getAllCartesPersonnage();
        List<ICarte> avecQuoi = FactoryCarte.getAllCartesArme();
        List<ICarte> ou = FactoryCarte.getAllCartesLieu();
        List<ICarte> speciales = FactoryCarte.getAllCartesSpeciales();

        indices.addAll(qui);
        indices.addAll(avecQuoi);
        indices.addAll(ou);
        indices.addAll(speciales);

        Collections.shuffle(indices);
    }

    public ICarte tirerIndice(){
        ICarte indice=indices.pop();
        indices.push(indice);
        return indice;
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
