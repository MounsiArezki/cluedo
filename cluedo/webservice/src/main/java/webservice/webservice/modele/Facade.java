package webservice.webservice.modele;

import webservice.webservice.modele.entite.Invitation;
import webservice.webservice.modele.entite.Joueur;
import webservice.webservice.modele.entite.Partie;
import webservice.webservice.modele.entite.User;
import webservice.webservice.modele.exception.connexionException.MdpIncorrectException;
import webservice.webservice.modele.exception.connexionException.NonInscritException;
import webservice.webservice.modele.fabrique.FactoryInvitation;
import webservice.webservice.modele.fabrique.FactoryPartie;
import webservice.webservice.modele.fabrique.FactoryUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Facade {

    private static Facade fac = new Facade();

    private Facade() {}

    private FactoryUser facU = FactoryUser.getFacU(); //factory à utilisateur
    private FactoryInvitation facI = FactoryInvitation.getFacI(); //factory à invitation
    private FactoryPartie facP = FactoryPartie.getFacP(); //factory à partie (est-ce qu'on gère qu'une partie ou plusieurs ?)

    private Collection<User> listeU = new ArrayList<>(); //liste d'utilisateurs
    private Collection<User> listeUC = new ArrayList<>(); //liste d'utilisateurs connectés (?)
    private Collection<Invitation> listeI = new ArrayList<>(); //liste d'invitations
    private Collection<Partie> listeP = new ArrayList<>(); //liste de partie
    private Collection<Partie> listePSauv = new ArrayList<>(); //liste de partie sauvegardées pour "simuler mongo"

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les utilisateurs
    // ----------------------------------------------------------------------------------------

    // récupérer tous les utilisateurs
    public Collection<User> getUsers() {
        return listeU;
    }

    // ajouter un utilisateur
    public User addUser(String pseudo, String pwd) {

        User u = facU.createUser(pseudo, pwd); // création de l'utilisateur

        listeU.add(u); // ajout à la liste
        return u;
    }

    // trouver un utilisateur par son id
    public User findUser(String id) {
        List<User> lu = listeU.stream().filter(u -> u.getId().equals(id)).collect(Collectors.toList()); // filtrage

        if (!lu.isEmpty()) return lu.get(0); // si la liste n'est pas vide, on récupère l'utilisateur
        else return null; // sinon on retourne null
    }

    // trouver un utilisateur par son pseudo/login
    public User findUserByLogin(String login) {
        List<User> lu = listeU.stream().filter(u -> u.getPseudo().equals(login)).collect(Collectors.toList()); // filtrage

        if (!lu.isEmpty()) return lu.get(0); // si la liste n'est pas vide, on récupère l'utilisateur
        else return null; // sinon on retourne null
    }

    // recherche des utilisateurs avec un début de pseudo
    public Collection<User> filterUserByLogin(String db) {
        return listeU.stream().filter(u -> u.getPseudo().startsWith(db)).collect(Collectors.toList()); // filtrage
    }

    // supprimer un utilisateur
    public void removeUser(String id) {
        listeU = listeU.stream().filter(u -> !u.getId().equals(id)).collect(Collectors.toList()); // filtrage
    }

    // connexion (vérification pwd saisi = pwd utilisateur)
    public User connexion(String ps, String pwd) throws MdpIncorrectException, NonInscritException {
        User u = findUserByLogin(ps);

        if (u == null) {
            throw new NonInscritException();
        }
        if (u.getPwd().equals(pwd)){
            listeUC.add(u); //ajout à la liste des connectés
            return u;
        }
        else throw new MdpIncorrectException(); //mdp incorrect


}

    // déconnexion
    public User deconnexion(String ps) {
        User u = findUserByLogin(ps);

        if (u != null) listeUC.remove(u);

        return u;
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les invitations
    // ----------------------------------------------------------------------------------------

    // récupérer toutes les invitations
    public Collection<Invitation> getInvitations() { return listeI; }

    // ajout une invitation
    public Invitation addInvitation(String idP, String idH, List<User> lj) {
        User hote = findUser(idH);
        Invitation i = facI.createInvitation(idP, hote, lj);
        listeI.add(i);
        return i;
    }

    // trouver une invitation par son id
    public Invitation findInvitation(String id) {
        List<Invitation> li = listeI.stream().filter(i -> i.getId().equals(id)).collect(Collectors.toList());

        if (!li.isEmpty()) return li.get(0);
        else return null;
    }

    // trouver une invitation par son id Partie
    public Invitation findInvitationByPartie(String idP) {
        List<Invitation> li = listeI.stream().filter(i -> i.getIdPartie().equals(idP)).collect(Collectors.toList());

        if (!li.isEmpty()) return li.get(0);
        else return null;
    }

    // trouver des invitations par l'id de son Hôte (utilisateur créateur)
    public Collection<Invitation> findInvitationByHost(String idU) {
        User hote=findUser(idU);
        return listeI.stream().filter(i -> i.getHote().equals(hote)).collect(Collectors.toList());
    }

    // trouver des invitations par l'id d'un invité (utilisateur compris dans l'invitation)
    public Collection<Invitation> findInvitationByGuest(String idU) {
        return listeI.stream().filter(i -> i.getInvites().contains(findUser(idU))).collect(Collectors.toList());
    }

    // l'utilisateur avec idU accepte l'invitation idI
    public boolean accepterInvitation(String idI, String idU) {
        User u = findUser(idU);
        Invitation i = findInvitation(idI);

        if (i != null) {
            if (i.getInvites().contains(u)) { // si l'utilisateur est dans la liste des invités
                i.getInvites().remove(u); // on le retire de la liste des invités
                Partie partie=findPartie(i.getIdPartie());
                partie.getJoueurs().put(idU, new Joueur()); // on l'intègre à la partie
                checkInvitationPartie(i,partie);
                return true;
            }
        }

        return false;
    }

    // l'utilisateur avec idU refuse l'invitation idI
    public boolean refuserInvitation(String idI, String idU) {
        User u = findUser(idU);
        Invitation i = findInvitation(idI);
        Partie partie=findPartie(i.getIdPartie());
        if (i != null) {
            i.getInvites().remove(u); // on le retire simplement de la liste des invités
            checkInvitationPartie(i,partie);
            return true;
        }

        return false;
    }

    private void checkInvitationPartie(Invitation invitation, Partie partie) {
        if (invitation.getInvites().size() < 1) {
            if (partie.getJoueurs().size() < 2) {
                partie.getEtatPartie().finir();
            }
            else {
                partie.getEtatPartie().init();
            }
        }
    }

    // suppression d'une invitation par son id
    public void removeInvitation(String id) {
        listeI = listeI.stream().filter(i -> !i.getId().equals(id)).collect(Collectors.toList());
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les parties
    // ----------------------------------------------------------------------------------------

    // récupérer les parties
    public Collection<Partie> getParties() { return listeP; }

    // ajout d'une partie
    public Partie addPartie(String idH) {
        Partie p = facP.createPartie(findUser(idH));
        listeP.add(p);
        return p;
    }

    // trouver une partie par son id
    public Partie findPartie(String id) {
        List<Partie> lp = listeP.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList());

        if (!lp.isEmpty()) return lp.get(0);
        else return null;
    }

    // trouver les parties par l'id de son Hôte
    public Collection<Partie> findPartieByHost(String idH) {
        return listeP.stream().filter(p -> p.getHote().getId().equals(idH)).collect(Collectors.toList());
    }

    // sauvegarde d'une partie
    public Partie savePartie(String idP, String idH) {
        Partie p = findPartie(idP);

        if (idH.equals(p.getHote().getId())) listePSauv.add(p);

        return p;
    }

    // sauvegarde d'une partie
    public Partie restorePartie(String idP, String idH) {
        Partie ps = null;

        List<Partie> lp = listePSauv.stream().filter(p -> p.getId().equals(idP) && p.getHote().getId().equals(idH)).collect(Collectors.toList());

        if (!lp.isEmpty()) { ps = lp.get(0); listePSauv.remove(ps); }

        return ps;
    }

    // supprimer une partie par son id
    public void removePartie(String id) {
        listeP = listeP.stream().filter(p -> !p.getId().equals(id)).collect(Collectors.toList());
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes statiques
    // ----------------------------------------------------------------------------------------

    // récupérer le singleton de la facade
    public static Facade getFac() { return fac; }
}
