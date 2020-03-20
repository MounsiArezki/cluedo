package webservice.webservice.modele;

import webservice.webservice.modele.entite.Invitation;
import webservice.webservice.modele.entite.Partie;
import webservice.webservice.modele.entite.User;
import webservice.webservice.modele.exception.DejaCoException;
import webservice.webservice.modele.exception.MdpIncorrectException;
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
    public Collection<User> getUsers() { return listeU; }

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

    // supprimer un utilisateur
    public void removeUser(String id) {
        listeU = listeU.stream().filter(u -> !u.getId().equals(id)).collect(Collectors.toList()); // filtrage
    }

    // connexion (vérification pwd saisi = pwd utilisateur)
    public User connexion(String ps, String pwd) throws DejaCoException, MdpIncorrectException {
        User u = findUserByLogin(ps);

        if (u != null) {
            if (u.getPwd().equals(pwd)) listeUC.add(u); //ajout à la liste des connectés
            else if (listeUC.contains(u)) throw new DejaCoException(); //déjà connecté
            else throw new MdpIncorrectException(); //mdp incorrect
        }

        return u;
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
    public Invitation addInvitation(String idP, String idH, List<String> lj) {
        Invitation i = facI.createInvitation(idP, idH, lj);
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

    // trouver une invitation par l'id de son Hôte (utilisateur créateur)
    public Invitation findInvitationByHost(String idU) {
        List<Invitation> li = listeI.stream().filter(i -> i.getIdHote().equals(idU)).collect(Collectors.toList());

        if (!li.isEmpty()) return li.get(0);
        else return null;
    }

    // trouve si l'id user est invite
    public boolean findIfInvite(Invitation invitation, String idU) {
        boolean trouve = false;
        if(invitation.getInvites().stream().anyMatch(i -> i.equals(idU))){
            trouve = true;
        }
        return trouve;
    }

    // l'utilisateur avec idU accepte l'invitation idI
    public boolean accepterInvitation(String idI, String idU) {
        Invitation i = findInvitation(idI);

        if (i != null) {
            if (i.getInvites().contains(idU)) { // si l'utilisateur est dans la liste des invités
                i.getInvites().remove(idU); // on le retire de la liste des invités
                findPartie(i.getIdPartie()).getIdJoueurs().add(idU); // on l'intègre à la partie
                return true;
            }
        }

        return false;
    }

    // l'utilisateur avec idU refuse l'invitation idI
    public boolean refuserInvitation(String idI, String idU) {
        Invitation i = findInvitation(idI);

        if (i != null) {
            i.getInvites().remove(idU); // on le retire simplement de la liste des invités
            return true;
        }

        return false;
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
        Partie p = facP.createPartie(idH);
        listeP.add(p);
        return p;
    }

    // sauvegarde d'une partie
    public Partie savePartie(String idH) {
        Partie partie = findPartieByHost(idH);
        listePSauv.add(partie);
        return partie;
    }

    // trouver une partie par son id
    public Partie findPartie(String id) {
        List<Partie> lp = listeP.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList());

        if (!lp.isEmpty()) return lp.get(0);
        else return null;
    }

    // trouver une partie par l'id de son Hôte
    public Partie findPartieByHost(String idH) {
        List<Partie> lp = listeP.stream().filter(p -> p.getIdHote().equals(idH)).collect(Collectors.toList());

        if (!lp.isEmpty()) return lp.get(0);
        else return null;
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
