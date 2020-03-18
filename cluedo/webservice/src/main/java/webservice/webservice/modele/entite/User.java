package webservice.webservice.modele.entite;

public class User {

    private long id;
    private String pseudo;
    private String pwd;

    private static long lastId = 0L;

    public User(String pseudo, String password) {
        this.id = ++lastId;
        this.pseudo = pseudo;
        this.pwd = password;
    }

    public long getId() { return id; }

    public String getPseudo() { return pseudo; }

    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getPwd() { return pwd; }

    public void setPwd(String pwd) { this.pwd = pwd; }
}
