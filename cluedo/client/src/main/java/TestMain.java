import client.client.modele.entite.User;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TestMain {

    public static void main(String[] args) {
        User u=new User();
        u.setId("la");
        u.setPseudo("la");
        ResponseEntity<User> r=new ResponseEntity<>(u, HttpStatus.CREATED);
        Gson g=new Gson();
        System.out.println(g.toJson(r));
    }
}
