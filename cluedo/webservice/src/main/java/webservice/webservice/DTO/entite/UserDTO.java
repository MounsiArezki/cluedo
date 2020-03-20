package webservice.webservice.DTO.entite;

import webservice.webservice.modele.entite.User;

public class UserDTO {

    private String id;
    private String pseudo;
    private String pwd;

    public UserDTO(){}

    public static UserDTO creer(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPseudo(user.getPseudo());
        return userDTO;
    }

    public String getId() { return id; }


    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() { return pseudo; }

    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getPwd() { return pwd; }

    public void setPwd(String pwd) { this.pwd = pwd; }
}
