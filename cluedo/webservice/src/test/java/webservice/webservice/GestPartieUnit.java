package webservice.webservice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import webservice_v2.exception.*;
import webservice_v2.exception.partie.ActionNonAutoriseeException;
import webservice_v2.exception.partie.DeplacementNonAutoriseException;
import webservice_v2.exception.partie.PasJoueurActifException;
import webservice_v2.exception.partie.PasJoueurCourantException;
import webservice_v2.facade.Facade;
import webservice_v2.modele.entite.Invitation;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.Position;
import webservice_v2.modele.entite.User;
import webservice_v2.modele.entite.carte.*;
import webservice_v2.modele.entite.etat_partie.*;

import java.util.*;

public class GestPartieUnit {

    private Facade fac = Facade.getFac();

    private Map<TypeCarte, ICarte> acc = new HashMap<>();

    private User hote; // hôte de la partie
    private User invite1; // invité 1
    private User invite2; // invité 2
    private User intrus; // intrus (n'est pas dans la partie)
    private Partie partie; // partie sur laquelle on teste
    private Invitation i1; // invitation (uniquement dans le before)

    private Map<Integer, String> ordre; // ordre de jeu [attention : pas d'indice 0]

    @Before // mise en place du contexte
    public void init() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PasConnecteException, InvitationInvalideException, PartieInexistanteException {
        hote = fac.addUser("hote", "pwd"); // création de l'utilisateur hôte
        fac.connexion(hote.getPseudo(), hote.getPwd()); // connexion hôte

        invite1 = fac.addUser("invite1", "pwd"); // création de l'utilisateur invité 1
        fac.connexion(invite1.getPseudo(), invite1.getPwd()); // connexion i1

        invite2 = fac.addUser("invite2", "pwd"); // création de l'utilisateur invité 2
        fac.connexion(invite2.getPseudo(), invite2.getPwd()); // connexion i2

        intrus = fac.addUser("intrus", "pwd"); // création de l'utilisateur invité 2
        fac.connexion(intrus.getPseudo(), intrus.getPwd()); // connexion de l'intrus

        partie = fac.addPartie(hote.getId()); // création de la partie

        i1 = fac.addInvitation(partie.getId(), hote.getId(), new ArrayList<>(Arrays.asList(invite1, invite2))); // création de l'invitation (avec liste mutable)
        fac.accepterInvitation(i1.getId(), invite1.getId()); // le premier invité accepte l'invitation
        fac.accepterInvitation(i1.getId(), invite2.getId()); // le second invité accepte l'invitation

        ordre = partie.getJoueurByOrdre(); // récupération de l'ordre de jeu

        // création d'une accusation "valide"
        acc.put(TypeCarte.PERSONNAGE, Personnage.MOUTARDE);
        acc.put(TypeCarte.LIEU, Lieu.BALL_ROOM);
        acc.put(TypeCarte.ARME, Arme.CHANDELIER);
    }

    // ------------------------------------------------------------------------
    // récupération des cartes d'un joueur
    // ------------------------------------------------------------------------

    @Test // Récupération "normale" des cartes d'un joueur
    public void getCartesOK() throws JoueurPasDansLaPartieException, PartieInexistanteException {
        Assert.assertEquals(fac.getJoueurCartes(partie.getId(), hote.getId()), partie.getJoueurs().get(hote.getId()).getListeCartes());
    }

    // Récupération des cartes d'un joueur qui n'est pas la partie
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void getCartesJPDPE() throws JoueurPasDansLaPartieException, PartieInexistanteException {
        fac.getJoueurCartes(partie.getId(), intrus.getId());
    }

    // Récupération des cartes d'un joueur d'une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void getCartesPIE() throws JoueurPasDansLaPartieException, PartieInexistanteException {
        fac.getJoueurCartes(null, hote.getId());
    }

    // Récupération des cartes d'un joueur inexistante d'une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void getCartesKO() throws JoueurPasDansLaPartieException, PartieInexistanteException {
        fac.getJoueurCartes(null, null);
    }

    // ------------------------------------------------------------------------
    // récupération de la fiche d'enquête d'un joueur
    // ------------------------------------------------------------------------

    @Test // Récupération "normale" de la fiche d'un joueur
    public void getFicheOK() throws JoueurPasDansLaPartieException, PartieInexistanteException {
        Assert.assertEquals(fac.getJoueurFiche(partie.getId(), hote.getId()), partie.getJoueurs().get(hote.getId()).getFicheEnquete());
    }

    // Récupération de la fiche d'un joueur qui n'est pas la partie
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void getFicheJPDPE() throws JoueurPasDansLaPartieException, PartieInexistanteException {
        fac.getJoueurFiche(partie.getId(), intrus.getId());
    }

    // Récupération de la fiche d'un joueur d'une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void getFichePIE() throws JoueurPasDansLaPartieException, PartieInexistanteException {
        fac.getJoueurFiche(null, hote.getId());
    }

    // Récupération de la fiche d'un joueur inexistante d'une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void getFicheKO() throws JoueurPasDansLaPartieException, PartieInexistanteException {
        fac.getJoueurFiche(null, null);
    }

    // ------------------------------------------------------------------------
    // tests sur le lancer de dés
    // ------------------------------------------------------------------------

    @Test // Le joueur lance "normalement" les dés
    public void lancerDesOK() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.lancerDes(partie.getId(), ordre.get(1));
        Assert.assertEquals(partie.getEtatPartie().getClass(), ResolutionDes.class);
    }

    // Lancement des dés d'un joueur qui n'est pas dans la partie
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void lancerDesJPDPE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.lancerDes(partie.getId(), intrus.getId());
    }

    // Lancement des dés d'un joueur dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void lancerDesPIE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.lancerDes(null, intrus.getId());
    }

    // Lancement des dés d'un joueur à qui ce n'est pas le tour
    @Test (expected = PasJoueurCourantException.class)
    public void lancerDesPJCE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.lancerDes(partie.getId(), ordre.get(2));
    }

    // Lancement des dés d'un joueur alors que ce n'est pas le moment
    @Test (expected = ActionNonAutoriseeException.class)
    public void lancerDesANAE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on passe à l'état suivant de la partie ("se déplacer")
        fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
    }

    // Lancement des dés d'un joueur inexistante dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void lancerDesKO() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.lancerDes(null, null);
    }

    // ------------------------------------------------------------------------
    // tests sur le déplacement
    // ------------------------------------------------------------------------

    @Test // Le joueur effectue un déplacement "normal"
    public void deplacementOK() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, DeplacementNonAutoriseException {
        List<Integer> des = fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
        Position pi = partie.getJoueurs().get(ordre.get(1)).getPosition(); // on récupère la position initiale du joueur
        Position pf = new Position(pi.getX() + des.get(0), pi.getY() + des.get(1)); // on "calcule" une position finale
        fac.deplacer(partie.getId(), ordre.get(1), pf); // on se déplace
        Assert.assertEquals(partie.getEtatPartie().getClass(), Supputation.class); // ce check fail si on a un 1 au dé (à corriger)
    }

    // Déplacement d'un joueur qui n'est pas dans la partie
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void deplacementJPDPE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, DeplacementNonAutoriseException {
        List<Integer> des = fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
        Position pi = partie.getJoueurs().get(ordre.get(1)).getPosition(); // on récupère la position initiale du joueur
        Position pf = new Position(pi.getX() + des.get(0), pi.getY() + des.get(1)); // on "calcule" une position finale
        fac.deplacer(partie.getId(), intrus.getId(), pf); // on se déplace
    }

    // Déplacement d'un joueur dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void deplacementPIE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, DeplacementNonAutoriseException {
        List<Integer> des = fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
        Position pi = partie.getJoueurs().get(ordre.get(1)).getPosition(); // on récupère la position initiale du joueur
        Position pf = new Position(pi.getX() + des.get(0), pi.getY() + des.get(1)); // on "calcule" une position finale
        fac.deplacer(null, ordre.get(1), pf); // on se déplace
    }

    // Déplacement d'un joueur à qui ce n'est pas le tour
    @Test (expected = PasJoueurCourantException.class)
    public void deplacementPJCE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, DeplacementNonAutoriseException {
        List<Integer> des = fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
        Position pi = partie.getJoueurs().get(ordre.get(1)).getPosition(); // on récupère la position initiale du joueur
        Position pf = new Position(pi.getX() + des.get(0), pi.getY() + des.get(1)); // on "calcule" une position finale
        fac.deplacer(partie.getId(), ordre.get(2), pf); // on se déplace
    }

    // Déplacement d'un joueur alors que ce n'est pas le moment
    @Test (expected = ActionNonAutoriseeException.class)
    public void deplacementANAE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, DeplacementNonAutoriseException {
        List<Integer> des = fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
        Position pi = partie.getJoueurs().get(ordre.get(1)).getPosition(); // on récupère la position initiale du joueur
        Position pf = new Position(pi.getX() + des.get(0), pi.getY() + des.get(1)); // on "calcule" une position finale
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on passe à l'état suivant de la partie ("se déplacer")
        fac.deplacer(partie.getId(), ordre.get(1), pf); // on se déplace
    }

    // Déplacement non-conforme (trop long) d'un joueur
    // [!] la méthode de validation renvoie toujours true (actuellement)
    @Test (expected = DeplacementNonAutoriseException.class)
    public void deplacementDNAE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, DeplacementNonAutoriseException {
        List<Integer> des = fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
        Position pi = partie.getJoueurs().get(ordre.get(1)).getPosition(); // on récupère la position initiale du joueur
        Position pf = new Position(pi.getX() + des.get(0) + 1, pi.getY() + des.get(1) + 1); // on "calcule" une position finale non rejoignable
        fac.deplacer(partie.getId(), ordre.get(1), pf); // on se déplace
    }

    // Déplacement d'un joueur inexistant dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void deplacementKO1() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, DeplacementNonAutoriseException {
        List<Integer> des = fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
        Position pi = partie.getJoueurs().get(ordre.get(1)).getPosition(); // on récupère la position initiale du joueur
        Position pf = new Position(pi.getX() + des.get(0), pi.getY() + des.get(1)); // on "calcule" une position finale
        fac.deplacer(null, null, pf); // on se déplace
    }

    // Déplacement d'un joueur inexistant dans une partie
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void deplacementKO2() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, DeplacementNonAutoriseException {
        List<Integer> des = fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
        Position pi = partie.getJoueurs().get(ordre.get(1)).getPosition(); // on récupère la position initiale du joueur
        Position pf = new Position(pi.getX() + des.get(0), pi.getY() + des.get(1)); // on "calcule" une position finale
        fac.deplacer(partie.getId(), null, pf); // on se déplace
    }

    // Déplacement vers une position inexistante d'un joueur dans une partie
    @Test (expected = DeplacementNonAutoriseException.class)
    public void deplacementKO3() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, DeplacementNonAutoriseException {
        fac.lancerDes(partie.getId(), ordre.get(1)); // on effectue un lancer de dés
        fac.deplacer(partie.getId(), ordre.get(1), null); // on se déplace
    }

    // ------------------------------------------------------------------------
    // tests sur l'accusation
    // ------------------------------------------------------------------------

    @Test // Le joueur effectue une accusation "normale" (mauvaise accusation)
    public void accusationOK() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.accuser(partie.getId(), ordre.get(1), acc); // on lance une accusation
        Assert.assertEquals(partie.getEtatPartie().getClass(), DebutTour.class); // mauvaise accusation = tour du joueur suivant
    }

    @Test // Le joueur effectue une accusation "normale" (accusation gagnante)
    public void accusationOK2() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setCombinaisonGagante(acc); // on modifie la combinaison gagnante
        fac.accuser(partie.getId(), ordre.get(1), acc); // on lance une accusation
        Assert.assertEquals(partie.getEtatPartie().getClass(), PartieFinie.class); // bonne accusation = partie finie
    }

    // Accusation lancée par un joueur qui n'est pas dans la partie
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void accusationJPDPE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.accuser(partie.getId(), intrus.getId(), acc); // on lance une accusation
    }

    // Accusation lancée par un joueur dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void accusationPIE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.accuser(null, ordre.get(1), acc); // on lance une accusation
    }

    // Accusation lancée par un joueur alors que ce n'est pas à lui
    @Test (expected = PasJoueurCourantException.class)
    public void accusationPJCE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.accuser(partie.getId(), ordre.get(2), acc); // on lance une accusation
    }

    // Accusation lancée par un joueur alors que ce n'est pas le moment
    @Test (expected = ActionNonAutoriseeException.class)
    public void accusationANAE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        fac.accuser(partie.getId(), ordre.get(1), acc); // on lance une accusation
    }

    // Accusation lancée par un joueur inexistant dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void accusationKO1() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.accuser(null, null, acc); // on lance une accusation
    }

    // Accusation lancée par un joueur inexistant
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void accusationKO2() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.accuser(partie.getId(), null, acc); // on lance une accusation
    }

    // Accusation inexistant lancée par un joueur
    @Test (expected = ActionNonAutoriseeException.class)
    public void accusationKO3() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.accuser(partie.getId(), ordre.get(1), null); // on lance une accusation
    }

    // ------------------------------------------------------------------------
    // tests sur l'hypothèse
    // ------------------------------------------------------------------------

    @Test // Le joueur effectue une hypothèse "normale"
    public void hypotheseOK() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        Assert.assertEquals(partie.getEtatPartie().getClass(), Hypothese.class);
    }

    // Hypothèse lancée par un joueur qui n'est pas dans la partie
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void hypotheseJPDPE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), intrus.getId(), acc); // on lance une hypothèse
    }

    // Hypothèse lancée par un joueur qui est dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void hypothesePIE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(null, ordre.get(1), acc); // on lance une hypothèse
    }

    // Hypothèse lancée par un joueur à qui ce n'est pas le tour
    @Test (expected = PasJoueurCourantException.class)
    public void hypothesePJCE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(2), acc); // on lance une hypothèse
    }

    // Hypothèse lancée alors que ce n'est pas le moment
    @Test (expected = ActionNonAutoriseeException.class)
    public void hypotheseANAE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
    }

    // Hypothèse lancée par un joueur inexistant qui est dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void hypotheseKO1() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(null, null, acc); // on lance une hypothèse
    }

    // Hypothèse lancée par un joueur inexistant
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void hypotheseKO2() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(),null, acc); // on lance une hypothèse
    }

    // Hypothèse inexistant lancée par un joueur
    @Test (expected = ActionNonAutoriseeException.class)
    public void hypotheseKO3() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(),ordre.get(1), null); // on lance une hypothèse
    }

    // ------------------------------------------------------------------------
    // tests sur l'action 'révéler carte' (ainsi que passer dans le cas de l'hypothèse)
    // ------------------------------------------------------------------------

    @Test // Le joueur effectue l'action de façon "normale" (1 essai)
    public void revCarteOK1() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        partie.getJoueurs().get(ordre.get(2)).setListeCartes(Collections.singletonList(Arme.CHANDELIER)); // on "donne" cette carte au joueur d'après
        fac.revelerCarte(partie.getId(), ordre.get(2), Arme.CHANDELIER); // le joueur d'après relève une carte
        Assert.assertEquals(partie.getEtatPartie().getClass(), FinTour.class);
    }

    @Test // Le joueur effectue l'action de façon "normale" (2 essais)
    public void revCarteOK2() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        partie.getJoueurs().get(ordre.get(2)).setListeCartes(Collections.emptyList()); // on "vide" la main du joueur d'après
        fac.passer(partie.getId(), ordre.get(2)); // le joueur suivant n'a pas une des cartes demandées (il passe)
        partie.getJoueurs().get(ordre.get(3)).setListeCartes(Collections.singletonList(Arme.CHANDELIER)); // on "donne" cette carte au joueur d'encore après
        fac.revelerCarte(partie.getId(), ordre.get(3), Arme.CHANDELIER); // le joueur d'encore après relève une carte
        Assert.assertEquals(partie.getEtatPartie().getClass(), FinTour.class);
    }

    @Test // Le joueur effectue l'action de façon "normale" (personne n'a les cartes de l'hypothèse)
    public void revCarteOK3() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        partie.getJoueurs().get(ordre.get(2)).setListeCartes(Collections.emptyList()); // on "vide" la main du joueur d'après
        fac.passer(partie.getId(), ordre.get(2)); // le joueur suivant n'a pas une des cartes demandées (il passe)
        partie.getJoueurs().get(ordre.get(3)).setListeCartes(Collections.emptyList()); // on "vide" la main du joueur d'encore après
        fac.passer(partie.getId(), ordre.get(3)); // le joueur d'encore après n'a pas une des cartes demandées (il passe)
        Assert.assertEquals(partie.getEtatPartie().getClass(), FinTour.class);
    }

    // Action demandée sur un joueur qui n'est pas dans la partie
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void revCarteJPDPE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        fac.revelerCarte(partie.getId(), intrus.getId(), Arme.CHANDELIER); // le joueur suivant relève une carte
    }

    // Action demandée sur un joueur qui est dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void revCartePIE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        fac.revelerCarte(null, intrus.getId(), Arme.CHANDELIER); // le joueur suivant relève une carte
    }

    // Action demandée sur un joueur que ce n'est pas le moment
    @Test (expected = ActionNonAutoriseeException.class)
    public void revCarteANAE1() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        partie.getJoueurs().get(ordre.get(2)).setListeCartes(Collections.singletonList(Arme.CHANDELIER)); // on "donne" cette carte au joueur d'après
        fac.passer(partie.getId(), ordre.get(2)); // le joueur suivant tente de passer alors qu'il a la bonne carte
    }

    // Action demandée sur un joueur alors qu'il n'a pas la carte qu'il veut relever
    @Test (expected = ActionNonAutoriseeException.class)
    public void revCarteANAE2() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        partie.getJoueurs().get(ordre.get(2)).setListeCartes(Collections.singletonList(Arme.CORDE)); // on "donne" cette carte au joueur d'après
        fac.revelerCarte(partie.getId(), ordre.get(2), Arme.CHANDELIER); // le joueur suivant relève une carte
    }

    // Action demandée sur un joueur alors que ce n'est pas à lui de relever une carte
    @Test (expected = PasJoueurActifException.class)
    public void revCartePJAE1() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        partie.getJoueurs().get(ordre.get(2)).setListeCartes(Collections.singletonList(Arme.CHANDELIER)); // on "donne" cette carte au joueur d'après
        fac.revelerCarte(partie.getId(), ordre.get(2), Arme.CHANDELIER); // le joueur d'après relève une carte
        partie.getJoueurs().get(ordre.get(3)).setListeCartes(Collections.singletonList(Lieu.BALL_ROOM)); // on "donne" cette carte au joueur d'encore après
        fac.revelerCarte(partie.getId(), ordre.get(3), Lieu.BALL_ROOM); // le joueur d'encore après relève une carte
    }

    // Action demandée sur un joueur alors que ce n'est pas à lui de passer
    @Test (expected = PasJoueurActifException.class)
    public void revCartePJAE2() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        partie.getJoueurs().get(ordre.get(3)).setListeCartes(Collections.emptyList()); // on vide la main du dernier joueur
        fac.passer(partie.getId(), ordre.get(3)); // le joueur suivant n'a pas une des cartes demandées
    }

    // Action demandée sur un joueur inexistant qui est dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void revCarteKO1() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        fac.revelerCarte(null, null, Arme.CHANDELIER); // le joueur suivant relève une carte
    }

    // Action demandée sur un joueur inexistant
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void revCarteKO2() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        fac.revelerCarte(partie.getId(), null, Arme.CHANDELIER); // le joueur suivant relève une carte
    }

    // Action demandée sur un joueur qui donne une carte inexistante
    @Test (expected = ActionNonAutoriseeException.class)
    public void revCarteKO3() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.hypothese(partie.getId(), ordre.get(1), acc); // on lance une hypothèse
        fac.revelerCarte(partie.getId(), ordre.get(1), null); // le joueur suivant relève une carte
    }

    // ------------------------------------------------------------------------
    // tests sur le passage du tour (dans les autres cas que l'hypothèse)
    // ------------------------------------------------------------------------

    @Test // Le joueur finit son tour (il "passe" son tour à un autre jour)
    public void passerOK() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.passer(partie.getId(), ordre.get(1)); // on passe simplement
        Assert.assertEquals(partie.getEtatPartie().getClass(), DebutTour.class);
    }

    // Le joueur finit son tour (il "passe" son tour à un autre jour) dans une partie inexistante
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void passerJPDPE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.passer(partie.getId(), intrus.getId()); // on passe simplement
    }

    // Le joueur finit son tour (il "passe" son tour à un autre jour) dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void passerPIE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.passer(null, ordre.get(1)); // on passe simplement
    }

    // Le joueur finit son tour (il "passe" son tour à un autre jour) alors que ce n'est pas à lui
    @Test (expected = PasJoueurCourantException.class)
    public void passerPJCE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.passer(partie.getId(), ordre.get(2)); // on passe simplement
    }

    // Le joueur finit son tour (il "passe" son tour à un autre jour) alors que ce n'est pas le moment
    @Test (expected = ActionNonAutoriseeException.class)
    public void passerANAE() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        fac.passer(partie.getId(), ordre.get(2)); // on passe simplement
    }

    // Un joueur inexistant finit son tour (il "passe" son tour à un autre jour) dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void passerKO1() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.passer(null, null); // on passe simplement
    }

    // Un joueur inexistant finit son tour (il "passe" son tour à un autre jour)
    @Test (expected = JoueurPasDansLaPartieException.class)
    public void passerKO2() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException, PasJoueurActifException {
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(2, 2))); // on simule un lancer de dés avec 2/2 comme résultat
        partie.setEtatPartie(partie.getEtatPartie().deplacer()); // on simule un déplacement
        fac.passer(partie.getId(), null); // on passe simplement
    }

    // ------------------------------------------------------------------------
    // après chaque test unitaire ...
    // ------------------------------------------------------------------------

    @After // on vide toutes les listes de la facade
    public void clearFacade() {
        fac.getUsers().clear();
        fac.getConnectedUsers().clear();
        fac.getAvailableUsers().clear();
        fac.getInvitations().clear();
        fac.getParties().clear();
    }
}
