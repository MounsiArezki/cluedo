package webservice_v2.modele.entite.carte;

public enum Speciale implements ICarte {
    REVELER_CARTE_ALL("REVELER_CARTE_ALL"),
    NOMER_ARME("NOMER_ARME"),
    NOMER_LIEU("NOMER_LIEU"),
    NOMER_PERSONNAGE("NOMER_PERSONNAGE"),
    MONTRER_CARTE_JOUEUR_GAUCHE("MONTRER_CARTE_JOUEUR_GAUCHE"),
    REVELER_CARTE_JOUEUR_CHOISIT("REVELER_CARTE_JOUEUR_CHOISIT"),
    PASSAGE_SECRET("PASSAGE_SECRET"),
    DEPLACER_ALL_LIEU("DEPLACER_ALL_LIEU");

    private String nom;

    Speciale(String nom) {
        this.nom = nom;
    }

    @Override
    public String getNom() {
        return nom;
    }

    public static ICarte getCarteByNom(String nom) {
        for (ICarte c : Speciale.values()) {
            if (c.getNom().equalsIgnoreCase(nom)) {
                return c;
            }
        }
        return null;
    }
}
