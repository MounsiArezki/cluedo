package client.client.controleur;

import client.client.exception.ConnexionException;
        import client.client.exception.InscriptionException;
        import client.client.global.VariablesGlobales;
        import client.client.modele.entite.User;
        import client.client.modele.entite.io.FxmlPath;
        import client.client.service.Facade;
        import client.client.service.IUserService;
        import client.client.vue.Login;
        import client.client.vue.Menu;
        import client.client.vue.View;
        import javafx.stage.Stage;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;

        import javax.security.auth.login.LoginException;

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
        ResponseEntity<User> responseEntity= userService.connexion(login,pwd);
        HttpStatus status=responseEntity.getStatusCode();
        if (!HttpStatus.CREATED.equals(status)){
            throw new ConnexionException();
        }
        User user=responseEntity.getBody();
        VariablesGlobales.setUser(user);
        goToMenu(connexionStage);
    }

    public void inscrireCntrl(String login,String password) throws InscriptionException {
        ResponseEntity<User> responseEntity=userService.insciption(login,password);
        HttpStatus status=responseEntity.getStatusCode();
        if (!HttpStatus.CREATED.equals(status)){
            throw new InscriptionException();
        }
    }

    private void goToMenu(Stage menuStage) {
        new MenuControleur(menuStage);
    }
}
