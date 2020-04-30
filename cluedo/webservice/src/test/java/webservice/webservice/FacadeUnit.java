package webservice.webservice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import webservice_v2.exception.*;
import webservice_v2.facade.Facade;
import webservice_v2.modele.entite.Invitation;
import webservice_v2.modele.entite.Partie;
import webservice_v2.modele.entite.User;

import java.util.ArrayList;
import java.util.Collections;

public class FacadeUnit {

    private Facade fac = Facade.getFac();

    // ------------------------------------------------------------------------
    // test sur l'ajout de l'utilisateur
    // ------------------------------------------------------------------------

    @Test // Inscription "normale"
    public void addUserOK() throws DejaInscritException, InscriptionIncorrecteException {
        User u1 = fac.addUser("test", "pwd");
        Assert.assertEquals("test", u1.getPseudo());
    }

    // Double inscription d'une même personne
    @Test (expected = DejaInscritException.class)
    public void addUserDIE() throws DejaInscritException, InscriptionIncorrecteException {
        fac.addUser("test", "pwd");
        fac.addUser("test", "pwd");
    }

    // Inscription sans login
    @Test (expected = InscriptionIncorrecteException.class)
    public void addUserKO1() throws DejaInscritException, InscriptionIncorrecteException {
        fac.addUser(null, "pwd");
    }

    // Inscription sans pwd
    @Test (expected = InscriptionIncorrecteException.class)
    public void addUserKO2() throws DejaInscritException, InscriptionIncorrecteException {
        fac.addUser("test", null);
    }

    // ------------------------------------------------------------------------
    // test sur la connexion
    // ------------------------------------------------------------------------

    @Test // Connexion "normale"
    public void connexionOK() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException  {
        User u1 = fac.addUser("test", "pwd");
        User co = fac.connexion(u1.getPseudo(), u1.getPwd());
        Assert.assertEquals("test", co.getPseudo());
    }

    // Connexion sans le bon mdp
    @Test (expected = MdpIncorrectException.class)
    public void connexionMIE() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException {
        fac.addUser("test", "pwd");
        fac.connexion("test", "fail");
    }

    // Connexion sans être inscrit
    @Test (expected = NonInscritException.class)
    public void connexionNIE() throws NonInscritException, DejaCoException, MdpIncorrectException {
        fac.connexion("test", "pwd");
    }

    // Connexion alors qu'on est déjà connecté
    @Test (expected = DejaCoException.class)
    public void connexionDCE() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException {
        User u1 = fac.addUser("test", "pwd");
        fac.connexion(u1.getPseudo(), u1.getPwd());
        fac.connexion(u1.getPseudo(), u1.getPwd());
    }

    // Connexion "sans pseudo"
    @Test (expected = NonInscritException.class) // null n'est pas inscrit ... faudrait peut-être une exception à part ?
    public void connexionKO() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException {
        fac.addUser("test", "pwd");
        fac.connexion(null, "fail");
    }

    // ------------------------------------------------------------------------
    // test sur la déconnexion
    // ------------------------------------------------------------------------

    @Test // Déconnexion "normale"
    public void deconnexionOK() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PasConnecteException {
        User u1 = fac.addUser("test", "pwd");
        User co = fac.connexion(u1.getPseudo(), u1.getPwd());
        User deco = fac.deconnexion(co.getPseudo());
        Assert.assertEquals("test", deco.getPseudo());
    }

    // Déconnexion d'un utilisateur non connecté
    @Test (expected = PasConnecteException.class)
    public void deconnexionPCE() throws DejaInscritException, InscriptionIncorrecteException, PasConnecteException {
        User u1 = fac.addUser("test", "pwd");
        fac.deconnexion(u1.getPseudo());
    }

    // Déconnexion d'un utilisateur sans pseudo
    @Test (expected = PasConnecteException.class) // null n'est pas co ... faudrait peut-être une exception à part ?
    public void deconnexionKO() throws PasConnecteException {
        fac.deconnexion(null);
    }

    // ------------------------------------------------------------------------
    // test l'ajout d'une invitation
    // ------------------------------------------------------------------------

    @Test // Création d'une invitation "normale"
    public void addInvitOK() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        Partie p1 = fac.addPartie(u1.getId()); // création de la partie
        Invitation i1 = fac.addInvitation(p1.getId(), co.getId(), Collections.singletonList(u2)); // création de l'invitation
        Assert.assertEquals(u1.getId(), i1.getHote().getId());
    }

    // Création d'une invitation sans que l'hôte soit connecté
    @Test (expected = PasConnecteException.class)
    public void addInvitPCE() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, PartieInexistanteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        Partie p1 = fac.addPartie(u1.getId()); // création de la partie
        fac.addInvitation(p1.getId(), u1.getId(), Collections.singletonList(u2)); // création de l'invitation
    }

    // Création d'une invitation sans invité (mais avec une liste)
    @Test (expected = InvitationInvalideException.class)
    public void addInvitIIE() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        Partie p1 = fac.addPartie(u1.getId()); // création de la partie
        fac.addInvitation(p1.getId(), co.getId(), Collections.emptyList()); // création de l'invitation
    }

    // Création d'une invitation sans invité (liste NULL)
    @Test (expected = InvitationInvalideException.class)
    public void addInvitKO1() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        Partie p1 = fac.addPartie(u1.getId()); // création de la partie
        fac.addInvitation(p1.getId(), co.getId(), null); // création de l'invitation
    }

    // Création d'une invitation sans idPartie
    @Test (expected = PartieInexistanteException.class)
    public void addInvitKO2() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        fac.addInvitation(null, co.getId(), Collections.singletonList(u2)); // création de l'invitation
    }

    // Création d'une invitation sans idHote
    @Test (expected = PasConnecteException.class) // null n'est pas co ... faudrait peut-être une exception à part ?
    public void addInvitKO3() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        Partie p1 = fac.addPartie(u1.getId()); // création de la partie
        fac.addInvitation(p1.getId(), null, Collections.singletonList(u2)); // création de l'invitation
    }

    // ------------------------------------------------------------------------
    // test l'acceptation d'une invitation
    // ------------------------------------------------------------------------

    @Test // Acceptation d'une invitation "normale"
    public void acceptInvitOK() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        Partie p1 = fac.addPartie(u1.getId()); // création de la partie
        Invitation i1 = fac.addInvitation(p1.getId(), co.getId(), new ArrayList<>(Collections.singletonList(u2))); // création de l'invitation (avec liste mutable)
        Assert.assertTrue(fac.accepterInvitation(i1.getId(), u2.getId()));
    }

    // Acceptation d'une invitation sur une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void acceptInvitPIE() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, PartieInexistanteException, NonInscritException, DejaCoException, MdpIncorrectException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        Invitation i1 = fac.addInvitation(null, co.getId(), Collections.singletonList(u2)); // création de l'invitation
        fac.accepterInvitation(i1.getId(), u2.getId());
    }

    // Acceptation d'une invitation sur d'un joueur inexistant
    @Test (expected = NonInscritException.class)
    public void acceptInvitNIE() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, PartieInexistanteException, NonInscritException, DejaCoException, MdpIncorrectException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        Partie p1 = fac.addPartie(u1.getId()); // création de la partie
        Invitation i1 = fac.addInvitation(p1.getId(), co.getId(), Collections.singletonList(u2)); // création de l'invitation
        fac.accepterInvitation(i1.getId(), null);
    }

    // ------------------------------------------------------------------------
    // test le refus d'une invitation
    // ------------------------------------------------------------------------

    @Test // Refuser une invitation "normale"
    public void refusInvitOK() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        Partie p1 = fac.addPartie(u1.getId()); // création de la partie
        Invitation i1 = fac.addInvitation(p1.getId(), co.getId(), new ArrayList<>(Collections.singletonList(u2))); // création de l'invitation (avec liste mutable)
        Assert.assertTrue(fac.refuserInvitation(i1.getId(), u2.getId()));
    }

    // Refus d'une invitation sur une partie inexistante
    @Test (expected = PartieInexistanteException.class)
    public void refusInvitPIE() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, PartieInexistanteException, NonInscritException, DejaCoException, MdpIncorrectException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        Invitation i1 = fac.addInvitation(null, co.getId(), Collections.singletonList(u2)); // création de l'invitation
        fac.refuserInvitation(i1.getId(), u2.getId());
    }

    // Refus d'une invitation sur d'un joueur inexistant
    @Test (expected = NonInscritException.class)
    public void refusInvitNIE() throws DejaInscritException, InscriptionIncorrecteException, InvitationInvalideException, PasConnecteException, PartieInexistanteException, NonInscritException, DejaCoException, MdpIncorrectException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        User u2 = fac.addUser("invite", "pwd"); // création de l'utilisateur invité
        Partie p1 = fac.addPartie(co.getId()); // création de la partie
        Invitation i1 = fac.addInvitation(p1.getId(), co.getId(), Collections.singletonList(u2)); // création de l'invitation
        fac.refuserInvitation(i1.getId(), null);
    }

    // ------------------------------------------------------------------------
    // tests sur la création d'une partie
    // ------------------------------------------------------------------------

    @Test // Création d'une partie "normale"
    public void addPartieOK() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PasConnecteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        Partie p1 = fac.addPartie(co.getId()); // création de la partie
        Assert.assertEquals(u1.getId(), p1.getHote().getId());
    }

    // Création d'une partie sans que l'utilisateur ne soit connecté
    @Test (expected = PasConnecteException.class)
    public void addPartiePCE() throws DejaInscritException, InscriptionIncorrecteException, PasConnecteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        fac.addPartie(u1.getId()); // création de la partie
    }

    // Création d'une partie avec l'utilisateur null
    @Test (expected = PasConnecteException.class)
    public void addPartieKO() throws PasConnecteException {
        fac.addPartie(null); // création de la partie
    }

    // ------------------------------------------------------------------------
    // tests sur la sauvegarde d'une partie
    // ------------------------------------------------------------------------

    @Test // Sauvegarde "normale" d'une partie "normale"
    public void savePartieOK() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PasConnecteException, PartieInexistanteException, PasHotePartieException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        Partie p1 = fac.addPartie(co.getId()); // création de la partie
        Partie psave = fac.savePartie(p1.getId(), co.getId()); // sauvegarde de la partie
        Assert.assertEquals(u1.getId(), psave.getHote().getId());
    }

    // Sauvegarde d'une partie sans que l'utilisateur ne soit connecté
    @Test (expected = PasConnecteException.class)
    public void savePartiePCE() throws DejaInscritException, InscriptionIncorrecteException, PasConnecteException, PartieInexistanteException, PasHotePartieException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        Partie p1 = fac.addPartie(u1.getId()); // création de la partie
        fac.savePartie(p1.getId(), u1.getId()); // sauvegarde de la partie
    }

    // Sauvegarde d'une partie demandé par un autre user que l'hôte
    @Test (expected = PasHotePartieException.class)
    public void savePartiePHPE() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PasConnecteException, PartieInexistanteException, PasHotePartieException {
        User u1 = fac.addUser("hote", "pwd"); // création de l'utilisateur hôte
        User u2 = fac.addUser("fail", "pwd"); // création d'un second utilisateur
        User co1 = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion de l'hôte
        User co2 = fac.connexion(u2.getPseudo(), u2.getPwd()); // connexion du 2nd utilisateur
        Partie p1 = fac.addPartie(co1.getId()); // création de la partie par l'hôte
        fac.savePartie(p1.getId(), co2.getId()); // sauvegarde de la partie par le 2nd utilisateur
    }

    // Sauvegarde d'une partie avec partie null + l'utilisateur null
    @Test (expected = PartieInexistanteException.class)
    public void savePartieKO1() throws PartieInexistanteException, PasHotePartieException {
        fac.savePartie(null, null); // sauvegarde de la partie
    }

    // Sauvegarde d'une partie avec une partie null
    @Test (expected = PartieInexistanteException.class)
    public void savePartieKO2() throws PartieInexistanteException, PasHotePartieException, DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        fac.savePartie(null, co.getId()); // sauvegarde de la partie
    }

    // Sauvegarde d'une partie avec l'utilisateur null
    @Test (expected = PasHotePartieException.class)
    public void savePartieKO3() throws PartieInexistanteException, PasHotePartieException, DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PasConnecteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        Partie p1 = fac.addPartie(co.getId()); // création de la partie
        fac.savePartie(p1.getId(), null); // sauvegarde de la partie
    }

    // ------------------------------------------------------------------------
    // tests sur la restauration d'une partie
    // ------------------------------------------------------------------------

    @Test // Restauration "normale" d'une partie "normale"
    public void recupPartieOK() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PasConnecteException, PartieInexistanteException, PasHotePartieException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        Partie p1 = fac.addPartie(co.getId()); // création de la partie
        Partie psave = fac.savePartie(p1.getId(), co.getId()); // sauvegarde de la partie
        Partie pres = fac.restorePartie(psave.getId(), co.getId()); // restauration de la partie
        Assert.assertEquals(u1.getId(), pres.getHote().getId());
    }

    // Restauration d'une partie par un autre user que l'hôte
    @Test (expected = PasHotePartieException.class)
    public void recupPartiePHPE() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PasConnecteException, PartieInexistanteException, PasHotePartieException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User u2 = fac.addUser("fail", "pwd"); // création d'un second utilisateur
        User co1 = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion de l'hôte
        User co2 = fac.connexion(u2.getPseudo(), u2.getPwd()); // connexion du 2nd utilisateur
        Partie p1 = fac.addPartie(co1.getId()); // création de la partie par l'hôte
        Partie psave = fac.savePartie(p1.getId(), co1.getId()); // sauvegarde de la partie par l'hôte
        fac.restorePartie(psave.getId(), co2.getId()); // restauration de la partie par le 2nd utilisateur
    }

    // Restauration d'une partie non-sauvegardée
    @Test (expected = PartieInexistanteException.class)
    public void recupPartiePIE() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException, PasHotePartieException, PasConnecteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        Partie p1 = fac.addPartie(co.getId()); // création de la partie 1 (qui sera sauvegardée)
        Partie p2 = fac.addPartie(co.getId()); // création de la partie 2 (qui sera restaurée)
        fac.savePartie(p1.getId(), co.getId()); // sauvegarde de la partie 1
        fac.restorePartie(p2.getId(), co.getId()); // restauration de la partie 2
    }

    // Restauration d'une partie null + hôte null
    @Test (expected = PartieInexistanteException.class)
    public void recupPartieKO1() throws PartieInexistanteException, PasHotePartieException {
        fac.restorePartie(null, null); // restauration de la partie
    }

    // Restauration d'une partie null
    @Test (expected = PartieInexistanteException.class)
    public void recupPartieKO2() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException, PasHotePartieException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        fac.restorePartie(null, co.getId()); // restauration de la partie
    }

    // Restauration d'une partie avec hôte null
    @Test (expected = PasHotePartieException.class)
    public void recupPartieKO3() throws DejaInscritException, InscriptionIncorrecteException, NonInscritException, DejaCoException, MdpIncorrectException, PartieInexistanteException, PasHotePartieException, PasConnecteException {
        User u1 = fac.addUser("test", "pwd"); // création de l'utilisateur hôte
        User co = fac.connexion(u1.getPseudo(), u1.getPwd()); // connexion
        Partie p1 = fac.addPartie(co.getId()); // création de la partie
        Partie psave = fac.savePartie(p1.getId(), co.getId()); // sauvegarde de la partie
        fac.restorePartie(psave.getId(), null); // restauration de la partie
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
