package main;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.datafaker.Faker;
import personnages.Ennemi;
import personnages.Heros;
import questions.ListeQuestions;
import utils.TypeEnnemi;

/**
 * Classe représentant un combat entre un héros et un groupe d'ennemis.
 * Elle gère la logique du combat, y compris l'ordre des attaques,
 * l'état des participants, et l'affichage des statistiques.
 */
public class Combat {
    /**
     * Logger pour suivre les événements et les actions pendant le combat.
     */
    private static final Logger logger = Logger.getLogger(Combat.class.getName());

    /**
     * Liste des ennemis participant au combat.
     */
    private final List<Ennemi> ennemis = new ArrayList<>();

    /**
     * Héros participant au combat.
     */
    private final Heros heros;

    /**
     * Nombre de tours;
     */
    private int nbT = 0;

    /**
     * Constructeur qui initialise un combat avec un héros donné.
     * Le nombre d'ennemis est généré aléatoirement, et chaque ennemi est
     * initialisé avec des points de vie, une force d'attaque, et un type.
     *
     * @param heros Héros participant au combat.
     */
    public Combat(Heros heros) {
        Faker faker = new Faker();
        int nbrEnnemis = faker.number().numberBetween(1, 5);
        logger.info("Nombre d'ennemis ajoutés : " + nbrEnnemis);

        for (int i = 0; i < nbrEnnemis; i++) {
            int pv = faker.number().numberBetween(50, 100);
            int forceAtt = faker.number().numberBetween(1, 3);
            ennemis.add(new Ennemi(faker.streetFighter().characters(), pv, forceAtt, TypeEnnemi.aleatoire()));
        }
        this.heros = heros;
    }

    public void addEnnemis(Ennemi ennemi) {
        ennemis.add(ennemi);
    }

    /**
     * Vérifie si le combat est terminé.
     * Le combat se termine si la liste des ennemis est vide ou si le héros est mort.
     *
     * @return `true` si le combat est terminé, sinon `false`.
     */
    private boolean estTerminer() {
        return ennemis.isEmpty() || heros.estMort();
    }

    /**
     * Détermine si le héros attaque en premier.
     * Le héros attaque en premier sauf si l'ennemi est de type {@code GANGSTER}.
     *
     * @param e personnages.Ennemi à vérifier.
     * @return `true` si le héros attaque en premier, sinon `false`.
     */
    private boolean herosAttaqueEnPremier(Ennemi e) {
        return e.getType() != TypeEnnemi.GANGSTER;
    }

    /**
     * Génère une barre de statistiques pour le combat, comprenant
     * les statistiques du héros et la liste des ennemis.
     *
     * @return Barre de statistiques sous forme de chaîne de caractères.
     */
    private String statsBar() {
        StringBuilder sb = new StringBuilder();
        sb.append(heros.statsBar()).append("\n");
        sb.append("personnages.Ennemi(s) :\n");
        for (Ennemi e : ennemis) {
            sb.append("• ").append(e.statsBar()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Génère une barre de statistiques un ennemi en particulier, affichage du nombre de tour.
     *
     * @return Barre de statistiques sous forme de chaîne de caractères.
     */
    private String statsBar(Ennemi e) {
        return "\n[" + nbT + "] " + heros.statsBar() + " \uD83E\uDD3C " + e.statsBar();
    }

    /**
     * Déroule le combat entre le héros et les ennemis.
     * Le combat continue tant que {@link Combat#estTerminer()} retourne false.
     * Le déroulement inclut :
     * <ul>
     * <li>L'ordre des attaques (le héros ou l'ennemi attaque en premier).</li>
     * <li>L'affichage des statistiques après chaque action.</li>
     * <li>La gestion de la mort des participants.</li>
     * </ul>
     */
    public void derouleCombat(ListeQuestions listeQuestions) {
        Ennemi e;
        System.out.println("=== Début des combats ===");
        System.out.println(statsBar());
        while (!estTerminer()) {
            System.out.println("\n=== Début du combat ===");
            e = ennemis.getFirst();
            if(nbT == 0) System.out.println(statsBar(e));
            while (!(heros.estMort() || e.estMort())) {
                nbT++;
                if (herosAttaqueEnPremier(e)) {
                    heros.attaque(ennemis, listeQuestions);
                    Jeu.attendre(500);
                    if (!e.estMort()) {
                        e.attaque(heros);
                        System.out.println(e.getName() + " inflige " + e.getForceAttaque() + " dégâts.");
                        Jeu.attendre(500);
                    }
                } else {
                    e.attaque(heros);
                    System.out.println(e.getName() + " inflige " + e.getForceAttaque() + " dégâts.");
                    Jeu.attendre(500);
                    if (!heros.estMort()) {
                        heros.attaque(ennemis, listeQuestions);
                        Jeu.attendre(500);
                    }
                }
                System.out.println(statsBar(e));
            }
            if (!heros.estMort()) {
                System.out.println("\u001B[31m" + e.getName() + " est mort en " + nbT + " rounds.\u001B[0m");
                nbT=0;
                ennemis.remove(e);
                Jeu.attendre(1000);
            }
        }
    }
}
