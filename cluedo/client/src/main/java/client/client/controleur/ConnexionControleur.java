package client.client.controleur;

import client.client.exception.ConnexionException;
        import client.client.exception.InscriptionException;
        import client.client.global.VariablesGlobales;
        import client.client.modele.entite.User;
        import client.client.modele.entite.io.FxmlPath;
        import client.client.service.Facade;
        import client.client.service.IUserService;
        import client.client.vue.Login;
        import client.client.vue.View;
        import javafx.stage.Stage;
public class ConnexionControleur {

    Stage connexionStage;

    IUserService userService;

    Login login;

    public ConnexionControleur(Stage primaryStage) {
        userService=new Facade();
        this.connexionStage=primaryStage;
        login= (Login) View.creerInstance(connexionStage, FxmlPath.LOGIN.getUrl());
        login.setControleur(this);
        login.show("Login");
    }

    public void loginCntrl(String login,String pwd) throws ConnexionException {
        User user=userService.connexion(login, pwd);
        VariablesGlobales.setUser(user);
        goToMenu(connexionStage);
    }

    public void inscrireCntrl(String login,String password) throws InscriptionException {
        userService.insciption(login, password);
    }

    private void goToMenu(Stage menuStage) {
        new MenuControleur(menuStage);
    }
}
