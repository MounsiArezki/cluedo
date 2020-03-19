package client.client.modele.entite;

public class User {

    private String id;

    private String pseudo;

    private String password;

    public User() {
    }

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
