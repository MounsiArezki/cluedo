package webservice_v2.exception;

public class InscriptionIncorrecteException extends Throwable {
    public InscriptionIncorrecteException() {
        super("Vous devez saisir un pseudo ET un mot de passe!");
    }
}
