package webservice_v2.exception;

public class MdpIncorrectException extends Throwable {
    public MdpIncorrectException() {
        super("Mot de passe incorrect");
    }
}
