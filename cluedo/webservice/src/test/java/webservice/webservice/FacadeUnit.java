package webservice.webservice;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import webservice_v2.exception.*;
import webservice_v2.facade.Facade;
import webservice_v2.modele.entite.User;

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
    // après chaque test unitaire ...
    // ------------------------------------------------------------------------

    @After // on vide la liste des utilisateurs et celle des utilisateurs connectés
    public void clearFacade() { fac.getUsers().clear(); fac.getConnectedUsers().clear(); }
}
