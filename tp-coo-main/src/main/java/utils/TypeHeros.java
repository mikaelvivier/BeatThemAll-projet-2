package utils;

import personnages.Ennemi;
import personnages.Heros;

import java.util.List;

/**
 * Enumération représentant les différentes capacités spéciales disponibles pour les héros.
 * Chaque capacité spéciale possède un nom et une description, et peut être utilisée par un héros
 * pour affecter les ennemis ou modifier ses propres statistiques.
 */
public enum TypeHeros {
    /**
     * Capacité "Barbare" : Multiplie la force d'attaque par 3.
     */
    BARBARE("Barbare", "Multiplication de sa force d'attaque x20", 100, 5),

    /**
     * Capacité "Mage" : Soigne les PV à 25% de leur valeur de base et inflige 15 dégâts à un ennemi.
     */
    MAGE("Mage", "Soigner ses PV à 25% de sa barre, et infliger 15 dégâts à un ennemi", 250, 2),

    /**
     * Capacité "Soigneur" : Soigne les PV à 50% de leur valeur actuelle.
     */
    SOIGNEUR("Soigneur", "Soigner ses PV à 50%", 500, 1),

    /**
     * Capacité "Assassin" : Élimine tous les ennemis en un coup.
     */
    ASSASSIN("Assassin", "One shot tous ses ennemis", 150, 2);

    /**
     * Nom de la capacité spéciale.
     */
    private final String name;

    /**
     * Description de la capacité spéciale.
     */
    private final String description;

    /**
     * Nombre de PV 
     */
    private final int pv;

    /**
     * Force d'attaque
     */
    private final int forceAttaque;

    /**
     * Constructeur privé pour initialiser une capacité spéciale avec un nom et une description.
     * 
     * @param name        Nom de la capacité spéciale.
     * @param description Description de la capacité spéciale.
     * @param pv          Nombre de PV
     * @param forceAttaque Force d'attaque
     */
    TypeHeros(String name, String description, int pv, int forceAttaque) {
        this.name = name;
        this.description = description;
        this.pv = pv;
        this.forceAttaque = forceAttaque;
    }

    /**
     * Récupère les points de vie associés à ce type de héros.
     *
     * @return Le nombre de points de vie (PV) du type de héros.
     */
    public int getPv() {
        return pv;
    }

    /**
     * Récupère la force d'attaque associée à ce type de héros.
     *
     * @return La force d'attaque du type de héros.
     */
    public int getForceAttaque() {
        return forceAttaque;
    }


    /**
     * Affiche toutes les capacités spéciales sous forme de cartes.
     * Chaque capacité est affichée avec un numéro incrémental et sa carte associée.
     */
    public static void afficherPossibilites() {
        int i = 1;
        for (TypeHeros capacite : TypeHeros.values()) {
            System.out.println((i++) + ".");
            capacite.afficherCarte();
        }
    }

    /**
     * Affiche une carte visuelle représentant la capacité spéciale.
     * La carte inclut le nom de la capacité et une description.
     */
    public void afficherCarte() {
        int largeurCarte = 45;
        int largeurTexte = largeurCarte - 2;

        StringBuilder descriptionDecoupee = new StringBuilder();
        String[] mots = description.split(" ");
        StringBuilder ligne = new StringBuilder();

        for (String mot : mots) {
            if (ligne.length() + mot.length() + 1 > largeurTexte) {
                descriptionDecoupee.append(ligne).append("\n");
                ligne = new StringBuilder();
            }
            if (!ligne.isEmpty()) {
                ligne.append(" ");
            }
            ligne.append(mot);
        }
        descriptionDecoupee.append(ligne);

        System.out.println("┌" + "─".repeat(largeurCarte) + "┐");
        System.out.printf("│ %-43s │%n", this.name);
        System.out.println("├" + "─".repeat(largeurCarte) + "┤");

        for (String ligneTexte : descriptionDecoupee.toString().split("\n")) {
            System.out.printf("│ %-43s │%n", ligneTexte);
        }

        System.out.println("└" + "─".repeat(largeurCarte) + "┘");
    }

    /**
     * Utilise la capacité spéciale d'un héros sur un ou plusieurs ennemis.
     * Les effets dépendent de la capacité spéciale sélectionnée :
     * <ul>
     * <li><b>Barbare :</b> Multiplie la force d'attaque par 20 et inflige des dégâts à un ennemi.</li>
     * <li><b>Mage :</b> Soigne les PV du héros à 25% et inflige 15 dégâts à un ennemi.</li>
     * <li><b>Soigneur :</b> Soigne les PV de l'ennemi à 50% (erreur possible dans la logique).</li>
     * <li><b>Assassin :</b> Élimine tous les ennemis d'un seul coup.</li>
     * </ul>
     * 
     * @param heros   Le héros utilisant la capacité spéciale.
     * @param ennemis Liste des ennemis affectés par la capacité.
     */
    public static void utilisationCapaciteSpeciale(Heros heros, List<Ennemi> ennemis) {
        if (heros.getTypeHeros() == BARBARE){
            ennemis.getFirst().setPv(ennemis.getFirst().getPv() - (20 * heros.getForceAttaque()));
            System.out.println("\uD83D\uDD25\uD83D\uDD25 : " + heros.getName() + " est en rage et a multiplié les dégâts infligés à son ennemi par 20.");
            System.out.println(heros.getName() + " inflige " + (heros.getForceAttaque() * heros.getNombreAttaque()) * 20 + " dégâts.");
        } else if (heros.getTypeHeros() == MAGE) {
            heros.setPv((int) (heros.getPv() * 1.25));
            ennemis.getFirst().setPv(ennemis.getFirst().getPv() - 15);
            System.out.println("❤ + \uD83D\uDD25 : " + heros.getName() + " c'est soigné à 25% et à infligé 15 dégâts à son ennemi");
        } else if (heros.getTypeHeros() == SOIGNEUR) {
            ennemis.getFirst().setPv((int) (ennemis.getFirst().getPv() * 1.5)); // Semble incohérent (erreur possible).
            System.out.println("❤ : " + heros.getName() + " c'est soigné à 50%.");
            System.out.println(heros.getName() + " inflige " + (heros.getForceAttaque() * heros.getNombreAttaque()) + " dégâts.");
        } else { // Assassin
            while (!ennemis.isEmpty()) {
                ennemis.getFirst().setPv(0);
                ennemis.removeFirst();
            }
            System.out.print("\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25 :" + heros.getName() + " a tué tous les ennemis.");

        }
    }
}
