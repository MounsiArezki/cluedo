package webservice_v2;

import webservice_v2.connexionException.DejaInscritException;
import webservice_v2.connexionException.MdpIncorrectException;
import webservice_v2.connexionException.NonInscritException;
import webservice_v2.connexionException.PasConnecteException;
import webservice_v2.entite.Invitation;
import webservice_v2.entite.Joueur;
import webservice_v2.entite.Partie;
import webservice_v2.entite.User;
import webservice_v2.fabrique.FactoryInvitation;
import webservice_v2.fabrique.FactoryPartie;
import webservice_v2.fabrique.FactoryUser;

import java.util.*;
import java.util.stream.Collectors;

public class Facade {

    private static Facade fac = new Facade();

    private FactoryUser facU; //factory à utilisateur
    private FactoryInvitation facI; //factory à invitation
    private FactoryPartie facP; //factory à partie (est-ce qu'on gère qu'une partie ou plusieurs ?)

    private Map<String,User> listeUsers; //liste d'utilisateurs
    private Map<String,User> listeUserssersConnectes; //liste d'utilisateurs connectés (?)
    private Map<String, Invitation> listeInvitations; //liste d'invitations
    private Map<String, Partie> listeParties; //liste de partie
    private Map<String,Partie> listePartiesartiesMongo; //liste de partie sauvegardées pour "simuler mongo"
    
    
    private Facade() {
        facI = FactoryInvitation.getFacI(); //factory à invitation
        facP = FactoryPartie.getFacP(); //factory à partie (est-ce qu'on gère qu'une partie ou plusieurs ?)

        listeUsers = new HashMap<>(); //liste d'utilisateurs
        listeUserssersConnectes = new HashMap<>(); //liste d'utilisateurs connectés (?)
        listeInvitations = new HashMap<>(); //liste d'invitations
        listeParties = new HashMap<>(); //liste de partie
        listePartiesartiesMongo = new HashMap<>(); //liste de partie sauvegardées pour "simuler mongo"
    }

   

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les utilisateurs
    // ----------------------------------------------------------------------------------------

    // récupérer tous les utilisateurs
    public Collection<User> getUsers() {
        return listeUsers.values();
    }

    // ajouter un utilisateur
    public User addUser(String pseudo, String pwd) throws DejaInscritException {
        User test=new User(pseudo);
        if(listeUsers.containsValue(test)){
            throw new DejaInscritException();
        }
        User u = facU.createUser(pseudo, pwd); // création de l'utilisateur
        listeUsers.put(u.getId(), u);
        return u;
    }

    // trouver un utilisateur par son id
    public User findUser(String id) {
        User u=null;
        if(listeUsers.containsKey(id)){
            u=listeUsers.get(id);
        }
        return u;
    }

    // trouver un utilisateur par son pseudo/login
    public User findUserByLogin(String login) {
        User user=null;
        List<User> lu = listeUsers
                .values()
                .stream()
                .filter(u -> u.getPseudo().equals(login))
                .collect(Collectors.toList()); // filtrage

        if (!lu.isEmpty()){
            user=lu.get(0); // si la liste n'est pas vide, on récupère l'utilisateur
        }
        return user; // sinon on retourne null
    }

    // recherche des utilisateurs avec un début de pseudo
    public Collection<User> filterUserByLogin(String db) {
        return listeUsers
                .values()
                .stream()
                .filter(u -> u.getPseudo().startsWith(db))
                .collect(Collectors.toList()); // filtrage
    }

    // supprimer un utilisateur
    public void removeUser(String id) {
        listeUsers.remove(id);
    }

    // connexion (vérification pwd saisi = pwd utilisateur)
    public User connexion(String ps, String pwd) throws MdpIncorrectException, NonInscritException {
        User u = findUserByLogin(ps);
        if (u == null) {
            throw new NonInscritException();
        }
        if (!u.getPwd().equals(pwd)){ //mdp incorrect
            throw new MdpIncorrectException();
        }
        listeUserssersConnectes.put(u.getId(), u); //ajout à la liste des connectés
        return u;
}

    // déconnexion
    public User deconnexion(String ps) throws PasConnecteException{
        User u = findUserByLogin(ps);
        if (u == null){
            throw new PasConnecteException();
        }
        listeUserssersConnectes.remove(u.getId());
        return u;
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les invitations
    // ----------------------------------------------------------------------------------------

    // récupérer toutes les invitations
    public Collection<Invitation> getInvitations() { return listeInvitations.values(); }

    // ajout une invitation
    public Invitation addInvitation(String idP, String idH, List<User> lj) {
        User hote = findUser(idH);
        Invitation i = facI.createInvitation(idP, hote, lj);
        listeInvitations.put(i.getId(), i);
        return i;
    }

    // trouver une invitation par son id
    public Invitation findInvitation(String id) {
        Invitation i=null;
        if(listeInvitations.containsKey(id)){
            i=listeInvitations.get(id);
        }
        return i;
    }

    // trouver une invitation par son id Partie
    public Invitation findInvitationByPartie(String idP) {
        Invitation invitation=null;
        List<Invitation> li = listeInvitations
                .values()
                .stream()
                .filter(i -> i.getIdPartie().equals(idP))
                .collect(Collectors.toList());

        if (!li.isEmpty()){
            invitation=li.get(0);
        }
        return invitation;
    }

    // trouver des invitations par l'id de son Hôte (utilisateur créateur)
    public Collection<Invitation> findInvitationByHost(String idU) {
        User hote=findUser(idU);
        return listeInvitations
                .values()
                .stream()
                .filter(i -> i.getHote().equals(hote))
                .collect(Collectors.toList());
    }

    // trouver des invitations par l'id d'un invité (utilisateur compris dans l'invitation)
    public Collection<Invitation> findInvitationByGuest(String idU) {
        User user=findUser(idU);
        return listeInvitations
                .values()
                .stream()
                .filter(i -> i.getInvites().contains(user))
                .collect(Collectors.toList());
    }

    // l'utilisateur avec idU accepte l'invitation idI
    public boolean accepterInvitation(String idI, String idU) {
        User u = findUser(idU);
        Invitation i = findInvitation(idI);
        boolean res=false;
        if (i != null) {
            if (i.getInvites().contains(u)) { // si l'utilisateur est dans la liste des invités
                i.getInvites().remove(u); // on le retire de la liste des invités
                Partie partie=findPartie(i.getIdPartie());
                partie.getJoueurs().put(idU, new Joueur()); // on l'intègre à la partie
                checkInvitationPartie(i,partie);
                res= true;
            }
        }
        return res;
    }

    // l'utilisateur avec idU refuse l'invitation idI
    public boolean refuserInvitation(String idI, String idU) {
        User u = findUser(idU);
        Invitation i = findInvitation(idI);

        boolean res=false;
        if (i != null) {
            Partie partie=findPartie(i.getIdPartie());
            i.getInvites().remove(u); // on le retire simplement de la liste des invités
            checkInvitationPartie(i,partie);
            res=true;
        }
        return res;
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
        listeInvitations.remove(id);
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les parties
    // ----------------------------------------------------------------------------------------

    // récupérer les parties
    public Collection<Partie> getParties() {
        return listeParties.values();
    }

    // ajout d'une partie
    public Partie addPartie(String idH) {
        Partie p = facP.createPartie(findUser(idH));
        listeParties.put(p.getId(),p);
        return p;
    }

    // trouver une partie par son id
    public Partie findPartie(String id) {
        Partie partie=null;
        if(listeParties.containsKey(id)){
            partie=listeParties.get(id);
        }
        return partie;
    }

    // trouver les parties SAUVEGARDEES par l'id de son Hôte
    public Collection<Partie> findPartieSauvegardeesByHost(String idH) {
        return listePartiesartiesMongo
                .values()
                .stream()
                .filter(p -> p.getHote().getId().equals(idH))
                .collect(Collectors.toList());
    }

    // sauvegarde d'une partie
    public Partie savePartie(String idP, String idH) {
        Partie p = findPartie(idP);
        if (idH.equals(p.getHote().getId())){
            listePartiesartiesMongo.put(p.getId(), p);
            listeParties.remove(idP);
        }
        return p;
    }

    // restauration d'une partie
    public Partie restorePartie(String idP, String idH) {
        Partie ps = listePartiesartiesMongo.get(idP);
        if(idH.equals(ps.getHote().getId())){
            listePartiesartiesMongo.remove(ps);
            listeParties.put(ps.getId(),ps);
        }
        return ps;
    }

    // supprimer une partie par son id
    public void removePartie(String id) {
        listeParties.remove(id);
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes statiques
    // ----------------------------------------------------------------------------------------

    // récupérer le singleton de la facade
    public static Facade getFac() { return fac; }
}
