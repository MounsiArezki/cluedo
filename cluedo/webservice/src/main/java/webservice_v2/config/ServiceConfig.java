package webservice_v2.config;

public class ServiceConfig {

    public final static String BASE_URL="/serv";

    //
    // USER
    //
    public final static String USER_ID_PARAM="id";
    public final static String URL_USER="/user";
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
    public final static String URL_PARTIE_ID_HYPOTHESE=URL_PARTIE_ID+"/hypothese";
    public final static String  URL_PARTIE_ID_SAUVEGARDE=URL_PARTIE_ID+"/sauvegarde";
    public final static String  URL_PARTIE_ID_RESTAURATION=URL_PARTIE_ID+"/restauration";

    public final static String URL_PARTIE_ID_LANCER_DES= URL_PARTIE_ID+"/lancerdes";
    public final static String URL_PARTIE_ID_DEPLACER= URL_PARTIE_ID+"/deplacer";
    public final static String URL_PARTIE_ID_ACCUSER= URL_PARTIE_ID+"/accuser";
    public final static String URL_PARTIE_ID_FIN_TOUR= URL_PARTIE_ID+"/fintour";
    public final static String URL_PARTIE_ID_EMETTRE_HYPOTHESE= URL_PARTIE_ID+"/emettrehypothse";
    public final static String URL_PARTIE_ID_REVELER_CARTE= URL_PARTIE_ID+"/revelercarte";



}
