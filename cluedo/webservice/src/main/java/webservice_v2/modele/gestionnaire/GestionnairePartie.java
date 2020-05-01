package webservice_v2.modele.gestionnaire;

import webservice_v2.exception.PlusPersonneDansPartieException;
import webservice_v2.exception.partie.*;
import webservice_v2.modele.entite.Position;
import webservice_v2.modele.entite.carte.Arme;
import webservice_v2.modele.entite.carte.ICarte;
import webservice_v2.modele.entite.carte.Personnage;
import webservice_v2.modele.entite.carte.TypeCarte;
import webservice_v2.modele.entite.Joueur;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;
import webservice_v2.modele.entite.etat_partie.*;
import webservice_v2.modele.fabrique.FactoryCarte;

import java.util.*;
import java.util.stream.Collectors;

public class GestionnairePartie {

    static Map<Personnage, Position> positionsDepart = new HashMap<>();
    static {
        positionsDepart.put(Personnage.ROSE, new Position(16,0));
        positionsDepart.put(Personnage.MOUTARDE, new Position(23,7));
        positionsDepart.put(Personnage.OLIVE, new Position(9,24));
        positionsDepart.put(Personnage.ORCHIDEE, new Position(14,24));
        positionsDepart.put(Personnage.PERVENCHE, new Position(0,18));
        positionsDepart.put(Personnage.VIOLET, new Position(0,5));
    }

    //
    // Methodes
    //
    public static void ajouterJoueur(Partie partie, User user){
        Joueur joueur=new Joueur(user);
        partie.getJoueurs().put(user.getId(), joueur);
    }

    private static boolean isJoueurCourant(User user, Partie partie){
        try{
            return partie
                    .getEtatPartie()
                    .obtenirJoueurCourant()
                    .getUser()
                    .equals(user);
        }
        catch (UnsupportedOperationException e){
            return false;
        }

    }

    private static boolean isJoueurActif(User user, Partie partie){
        try{
            return partie
                    .getEtatPartie()
                    .obtenirJoueurAtif()
                    .getUser()
                    .equals(user);
        }
        catch (UnsupportedOperationException e){
            return false;
        }

    }

    private static Joueur getJoueurSuivant(Partie partie){
        try{
            Joueur joueurCourant=partie.getEtatPartie().obtenirJoueurCourant();
            int ordreCourant=partie.getOrdreByJoueur().get(joueurCourant.getUser().getId());
            boolean trouve=false;
            int ordreSuivant= (ordreCourant % partie.getJoueurs().size())+1;

            Joueur joueurSuivant=null;

            while(!trouve && ordreSuivant!=ordreCourant){
                String idSuivant=partie.getJoueurByOrdre().get(ordreSuivant);
                joueurSuivant=partie.getJoueurs().get(idSuivant);
                if(joueurSuivant.isElimine()){
                    ordreSuivant=((ordreSuivant) % partie.getJoueurs().size())+1;
                    joueurSuivant=null;
                }
                else{
                    trouve=true;
                }
            }

            return joueurSuivant;
        }
        catch (UnsupportedOperationException e){
            System.out.println("Erreur dans la récupération du joueur suivant");
            return null;
        }
    }

    private static Joueur getJoueurActifSuivant(Partie partie){
        Joueur joueurActif = null;
        try{
            joueurActif=partie.getEtatPartie().obtenirJoueurAtif();
        }
        catch (UnsupportedOperationException e){
            try{
                joueurActif = partie.getEtatPartie().obtenirJoueurCourant();
            } catch (UnsupportedOperationException e2){
                return null;
            }
        }
        int ordreActif=partie.getOrdreByJoueur().get(joueurActif.getUser().getId());
        int ordreActifSuivant= (ordreActif % partie.getJoueurs().size())+1;
        String idSuivant=partie.getJoueurByOrdre().get(ordreActifSuivant);

        Joueur joueurActifSuivant=null;

        //check que le joueur courant ne soit pas le prochain joueur actif
        Joueur joueurCourant=partie.getEtatPartie().obtenirJoueurCourant();
        if(!idSuivant.equals(joueurCourant.getUser().getId())){
            joueurActifSuivant=partie.getJoueurs().get(idSuivant);
        }
        return joueurActifSuivant;
    }

    // vérifier si la distance du déplacement égal le nombre des dés
    private static boolean isDeplacementOk(Position dest, Joueur joueur, List<Integer> des, Partie partie) {
        Position pInitiale = joueur.getPosition(); // position initiale du joueur

        int dRes = des.get(0) + des.get(1); // résultat des deux dés
        int diff = Math.abs(pInitiale.getX() - dest.getX()) + Math.abs(pInitiale.getY() - dest.getY());

      //  return dRes >= diff;
        return true;

    }

    //
    // Finir partie quand pas assez joueurs
    //
    public static void annulerPartie(Partie partie){
        partie.setEtatPartie(
                partie.getEtatPartie().finirPartie(null, null)
        );
    }

    //
    // Initialisation partie
    //
    public static void init(Partie partie){

        //répartit aléatoirement les personnages entre les joueurs
        repartirPersonnages(partie);

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

        //remplit les fiches indices
        remplirFichesIndices(partie);

        //transitionne vers l'état Debut Tour du P1
        String id = partie.getJoueurByOrdre().get(1);
        Joueur p1= partie.getJoueurs().get(id);
        partie.setEtatPartie(
                partie.getEtatPartie().debuterTour(p1)
        );
        System.out.println("combinaison gagnante: "+partie.getCombinaisonGagante());
    }

    private static void repartirPersonnages(Partie partie){

        List<Joueur> listeJoueurs = new ArrayList<>(partie.getJoueurs().values());
        List<ICarte> persos=FactoryCarte.getAllCartesPersonnage();
        Stack<Joueur> pile=new Stack<>();
        pile.addAll(listeJoueurs);
        Collections.shuffle(pile);

        int i = 0;
        Map<String, Integer> ordres=new HashMap<>();
        Map<Integer, String> joueursByOrdre = new HashMap<>();

        while(!pile.empty()){
            Joueur j = pile.pop();
            ICarte perso = persos.get(i);
            j.setPersonnage(perso);
            j.setPosition(positionsDepart.get(perso));
            ordres.put(j.getUser().getId(),i+1);
            joueursByOrdre.put(i+1, j.getUser().getId());
            i++;
        }

        partie.setJoueurByOrdre(joueursByOrdre);
        partie.setOrdreByJoueur(ordres);
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
        List<Joueur> joueurs = new ArrayList<>(partie.getJoueurs().values());
        while(!all.empty()){
            ICarte carte=all.pop();
            joueurs.get(i).ajouterCarte(carte);
            i+=1;
            i%=joueurs.size();
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

    private static void remplirFichesIndices(Partie partie){
        for(Joueur joueur: partie.getJoueurs().values()){
            for(ICarte carte : joueur.getListeCartes()){
                joueur.getFicheEnquete().put(carte, joueur);
            }
        }
    }

    //
    // Actions
    //

    //LANCER_DES
    public static List<Integer> lancerDes(User user, Partie partie) throws PasJoueurCourantException, ActionNonAutoriseeException {
        boolean isJoueurCourant=isJoueurCourant(user, partie);
        if(!isJoueurCourant){
            throw new PasJoueurCourantException();
        }

        //check si etat = debut tour
        IEtatPartie etatPartie=partie.getEtatPartie();
        if(!(etatPartie instanceof DebutTour)){
            throw new ActionNonAutoriseeException();
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

    //DEPLACER
    public static void seDeplacer(User user, Position position, Partie partie) throws PasJoueurCourantException, DeplacementNonAutoriseException, ActionNonAutoriseeException {
        boolean isJoueurCourant = isJoueurCourant(user, partie);
        if (!isJoueurCourant) throw new PasJoueurCourantException();


        //check si etat = resolution des OU resolution indices
        IEtatPartie etatPartie=partie.getEtatPartie();
        if (!(etatPartie instanceof ResolutionDes || etatPartie instanceof ResolutionIndice)) {
            throw new ActionNonAutoriseeException();
        }

        Joueur joueur = partie.getJoueurs().get(user.getId());
        List<Integer> des = partie.getEtatPartie().obtenirDes();

        boolean isDeplacementOk = isDeplacementOk(position, joueur, des, partie);
        if (!isDeplacementOk) throw new DeplacementNonAutoriseException();

        joueur.setPosition(position);
        if (!des.contains(1)) partie.setEtatPartie(partie.getEtatPartie().deplacer());
        else partie.setEtatPartie(partie.getEtatPartie().deplacer());
    }

    //PIOCHER_INDICE
    public static List<ICarte> tirerIndice(User user, List<Integer> des, Partie partie) throws PasJoueurCourantException, PiocherIndiceNonAutoriseException, ActionNonAutoriseeException {
        boolean isJoueurCourant=isJoueurCourant(user, partie);
        if(!isJoueurCourant){
            throw new PasJoueurCourantException();
        }

        //check si etat = attente pioche indice
        IEtatPartie etatPartie=partie.getEtatPartie();
        if(!(etatPartie instanceof ResolutionDes)){
            throw new ActionNonAutoriseeException();
        }

        //check resultats des
        if(!des.contains(1)){
            throw new PiocherIndiceNonAutoriseException();
        }

        List<ICarte> indices=new ArrayList<>();
        Stack<ICarte> pileCartes=partie.getIndices();
        for(Integer de : des){
            if(de==1){
                ICarte indice=pileCartes.pop();
                pileCartes.add(pileCartes.size()-1,indice);
            }
        }

        partie.setEtatPartie(
                partie.getEtatPartie().piocherIndice(indices, des)
        );
        return indices;
    }

    //QUITTER
    public static void quitterPartie(User user, Partie partie) throws PlusPersonneDansPartieException {
        if (partie.getJoueurs().size() == 2) {
            partie.getJoueurs().remove(user.getId());
            Joueur gagnant = (Joueur) partie.getJoueurs().values().toArray()[0];
            partie.setEtatPartie(
                    new PartieFinie(gagnant, partie.getCombinaisonGagante())
            );
        }
        else if(partie.getJoueurs().size() <2){
            throw new PlusPersonneDansPartieException();
        }
        else {

            Joueur joueur = partie.getJoueurs().get(user.getId());

            //Check si le joueur est courant ou actif et modifie etat partie
            boolean isJoueurCourant = isJoueurCourant(user, partie);
            boolean isJoueurActif = isJoueurActif(user, partie);

            if (isJoueurCourant) {
                Joueur suivant = getJoueurSuivant(partie);
                partie.setEtatPartie(
                        new DebutTour(suivant)
                );
            } else if (isJoueurActif) {
                Joueur actifSuivant = getJoueurActifSuivant(partie);
                partie.getEtatPartie().changerJoueurActif(actifSuivant);
            }

            //Distribution des cartes joueur aux autres
            List<ICarte> yaBesoinQueDeCa =joueur.getListeCartes();
            List<ICarte> armes = new ArrayList<>();
            List<ICarte> lieux = new ArrayList<>();

            //Suppression du joueur AVANT distribution de ses cartes
            partie.getJoueurs().remove(user.getId());

            distributionCartes(partie, yaBesoinQueDeCa, armes, lieux);

            for(Joueur j : partie.getJoueurs().values()){
                System.out.println(j.getListeCartes());
            }
        }
    }

    //ACCUSER
    public static void accuser(User user, Map<TypeCarte, ICarte> accusation, Partie partie) throws PasJoueurCourantException, ActionNonAutoriseeException{
        boolean isJoueurCourant=isJoueurCourant(user, partie);
        if(!isJoueurCourant){
            throw new PasJoueurCourantException();
        }

        //check si etat = debut tour ou supputation ou fin tour
        IEtatPartie etatPartie=partie.getEtatPartie();
        if(!(etatPartie instanceof DebutTour || etatPartie instanceof Supputation || etatPartie instanceof FinTour)){
            throw new ActionNonAutoriseeException();
        }

        Map<TypeCarte, ICarte> combinaisonGagnante=partie.getCombinaisonGagante();
        if(combinaisonGagnante.get(TypeCarte.PERSONNAGE).equals(accusation.get(TypeCarte.PERSONNAGE))
            && combinaisonGagnante.get(TypeCarte.ARME).equals(accusation.get(TypeCarte.ARME))
            && combinaisonGagnante.get(TypeCarte.LIEU).equals(accusation.get(TypeCarte.LIEU))
        ){
            Joueur gagnant=partie.getJoueurs().get(user.getId());
            partie.setEtatPartie(
                    partie.getEtatPartie().finirPartie(gagnant, partie.getCombinaisonGagante())
            );
        }
        else{
            etatPartie.obtenirJoueurCourant().setElimine(true);
            if (partie.getJoueurs().size() < 3) {
                Joueur gagnant = (Joueur) partie.getJoueurs().values().stream().filter(j -> !j.getUser().getId().equals(user.getId())).collect(Collectors.toList()).get(0);
                partie.setEtatPartie(
                        new PartieFinie(gagnant, partie.getCombinaisonGagante())
                );
            }
            else{
                Joueur suivant = getJoueurSuivant(partie);
                partie.setEtatPartie(
                        new DebutTour(suivant)
                );
            }
        }
    }

    //PASSER
    public static void passer(User user, Partie partie) throws PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        IEtatPartie etatPartie=partie.getEtatPartie();

        if(etatPartie instanceof Hypothese){
            boolean isJoueurActif=isJoueurActif(user, partie);
            if(!isJoueurActif){
                throw new PasJoueurActifException();
            }

            //check si le joueur actif a une carte de l'hypothese
            List<ICarte> cartesHypotheseDuJoueurActif = partie.getJoueurs().get(user.getId()).getListeCartes()
                    .stream()
                    .filter( c ->  etatPartie.obtenirHypothese().containsValue(c))
                    .collect(Collectors.toList());
            if(cartesHypotheseDuJoueurActif.size()>0){
                throw new ActionNonAutoriseeException();
            }

            Joueur joueurActifSuivant=getJoueurActifSuivant(partie);
            if(joueurActifSuivant==null){
                partie.setEtatPartie(
                        partie.getEtatPartie().resoudreHypothese()
                );
            }
            else{
                partie.setEtatPartie(
                        partie.getEtatPartie().revelerCarte(joueurActifSuivant)
                );
            }
        }
        else if(etatPartie instanceof Supputation || etatPartie instanceof FinTour){
            boolean isJoueurCourant=isJoueurCourant(user, partie);
            if(!isJoueurCourant){
                throw new PasJoueurCourantException();
            }
            Joueur suivant=getJoueurSuivant(partie);
            partie.setEtatPartie(
                    partie.getEtatPartie().debuterTour(suivant)
            );
        }
        else{
            throw new ActionNonAutoriseeException();
        }
    }

    //EMETTRE_HYPOTHESE
    public static void emettreHypothese(User user, Map<TypeCarte, ICarte> hypothese, Partie partie) throws PasJoueurCourantException, ActionNonAutoriseeException{
        boolean isJoueurCourant=isJoueurCourant(user, partie);
        if(!isJoueurCourant){
            throw new PasJoueurCourantException();
        }

        //check si etat = supputation
        IEtatPartie etatPartie=partie.getEtatPartie();
        if(!(etatPartie instanceof Supputation)){
            throw new ActionNonAutoriseeException();
        }

        partie.setEtatPartie(
                partie.getEtatPartie().faireHypothese(getJoueurActifSuivant(partie), hypothese)
        );
    }

    //REVELER_CARTE
    public static void revelerCarte(User user, ICarte carte, Partie partie) throws PasJoueurActifException, ActionNonAutoriseeException{
        boolean isJoueurActif=isJoueurActif(user, partie);
        if(!isJoueurActif){
            throw new PasJoueurActifException();
        }

        //check si etat = hypothese
        IEtatPartie etatPartie=partie.getEtatPartie();
        if(!(etatPartie instanceof Hypothese)){
            throw new ActionNonAutoriseeException();
        }

        Joueur actifSuivant=getJoueurActifSuivant(partie);

        // on vérifie si le joueur dispose bien de la carte en question
        if (!partie.getJoueurs().get(user.getId()).getListeCartes().contains(carte)) throw new ActionNonAutoriseeException();

        if(carte==null && actifSuivant!=null){
            partie.setEtatPartie(
                    partie.getEtatPartie().revelerCarte(actifSuivant)
            );
        }
        else {
            Joueur joueurCourant=partie.getEtatPartie().obtenirJoueurCourant();
            Joueur joueurActif=partie.getEtatPartie().obtenirJoueurAtif();
            joueurCourant.getFicheEnquete().put(carte, joueurActif);
            partie.setEtatPartie(
                    partie.getEtatPartie().resoudreHypothese()
            );
        }
    }

    //JOUER_INDICE_STANDARD (une carte qui demande au joueur de révéler une carte précise)
    public static void jouerIndiceStandard(User user, ICarte carte, Partie partie) throws PasJoueurCourantException, ActionNonAutoriseeException {
        Joueur jc = partie.getJoueurs().get(user.getId()); // récupération du joueur courant
        boolean carteFound = false; // est-ce que la carte a été trouvée ?
        if (isJoueurCourant(user, partie)) throw new PasJoueurCourantException();

        //check si etat = indice
        IEtatPartie etatPartie=partie.getEtatPartie();
        if (!(etatPartie instanceof ResolutionIndice)) throw new ActionNonAutoriseeException();

        for (Joueur ja : partie.getJoueurs().values()) {
            if (ja.getListeCartes().contains(carte)) {
                carteFound = true;
                partie.setEtatPartie(partie.getEtatPartie().revelationIndice(jc, ja, carte));
            }
        }
        if (!carteFound) partie.setEtatPartie(partie.getEtatPartie().deplacer());
    }

}
