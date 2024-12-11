package utils;

import personnages.Personnage;

/**
 * Interface représentant les fonctionnalités d'un personnage dans le jeu.
 * Cette interface définit les comportements communs à tous les personnages (personnages.Ennemi et personnages.Heros).
 */
public interface IPersonnage {

    /**
     * Récupère le nombre maximum d'attaques que le personnage peut porter.
     * 
     * @return Nombre maximum d'attaques.
     */
    int getNombreAttaque();

    /**
     * Récupère le nom du personnage.
     * 
     * @return Nom du personnage.
     */
    String getName();

    /**
     * Modifie le nom du personnage.
     * 
     * @param name Nouveau nom du personnage.
     */
    void setName(String name);

    /**
     * Récupère les points de vie actuels du personnage.
     * 
     * @return Points de vie actuels.
     */
    int getPv();

    /**
     * Modifie les points de vie du personnage.
     * 
     * @param pv Nouveaux points de vie.
     */
    void setPv(int pv);

    /**
     * Récupère la force d'attaque du personnage.
     * 
     * @return Force d'attaque.
     */
    int getForceAttaque();

    /**
     * Modifie la force d'attaque du personnage.
     * 
     * @param forceAttaque Nouvelle force d'attaque.
     */
    void setForceAttaque(int forceAttaque);

    /**
     * Vérifie si le personnage porte actuellement une attaque.
     * 
     * @return `true` si le personnage porte une attaque, sinon `false`.
     */
    boolean getPorterAttaque();

    /**
     * Modifie l'état du personnage pour indiquer s'il porte une attaque.
     * 
     * @param porterAttaque `true` si le personnage doit porter une attaque.
     */
    void setPorterAttaque(boolean porterAttaque);

    /**
     * Vérifie si le personnage est mort (points de vie inférieurs ou égaux à 0).
     * 
     * @return `true` si le personnage est mort, sinon `false`.
     */
    boolean estMort();

    /**
     * Génère un nombre aléatoire représentant le nombre d'attaques que le
     * personnage peut porter.
     *
     */
    void setNombreAttaque();

    /**
     * Affiche les points de vie restants du personnage sous forme de barre de statistiques.
     */
    void afficherPvRestant();

    /**
     * Réalise une attaque sur un autre personnage.
     * Le nombre de dégâts dépend de la force d'attaque du personnage et du nombre
     * d'attaques effectuées.
     * 
     * @param e Le personnage cible de l'attaque.
     */
    void attaque(Personnage e);

    /**
     * Génère une représentation textuelle des statistiques du personnage (nom,
     * points de vie et force d'attaque).
     * 
     * @return Barre de statistiques sous forme de chaîne de caractères.
     */
    String statsBar();
}
