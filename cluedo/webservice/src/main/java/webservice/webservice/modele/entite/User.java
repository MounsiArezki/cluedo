package webservice.webservice.modele.entite;

import java.util.Objects;

public class User {

    private String id;
    private String pseudo;
    private String pwd;

    private static long lastId = 0L;

    public User(String pseudo, String password) {
        this.id = String.valueOf(++lastId);
        this.pseudo = pseudo;
        this.pwd = password;
    }

    public String getId() { return id; }

    public String getPseudo() { return pseudo; }

    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getPwd() { return pwd; }

    public void setPwd(String pwd) { this.pwd = pwd; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(pseudo, user.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pseudo);
    }
}
