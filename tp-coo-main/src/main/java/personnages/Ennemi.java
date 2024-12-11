package personnages;

import utils.TypeEnnemi;

/**
 * Classe représentant un ennemi dans le jeu.
 * Hérite de la classe abstraite {@link Personnage} et ajoute des fonctionnalités spécifiques aux ennemis,
 * notamment la gestion du type d'ennemi et ses attributs associés.
 */
public class Ennemi extends Personnage {
    /**
     * Type de l'ennemi (Brigand, Catcheur, Gangster).
     */
    private final TypeEnnemi typeEnnemi;

    /**
     * Constructeur pour créer un ennemi avec un nom, des points de vie, une force d'attaque, et un type.
     * Chaque type peut modifier les caractéristiques de l'ennemi via {@link #ajoutAttributType()}.
     * 
     * @param name          Nom de l'ennemi.
     * @param pv            Points de vie de l'ennemi.
     * @param forceAttaque  Force d'attaque de l'ennemi.
     * @param type          Type de l'ennemi (détermine ses caractéristiques spécifiques).
     */
    public Ennemi(String name, int pv, int forceAttaque, TypeEnnemi type) {
        super(name, pv, forceAttaque, 1);
        this.typeEnnemi = type;
        ajoutAttributType();
    }

    /**
     * Récupère le type de l'ennemi.
     * 
     * @return Le type de l'ennemi (Brigand, Catcheur, Gangster).
     */
    public TypeEnnemi getType() {
        return this.typeEnnemi;
    }

    /**
     * Modifie les caractéristiques de l'ennemi en fonction de son type.
     * <ul>
     * <li>Si le type est {@code Type.CATCHEUR}, les points de vie sont augmentés de 50%.</li>
     * <li>Si le type est {@code Type.GANGSTER}, l'ennemi est prêt à porter une attaque immédiatement.</li>
     * </ul>
     */
    private void ajoutAttributType() {
        if (typeEnnemi == TypeEnnemi.CATCHEUR) {
            this.setPv((int) (this.getPv() * 1.5));
        } else if (typeEnnemi == TypeEnnemi.GANGSTER) {
            this.setPorterAttaque(true);
        }
    }

    /**
     * Génère une représentation textuelle des statistiques de l'ennemi.
     * Affiche les points de vie, la force d'attaque et son type.
     *
     * @return Barre de statistiques sous forme de chaîne de caractères.
     */
    public String statsBar() {
        return super.statsBar().substring(0, super.statsBar().length() - 1) + " | Type : " + this.getType() + ")";
    }
}
