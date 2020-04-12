package client.client.exception.connexionException;

public class DejaInscritException extends Exception {

    public DejaInscritException() {
        super("le pseudo à été deja utilisé");
    }
}
