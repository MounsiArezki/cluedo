package client.client.controleur;

import client.client.exception.connexionException.*;
import client.client.global.VariablesGlobales;
        import client.client.modele.entite.User;
        import client.client.modele.entite.io.FxmlPath;
import client.client.service.Facade;
import client.client.service.IProxyV2;
import client.client.service.ProxyV2;
import client.client.vue.Login;
import client.client.vue.View;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnexionControleur {

    Stage connexionStage;

   // IUserService userService;
    IProxyV2 proxyV2;
    Login login;

    private List<String> tests;

    public boolean fini=false;

    public ConnexionControleur(Stage primaryStage) {
       // userService=new Facade();
        proxyV2 = new ProxyV2();
        this.connexionStage=primaryStage;
        login= (Login) View.creerInstance(connexionStage, FxmlPath.LOGIN.getUrl());
        login.setControleur(this);
        login.show("Login");

        Facade facade=new Facade();
        tests=new ArrayList<>();
        try {
            facade.subscribeToTest(this::getFluxTests);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try{
            facade.postTest("test10");
        }
        catch (HttpStatusCodeException e){
            System.out.println(e.getStatusCode());
        }


    }

    public void loginCntrl(String login,String pwd) throws InterruptedException, MdpIncorrectOuNonInscritException, IOException {
       // User user=userService.connexion(login, pwd);
          User user=proxyV2.connexion(login, pwd);

        VariablesGlobales.setUser(user);
        goToMenu(connexionStage);
    }

    public void inscrireCntrl(String login,String password) throws InscriptionException, DejaInscritException {
        proxyV2.insciption(login, password);
    }

    private void goToMenu(Stage menuStage) {
        new MenuControleur(menuStage);
    }

    public void getFluxTests(String test){
        Platform.runLater( () ->{
        if (test != null) {
            System.out.println(test);
            tests.add(test);
            System.out.println("\n\n\n" + tests);
        }

        });
    }
}
