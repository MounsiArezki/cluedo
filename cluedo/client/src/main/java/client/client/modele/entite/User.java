package client.client.modele.entite;

public class User {

    public String pseudo;

    private String password;

    public User() {
    }

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public String toString() {
        return pseudo;
    }
}