import client.client.config.ServiceConfig;
import client.client.modele.entite.User;
import client.client.service.Facade;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class TestMain {

    public static void main(String[] args) {
        Gson g=new Gson();
        Facade f=new Facade();
        f.insciption("la","la");
        f.connexion("la","la");
    }
}
