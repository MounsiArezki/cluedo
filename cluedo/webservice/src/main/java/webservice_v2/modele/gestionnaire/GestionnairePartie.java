package webservice_v2.modele.gestionnaire;

import webservice_v2.exception.partie.DeplacementNonAutoriseException;
import webservice_v2.exception.partie.PasJoueurCourantException;
import webservice_v2.exception.partie.PiocherIndiceNonAutoriseException;
import webservice_v2.modele.entite.Position;
import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;
import webservice_v2.modele.fabrique.FactoryCarte;

import java.util.*;

public class GestionnairePartie {

    //
    // Methodes
    //
    public static void ajouterJoueur(Partie partie, User user){
        Joueur joueur=new Joueur(user);
        partie.getJoueurs().put(user.getId(), joueur);
    }

    private static boolean checkJoueurCourant(User user, Partie partie){
        return partie
                .getEtatPartie()
                .getJoueurCourant()
                .getUser()
                .equals(user);
    }

    private static boolean isDeplacementOk(Position position, Joueur joueur, List<Integer> des, Partie partie){
        boolean res=true;

        //check si arrivée sur un autre joueur
        if(position.getLieu()==null){
            for(Joueur j : partie.getJoueurs().values()){
                if(j.getPosition().equals(position)){
                    res=false;
                }
            }
        }
        if(res){
            int xVoulu=position.getX();
            int yVoulu=position.getY();

            int xJoueur=joueur.getPosition().getX();
            int yJoueur=joueur.getPosition().getY();

            int distanceDisponible=0;
            des.stream().filter(i -> i!=1).reduce(distanceDisponible, Integer::sum);

            int distanceVoulue=Math.abs(xJoueur-xVoulu)+Math.abs(yJoueur-yVoulu);
            res=distanceDisponible>=distanceVoulue;
        }
        return res;
    }

    //
    // Initialisation partie
    //
    public static void init(Partie partie){

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

        //transitionne vers l'état Debut Tour du P1
        Joueur p1= partie.getJoueurs().get(1);
        partie.setEtatPartie(
                partie.getEtatPartie().debuterTour(p1)
        );
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

        Map<String, Integer> ordres=new HashMap<>();
        int ordre=1;

        for(Joueur j : aleatoire){
            ordres.put(j.getUser().getId(), ordre);
            ordre++;
        }
        partie.setOrdres(ordres);
    }

    private static void tirageCombinaison(Partie partie, List<ICarte> persos, List<ICarte> armes, List<ICarte> lieux) {
        Random random = new Random();
        int randP = random.nextInt(persos.size());
        int randA = random.nextInt(armes.size());
        int randL = random.nextInt(lieux.size());

        ICarte perso=persos.get(randP);
        ICarte arme=armes.get(randA);
        ICarte lieu=lieux.get(randL);

        Map<TypeCarte, ICarte> combinaisonGagante = new HashMap<>();
        combinaisonGagante.put(TypeCarte.PERSONNAGE, perso);
        combinaisonGagante.put(TypeCarte.ARME, arme);
        combinaisonGagante.put(TypeCarte.LIEU, lieu);

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
    public static List<Integer> lancerDes(User user, Partie partie) throws PasJoueurCourantException {
        boolean isJoueurCourant=checkJoueurCourant(user, partie);
        if(!isJoueurCourant){
            throw new PasJoueurCourantException();
        }
        List<Integer> des= new ArrayList<>();
        Random random = new Random();

        des.add(random.nextInt(6)+1);
        des.add(random.nextInt(6)+1);

        partie.setEtatPartie(
                partie.getEtatPartie().lancerDe(des)
        );
        return des;
    }

    public static void seDeplacer(User user, Position position, Partie partie) throws PasJoueurCourantException, DeplacementNonAutoriseException {
        boolean isJoueurCourant=checkJoueurCourant(user, partie);
        if(!isJoueurCourant){
            throw new PasJoueurCourantException();
        }

        Joueur joueur= partie.getJoueurs().get(user.getId());
        List<Integer> des= partie.getEtatPartie().getDes();

        boolean isDeplacementOk= isDeplacementOk(position, joueur, des, partie);
        if(!isDeplacementOk){
            throw new DeplacementNonAutoriseException();
        }

        joueur.setPosition(position);
        partie.setEtatPartie(
                partie.getEtatPartie().deplacer()
        );
    }

    public static void tirerIndice(User user, List<Integer> des, Partie partie) throws PasJoueurCourantException, PiocherIndiceNonAutoriseException {
        boolean isJoueurCourant=checkJoueurCourant(user, partie);
        if(!isJoueurCourant){
            throw new PasJoueurCourantException();
        }
        if(!des.contains(1)){
            throw new PiocherIndiceNonAutoriseException();
        }
        List<ICarte> indices=new ArrayList<>();
        for(Integer de : des){
            if(de==1){
                ICarte indice=partie.getIndices().pop();
                partie.getIndices().push(indice);
            }
        }

        partie.setEtatPartie(
                partie.getEtatPartie().piocherIndice(indices, des)
        );
    }



}
