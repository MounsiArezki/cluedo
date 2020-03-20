package webservice.webservice.modele.exception;

public class DejaCoException extends Throwable {
    public DejaCoException() {
        super("Vous êtes déjà connecté");
    }
}
