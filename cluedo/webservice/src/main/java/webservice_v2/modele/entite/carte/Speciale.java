package webservice_v2.modele.entite.carte;

public enum Speciale implements ICarte {
    REVELER_CARTE_ALL("Tout les joueurs révelent un carte de sa main."),
    NOMER_ARME("Nommer une arme. Le joueur possédant cette carte doit la révéler."),
    NOMER_LIEU("Nommer un lieu. Le joueur possédant cette carte doit la révéler."),
    NOMER_PERSONNAGE("Nommer un personnage. Le joueur possédant cette carte doit la révéler."),
    MONTRER_CARTE_JOUEUR_GAUCHE("Tous les joueurs montrent une carte discrètement au joueur de gauche."),
    REVELER_CARTE_JOUEUR_CHOISIT("Choisissez le joueur qui a l’air le plus coupable. Il met une carte de sa main face visible devant lui."),
    PASSAGE_SECRET("Vous trouvez un passage secret. Mettez cette carte dans la pièce de votre choix. Elle est maintenant reliée aux autres passages secrets."),
    DEPLACER_ALL_LIEU("Tous les joueurs se précipitent dans la pièce de votre choix.");

    private String nom;

    Speciale(String nom) {
        this.nom = nom;
    }

    @Override
    public String getNom() {
        return nom;
    }
}
