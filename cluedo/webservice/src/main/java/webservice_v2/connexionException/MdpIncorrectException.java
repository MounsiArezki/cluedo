package webservice.webservice.modele.exception.connexionException;

public class MdpIncorrectException extends Throwable {
    public MdpIncorrectException() {
        super("Mot de passe incorrect");
    }
}
