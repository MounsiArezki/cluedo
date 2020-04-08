package webservice_v2.config;

public class ServiceConfig {

    public final static String BASE_URL="/serv";

    //
    // USER
    //
    public final static String USER_ID_PARAM="id";
    public final static String URL_USER="/user";
    public final static String URL_USER_CONNECTED=URL_USER+"/connected";
    public final static String URL_USER_FILTRE=URL_USER+"/filtre";
    public final static String URL_USER_CONNEXION=URL_USER+"/connexion";
    public final static String URL_USER_DECONNEXION=URL_USER_CONNEXION+"/{id}";
    public final static String URL_USER_ID=URL_USER+"/{id}";
    public final static String URL_USER_ID_INVITATION_RECU=URL_USER_ID+"/invitation/recue";
    public final static String URL_USER_ID_INVITATION_EMISE=URL_USER_ID+"/invitation/emise";
    public final static String URL_USER_ID_PARTIES_SAUVEGARDEES=URL_USER_ID+"/sauvegardees";


    //
    // INVITATION
    //
    public final static String INVITATION_ID_PARAM="id";
    public final static String URL_INVITATION="/invitation";
    public final static String URL_INVITATION_ID=URL_INVITATION+"/{id}";
    public final static String URL_INVITATION_ID_ACCEPTATION=URL_INVITATION_ID+"/acceptation";
    public final static String URL_INVITATION_ID_REFUS=URL_INVITATION_ID+"/refus";


    //
    // PARTIE
    //
    public final static String PARTIE_ID_PARAM="id";
    public final static String URL_PARTIE="/partie";
    public final static String URL_PARTIE_ID=URL_PARTIE+"/{id}";
    public final static String URL_PARTIE_ID_JOUEUR=URL_PARTIE_ID+"/joueur";
    public final static String URL_PARTIE_ID_SAUVEGARDE=URL_PARTIE_ID+"/sauvegarde";
    public final static String URL_PARTIE_ID_RESTAURATION=URL_PARTIE_ID+"/restauration";


    //
    // JOUEUR avec ACTIONS
    //
    public final static String JOUEUR_ID_PARAM="idJ";
    public final static String URL_JOUEUR=URL_PARTIE_ID+"/joueur";
    public final static String URL_JOUEUR_ID=URL_JOUEUR+"/{idJ}";

    // les actions restantes à "refactor" sur partie/idP/joueur/idJ
    public final static String URL_PARTIE_ID_DEPLACER= URL_PARTIE_ID+"/deplacer";
    public final static String URL_PARTIE_ID_FIN_TOUR= URL_PARTIE_ID+"/fintour";
    public final static String URL_PARTIE_ID_REVELER_CARTE= URL_PARTIE_ID+"/revelercarte";

    // les actions traitées
    public final static String URL_PARTIE_ID_JOUEUR_CARTE= URL_JOUEUR_ID+"/carte";
    public final static String URL_PARTIE_ID_JOUEUR_FICHE= URL_JOUEUR_ID+"/fiche";
    public final static String URL_PARTIE_ID_JOUEUR_LANCER= URL_JOUEUR_ID+"/lancerDes";
    public final static String URL_PARTIE_ID_JOUEUR_HYPOTHESE= URL_JOUEUR_ID+"/hypothese";
    public final static String URL_PARTIE_ID_JOUEUR_ACCUSER= URL_JOUEUR_ID+"/accuser";

}
