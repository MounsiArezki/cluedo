package client.client.config;

public class ServiceConfig {

    public static String BASE_URL="http://localhost:8080/";



    //
    // USER
    //
    public static String USER_ID_PARAM="id";
    public static String URL_USER=BASE_URL+"/user";
    public static String URL_USER_CONNEXION=URL_USER+"/connexion";
    public static String URL_USER_ID=URL_USER+"/{id}";
    public static String URL_USER_ID_INVITATION_RECU=URL_USER_ID+"/invitation/recue";
    public static String URL_USER_ID_INVITATION_EMISE=URL_USER_ID+"/invitation/emise";

    //
    // PARTIE
    //
    public static String PARTIE_ID_PARAM="id";
    public static String URL_PARTIE=BASE_URL+"/partie";
    public static String URL_PARTIE_ID=URL_PARTIE+"/{id}";
    public static String URL_PARTIE_ID_JOUEUR=URL_PARTIE_ID+"/joueur";
    public static String URL_PARTIE_ID_HYPOTHESE=URL_PARTIE_ID+"/hypothese";
    public static String  URL_PARTIE_ID_SAUVEGARDE=URL_PARTIE_ID+"/sauvegarde";

    //
    // INVITATION
    //
    public static String INVITATION_ID_PARAM="id";
    public static String URL_INVITATION=BASE_URL+"/invitation";
    public static String URL_INVITATION_ID=URL_INVITATION+"/{id}";

}
