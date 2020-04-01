package webservice_v2.modele.gestionnaire;

import webservice_v2.modele.carte.ICarte;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;
import webservice_v2.modele.fabrique.FactoryCarte;

import javax.servlet.http.Part;
import java.util.*;

public class GestionnairePartie {

    //
    // Methodes
    //
    public static void ajouterJoueur(Partie partie, User user){
        Joueur joueur=new Joueur(user);
        partie.getJoueurs().put(-1, joueur);
    }


    //
    // Initialisation partie
    //
    public static void init(Partie partie){
        partie.getEtatPartie().next();

        //répartit aléatoirement les personnages entre les joueurs
        repartirPersonnages(partie);

        //ordre jeu des joueurs
        aleatoireOdreJoueurs(partie);

        // memes listes utilisées par TIRAGECOMBINAISON et DISTRIBUTIONCARTES
        List<ICarte> persos = FactoryCarte.getAllCartesPersonnage();
        List<ICarte> armes = FactoryCarte.getAllCartesArme();
        List<ICarte> lieux = FactoryCarte.getAllCartesLieu();

        //tire aléatoirement une combinaison et SUPPRIME les cartes tirées des listes
        tirageCombinaison(partie, persos, armes, lieux);

        //répartit aléatoirement les cartes aux joueurs
        distributionCartes(partie, persos, armes, lieux);

        //init la pile indice
        aleatoirePileIndice(partie);
    }

    private static void repartirPersonnages(Partie partie){
        List<ICarte> persos=FactoryCarte.getAllCartesPersonnage();
        Stack<ICarte> pile=new Stack<>();
        pile.addAll(persos);
        Collections.shuffle(pile);

        for(Joueur j : partie.getJoueurs().values()){
            ICarte p= pile.pop();
            j.setPersonnage(p);
        }
    }

    private static void aleatoireOdreJoueurs(Partie partie){
        List<Joueur> aleatoire= List.copyOf(partie.getJoueurs().values());
        Collections.shuffle(aleatoire);

        Map<Integer, Joueur> joueurs=new HashMap<>();
        int ordre=1;

        for(Joueur j : aleatoire){
            joueurs.put(ordre, j);
            ordre++;
        }
        partie.setJoueurs(joueurs);
    }

    private static void tirageCombinaison(Partie partie, List<ICarte> persos, List<ICarte> armes, List<ICarte> lieux) {
        Random random = new Random();
        int randP = random.nextInt(persos.size());
        int randA = random.nextInt(armes.size());
        int randL = random.nextInt(lieux.size());

        ICarte perso=persos.get(randP);
        ICarte arme=armes.get(randA);
        ICarte lieu=lieux.get(randL);

        Map<String, ICarte> combinaisonGagante = new HashMap<>();
        combinaisonGagante.put("Personnage", perso);
        combinaisonGagante.put("Arme", arme);
        combinaisonGagante.put("Lieu", lieu);

        partie.setCombinaisonGagante(combinaisonGagante);

        persos.remove(perso);
        armes.remove(arme);
        lieux.remove(lieu);
    }

    private static void distributionCartes(Partie partie, List<ICarte> persos, List<ICarte> armes, List<ICarte> lieux) {
        Stack<ICarte> all = new Stack<>();
        all.addAll(persos);
        all.addAll(armes);
        all.addAll(lieux);
        Collections.shuffle(all);
        int i=0;
        while(!all.empty()){
            ICarte carte=all.pop();
            partie.getJoueurs().get(i).ajouterCarte(carte);
            i+=1 % partie.getJoueurs().size();
        }
    }

    private static void aleatoirePileIndice(Partie partie){
        List<ICarte> qui = FactoryCarte.getAllCartesPersonnage();
        List<ICarte> avecQuoi = FactoryCarte.getAllCartesArme();
        List<ICarte> ou = FactoryCarte.getAllCartesLieu();
        List<ICarte> speciales = FactoryCarte.getAllCartesSpeciales();

        Stack<ICarte> indices=new Stack<>();
        indices.addAll(qui);
        indices.addAll(avecQuoi);
        indices.addAll(ou);
        indices.addAll(speciales);

        Collections.shuffle(indices);

        partie.setIndices(indices);
    }

    //
    // Actions
    //
    public static ICarte tirerIndice(Partie partie){
        ICarte indice=partie.getIndices().pop();
        partie.getIndices().push(indice);
        return indice;
    }

}
