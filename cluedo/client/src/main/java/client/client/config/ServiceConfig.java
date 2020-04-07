package client.client.config;

import client.client.service.Facade;


public class ServiceConfig {

    public static String BASE_URL="http://localhost:8080/serv";

    //
    // USER
    //
    public static String USER_ID_PARAM="id";
    public static String URL_USER=BASE_URL+"/user";
    public static String URL_USER_CONNEXION=URL_USER+"/connexion";
    public static String URL_USER_DECONNEXION=URL_USER_CONNEXION+"/{id}";
    public static String URL_USER_ID=URL_USER+"/{id}";
    public static String URL_USER_ID_INVITATION_RECU=URL_USER_ID+"/invitation/recue";
    public static String URL_USER_ID_INVITATION_EMISE=URL_USER_ID+"/invitation/emise";

    //
    // JOUEUR
    //
    public static String URL_JOUEUR="/joueur";
    public static String URL_JOUEUR_ID="/joueur/{id}";

    //
    // PARTIE
    //
    public static String PARTIE_ID_PARAM="id";
    public static String URL_PARTIE=BASE_URL+"/partie";
    public static String URL_PARTIE_ID=URL_PARTIE+"/{id}";
    public static String URL_PARTIE_ID_JOUEUR=URL_PARTIE_ID+"/joueur";
    public static String URL_PARTIE_ID_HYPOTHESE=URL_PARTIE_ID+"/hypothese";
    public static String URL_PARTIE_ID_SAUVEGARDE=URL_PARTIE_ID+"/sauvegarde";
    public static String URL_PARTIE_ID_RESTAURATION=URL_PARTIE_ID+"/restauration";
    public static String URL_PARTIE_ID_JOUEUR_CARTE= URL_PARTIE+"/{idP}"+URL_JOUEUR+"/{idJ}/carte";
    public static String URL_PARTIE_ID_JOUEUR_FICHE= URL_PARTIE_ID+""+URL_JOUEUR_ID+"/fiche";

    //
    // INVITATION
    //
    public static String INVITATION_ID_PARAM="id";
    public static String URL_INVITATION=BASE_URL+"/invitation";
    public static String URL_INVITATION_ID=URL_INVITATION+"/{id}";
    public static String URL_INVITATION_ID_ACCEPTATION=URL_INVITATION_ID+"/acceptation";
    public static String URL_INVITATION_ID_REFUS=URL_INVITATION_ID+"/refus";

}
