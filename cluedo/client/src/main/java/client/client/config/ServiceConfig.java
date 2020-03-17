package client.client.config;

public class ServiceConfig {

    //
    // USER
    //
    private static String URL_USER="/user";
    private static String URL_USER_CONNEXION="/connexion";
    private static String URL_USER_ID="";
    private static String URL_USER_ID_INVITATION_RECU="/invitation/recue";
    private static String URL_USER_ID_INVITATION_EMISE="/invitation/emise";

    private static String getUrlUser() {
        return URL_USER;
    }

    private static String getUrlUserConnexion() {
        return URL_USER+URL_USER_CONNEXION;
    }

    public static String getUrlUserId(String idUser) {
        return URL_USER+"/"+idUser;
    }

    private static String getUrlUserIdInvitationRecu(String idUser) {
        return URL_USER+"/"+idUser+URL_USER_ID_INVITATION_RECU;
    }

    private static String getUrlUserIdInvitationEmise(String idUser) {
        return URL_USER+"/"+idUser+URL_USER_ID_INVITATION_EMISE;
    }

    //
    // PARTIE
    //
    private static String URL_PARTIE="/partie";
    private static String URL_PARTIE_ID="";
    private static String URL_PARTIE_ID_JOUEUR="/joueur";
    private static String URL_PARTIE_ID_HYPOTHESE="/hypothese";
    private static String  URL_PARTIE_ID_SAUVEGARDE="/sauvegarde";

    private static String getUrlPartie() {
        return URL_PARTIE;
    }

    public static String getUrlPartieId(String idPartie) {
        return URL_PARTIE+"/"+idPartie;
    }

    private static String getUrlPartieJoueur(String idPartie) {
        return URL_PARTIE+"/"+idPartie+URL_PARTIE_ID_JOUEUR;
    }

    public static String getUrlPartieIdHypothese(String idPartie) {
        return URL_PARTIE+"/"+idPartie+URL_PARTIE_ID_HYPOTHESE;
    }

    public static String getUrlPartieIdSauvegarde(String idPartie) {
        return URL_PARTIE+"/"+idPartie+URL_PARTIE_ID_SAUVEGARDE;
    }

    //
    // Invitation
    //
    private static String URL_INVITATION="/invitation";
    private static String URL_INVITATION_ID="/invitation";

    public static String getUrlInvitation() {
        return URL_INVITATION;
    }

    public static String getUrlInvitationId(String idInvitation) {
        return URL_INVITATION+"/"+idInvitation;
    }
}
