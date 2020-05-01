package webservice.webservice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import webservice_v2.exception.*;
import webservice_v2.exception.partie.ActionNonAutoriseeException;
import webservice_v2.exception.partie.PasJoueurCourantException;
import webservice_v2.facade.Facade;
import webservice_v2.modele.entite.Invitation;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GestPartieUnit {

    private Facade fac = Facade.getFac();

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
    }

    // ------------------------------------------------------------------------
    // récupération des cartes d'un joueur
    // ------------------------------------------------------------------------

    @Test // Récupération "normale" des cartes d'un joueur
    public void getCartes() throws JoueurPasDansLaPartieException, PartieInexistanteException {
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
    public void getFiche() throws JoueurPasDansLaPartieException, PartieInexistanteException {
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
    public void lancerDes() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.lancerDes(partie.getId(), ordre.get(1));
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
        partie.setEtatPartie(partie.getEtatPartie().lancerDe(Arrays.asList(1, 1)));
        partie.setEtatPartie(partie.getEtatPartie().deplacer());
        fac.lancerDes(partie.getId(), ordre.get(1));
    }

    // Lancement des dés d'un joueur inexistante dans une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void lancerDesKO() throws JoueurPasDansLaPartieException, PartieInexistanteException, PasJoueurCourantException, ActionNonAutoriseeException {
        fac.lancerDes(null, null);
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
