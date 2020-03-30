package webservice_v2.connexionException;

public class MdpIncorrectException extends Throwable {
    public MdpIncorrectException() {
        super("Mot de passe incorrect");
    }
}
