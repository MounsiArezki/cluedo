package webservice_v2.connexionException;

public class DejaCoException extends Throwable {
    public DejaCoException() {
        super("Vous êtes déjà connecté");
    }
}
