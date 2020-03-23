package client.client.controleur;

import client.client.exception.connexionException.DejaConnecteException;
import client.client.exception.connexionException.InscriptionException;
import client.client.exception.connexionException.MdpIncorrectOuNonInscritException;
import client.client.global.VariablesGlobales;
        import client.client.modele.entite.User;
        import client.client.modele.entite.io.FxmlPath;
import client.client.service.IProxyV2;
import client.client.service.ProxyV2;
import client.client.vue.Login;
import client.client.vue.View;
        import javafx.stage.Stage;

import java.io.IOException;

public class ConnexionControleur {

    Stage connexionStage;

   // IUserService userService;
    IProxyV2 proxyV2;
    Login login;

    public ConnexionControleur(Stage primaryStage) {
       // userService=new Facade();
        proxyV2 = new ProxyV2();
        this.connexionStage=primaryStage;
        login= (Login) View.creerInstance(connexionStage, FxmlPath.LOGIN.getUrl());
        login.setControleur(this);
        login.show("Login");
    }

    public void loginCntrl(String login,String pwd) throws InterruptedException, MdpIncorrectOuNonInscritException, IOException {
       // User user=userService.connexion(login, pwd);
          User user=proxyV2.connexion(login, pwd);

        VariablesGlobales.setUser(user);
        goToMenu(connexionStage);
    }

    public void inscrireCntrl(String login,String password) throws InscriptionException {
        proxyV2.insciption(login, password);
    }

    private void goToMenu(Stage menuStage) {
        new MenuControleur(menuStage);
    }
}
