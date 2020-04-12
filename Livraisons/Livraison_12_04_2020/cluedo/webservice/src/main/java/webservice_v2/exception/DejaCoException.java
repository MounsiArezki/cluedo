package webservice_v2.exception;

public class DejaCoException extends Throwable {
    public DejaCoException() {
        super("Vous êtes déjà connecté");
    }
}
