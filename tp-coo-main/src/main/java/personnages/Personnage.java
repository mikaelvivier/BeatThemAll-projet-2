package personnages;

import utils.IPersonnage;

/**
 * Classe abstraite représentant un personnage dans le jeu.
 * Cette classe contient les propriétés communes et les fonctionnalités de base
 * pour tous les personnages. Elle implémente l'interface `utils.IPersonnage`.
 */
public abstract class Personnage implements IPersonnage {
    /**
     * Nombre d'attaques que le personnage peut porter.
     */
    private int nbrAttaque;

    /**
     * Nom du personnage.
     */
    private String name;

    /**
     * Points de vie initiaux du personnage.
     */
    private int pv;

    /**
     * Force d'attaque du personnage.
     */
    private int forceAttaque;

    /**
     * Indique si le personnage tir à distance.
     */
    private boolean porterAttaque;

    /**
     * Constructeur de la classe `personnages.Personnage`.
     * 
     * @param name          Nom du personnage.
     * @param pv            Points de vie initiaux du personnage.
     * @param forceAttaque  Force d'attaque du personnage (nombre de dégâts qu'il génère par attaque).
     * @param nombreAttaque Nombre maximum d'attaques possibles par tour.
     */
    public Personnage(String name, int pv, int forceAttaque, int nombreAttaque) {
        this.nbrAttaque = nombreAttaque;
        this.name = name;
        this.pv = pv;
        this.forceAttaque = forceAttaque;
        this.porterAttaque = false;
    }

    /**
     * Récupère le nombre maximum d'attaques que le personnage peut donner.
     * 
     * @return Nombre maximum d'attaques.
     */
    public int getNombreAttaque() {
        return this.nbrAttaque;
    }

    /**
     * Récupère le nom du personnage.
     * 
     * @return Nom du personnage.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Modifie le nom du personnage.
     * 
     * @param name Nouveau nom du personnage.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Récupère les points de vie actuels du personnage.
     * 
     * @return Points de vie actuels.
     */
    public int getPv() {
        return this.pv;
    }

    /**
     * Modifie les points de vie du personnage.
     * 
     * @param pv Nouveaux points de vie.
     */
    public void setPv(int pv) {
        this.pv = pv;
    }

    /**
     * Récupère la force d'attaque du personnage.
     * 
     * @return Force d'attaque.
     */
    public int getForceAttaque() {
        return this.forceAttaque;
    }

    /**
     * Modifie la force d'attaque du personnage.
     * 
     * @param forceAttaque Nouvelle force d'attaque.
     */
    public void setForceAttaque(int forceAttaque) {
        this.forceAttaque = forceAttaque;
    }

    /**
     * Vérifie si le personnage attaque à distance.
     * 
     * @return `true` si le personnage attaque à distance, `false` si il attaque au corps.
     */
    public boolean getPorterAttaque() {
        return this.porterAttaque;
    }

    /**
     * Modifie l'état du personnage pour indiquer s'il attaque à distance.
     * 
     * @param porterAttaque `true` si le personnage attaque à distance.
     */
    public void setPorterAttaque(boolean porterAttaque) {
        this.porterAttaque = porterAttaque;
    }

    /**
     * Vérifie si le personnage est mort (points de vie inférieurs ou égaux à 0).
     * 
     * @return `true` si le personnage est mort, sinon `false`.
     */
    public boolean estMort() {
        return getPv() <= 0;
    }

    /**
     * Génère un nombre aléatoire représentant le nombre d'attaques que le
     * personnage peut porter.
     *
     */
    public void setNombreAttaque() {
        this.nbrAttaque = (int) (Math.random() * this.nbrAttaque + 1);
    }

    /**
     * Affiche les points de vie restants du personnage sous forme de barre de
     * statistiques.
     */
    public void afficherPvRestant() {
        System.out.println(statsBar());
    }

    /**
     * Réalise une attaque sur un autre personnage.
     * Le nombre de dégâts dépend de la force d'attaque du personnage et du nombre
     * d'attaques effectuées.
     * 
     * @param e Le personnage cible de l'attaque.
     */
    public void attaque(Personnage e) {
        for (int i = 0; i < getNombreAttaque(); i++) {
            e.setPv(e.pv - this.forceAttaque);
        }
    }

    /**
     * Génère une représentation textuelle des statistiques du personnage (nom,
     * points de vie et force d'attaque).
     * 
     * @return Barre de statistiques sous forme de chaîne de caractères.
     */
    public String statsBar() {
        return name + " (♥ " + pv + " | ⚔ " + forceAttaque + ")";
    }
}
