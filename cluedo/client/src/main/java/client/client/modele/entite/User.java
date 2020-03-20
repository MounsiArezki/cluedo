package client.client.modele.entite;

public class User {

    private String id;

    private String pseudo;

    private String pwd;

    public User() {
    }

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.pwd = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String password) {
        this.pwd = password;
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
