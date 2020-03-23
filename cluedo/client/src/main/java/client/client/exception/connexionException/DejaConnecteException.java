package client.client.exception.connexionException;

public class DejaConnecteException extends Exception {

    public DejaConnecteException() {
        super("Utilisateur deja connecté ");
    }
}
