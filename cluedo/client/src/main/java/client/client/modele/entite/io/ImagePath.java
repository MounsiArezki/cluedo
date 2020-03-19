package client.client.modele.entite.io;

public enum ImagePath {




    PLATEAU_IMG("/image/cluedo.png"),
    MOUTARDE_IMG("/image/Personnage/MOUTARDE.jpg"),
    ROSE_IMG("/image/Personnage/ROSE.jpg"),
    OLIVE_IMG("/image/Personnage/OLIVE.jpg"),
    ORCHIDE_IMG("/image/Personnage/ORCHIDEE.jpg"),
    PERVENCHE_IMG("/image/Personnage/PERVENCHE.jpg"),
    VIOLET_IMG("/image/Personnage/VIOLET.jpg"),

    CLE_IMG("/image/Arme/CLE.jpg"),
    CORDE_IMG("/image/Arme/CORDE.jpg"),
    COUTEAU_IMG("/image/Arme/COUTEAU.jpg"),
    TUYAU_EN_PLOMP_IMG("/image/Arme/TUYAU_EN_PLOMB.jpg"),
    CHANDELIER_IMG("/image/Arme/CHANDELIER.jpg"),
    REVOLVER_IMG("/image/Arme/REVOLVER.jpg");




    private String url;

    ImagePath(String  url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
