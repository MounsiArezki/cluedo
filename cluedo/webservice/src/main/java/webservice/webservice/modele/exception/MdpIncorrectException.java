package webservice.webservice.modele.exception;

public class MdpIncorrectException extends Throwable {
    public MdpIncorrectException() {
        super("Mot de passe incorrect");
    }
}
