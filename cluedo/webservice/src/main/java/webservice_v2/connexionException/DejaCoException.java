package webservice.webservice.modele.exception.connexionException;

public class DejaCoException extends Throwable {
    public DejaCoException() {
        super("Vous êtes déjà connecté");
    }
}
