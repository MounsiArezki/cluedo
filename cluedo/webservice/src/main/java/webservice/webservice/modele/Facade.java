package webservice.webservice.modele;

import webservice.webservice.modele.entite.Invitation;
import webservice.webservice.modele.entite.Partie;
import webservice.webservice.modele.entite.User;
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

    private FactoryUser facU = FactoryUser.getFacU();
    private FactoryInvitation facI = FactoryInvitation.getFacI();
    private FactoryPartie facP = FactoryPartie.getFacP();

    private Collection<User> listeU = new ArrayList<>();
    private Collection<User> listeUC = new ArrayList<>();
    private Collection<Invitation> listeI = new ArrayList<>();
    private Collection<Partie> listeP = new ArrayList<>();

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les utilisateurs
    // ----------------------------------------------------------------------------------------

    public Collection<User> getUsers() { return listeU; }

    public User addUser(String pseudo, String pwd) {
        User u = facU.createUser(pseudo, pwd);
        listeU.add(u);
        return u;
    }

    public User findUser(long id) {
        List<User> lu = listeU.stream().filter(u -> u.getId() == id).collect(Collectors.toList());

        if (lu.size() > 0) return lu.get(0);
        else return null;
    }

    public User findUserByLogin(String login) {
        List<User> lu = listeU.stream().filter(u -> u.getPseudo().equals(login)).collect(Collectors.toList());

        if (lu.size() > 0) return lu.get(0);
        else return null;
    }

    public void removeUser(long id) {
        listeU = listeU.stream().filter(u -> u.getId() != id).collect(Collectors.toList());
    }

    public User connexion(String ps, String pwd) throws Exception {
        User u = findUserByLogin(ps);

        if (u != null) {
            if (u.getPwd().equals(pwd)) listeUC.add(u);
            else if (listeUC.contains(u)) throw new Exception(); //déjà connecté
            else throw new Exception(); //mdp incorrect
        }

        return u;
    }

    public User deconnexion(String ps) {
        User u = findUserByLogin(ps);

        if (u != null) listeUC.remove(u);

        return u;
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les invitations
    // ----------------------------------------------------------------------------------------

    public Collection<Invitation> getInvitations() { return listeI; }

    public Invitation addInvitation(long idP, long idH, List<Long> lj) {
        Invitation i = facI.createInvitation(idP, idH, lj);
        listeI.add(i);
        return i;
    }

    public Invitation findInvitation(long id) {
        List<Invitation> li = listeI.stream().filter(i -> i.getId() == id).collect(Collectors.toList());

        if (li.size() > 0) return li.get(0);
        else return null;
    }

    public Invitation findInvitationByPartie(long idP) {
        List<Invitation> li = listeI.stream().filter(i -> i.getIdPartie() == idP).collect(Collectors.toList());

        if (li.size() > 0) return li.get(0);
        else return null;
    }

    public Invitation findInvitationByHost(long idU) {
        List<Invitation> li = listeI.stream().filter(i -> i.getIdHote() == idU).collect(Collectors.toList());

        if (li.size() > 0) return li.get(0);
        else return null;
    }

    public boolean accepterInvitation(long idI, long idU) {
        Invitation i = findInvitation(idI);

        if (i != null) {
            if (i.getInvites().contains(idU)) {
                i.getInvites().remove(idU);
                findPartie(i.getIdPartie()).getIdJoueurs().add(idU);
                return true;
            }
        }

        return false;
    }

    public boolean refuserInvitation(long idI, long idU) {
        Invitation i = findInvitation(idI);

        if (i != null) {
            i.getInvites().remove(idU);
            return true;
        }

        return false;
    }

    public void removeInvitation(long id) {
        listeI = listeI.stream().filter(i -> i.getId() != id).collect(Collectors.toList());
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes sur les parties
    // ----------------------------------------------------------------------------------------

    public Collection<Partie> getParties() { return listeP; }

    public Partie addPartie(long idH) {
        Partie p = facP.createPartie(idH);
        listeP.add(p);
        return p;
    }

    public Partie findPartie(long id) {
        List<Partie> lp = listeP.stream().filter(p -> p.getId() == id).collect(Collectors.toList());

        if (lp.size() > 0) return lp.get(0);
        else return null;
    }

    public Partie findPartieByHost(long idH) {
        List<Partie> lp = listeP.stream().filter(p -> p.getIdHote() == idH).collect(Collectors.toList());

        if (lp.size() > 0) return lp.get(0);
        else return null;
    }

    public void removePartie(long id) {
        listeP = listeP.stream().filter(p -> p.getId() != id).collect(Collectors.toList());
    }

    // ----------------------------------------------------------------------------------------
    // Méthodes statiques
    // ----------------------------------------------------------------------------------------

    public static Facade getFac() { return fac; }
}
