package client.client.modele.entite;

import java.util.Objects;

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

    public User(String id, String pseudo, String pwd) {
        this.id = id;
        this.pseudo = pseudo;
        this.pwd = pwd;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return pseudo.equals(user.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo);
    }
}
