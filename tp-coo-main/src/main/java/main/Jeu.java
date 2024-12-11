package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import net.datafaker.Faker;
import personnages.Heros;
import questions.ListeQuestions;
import utils.TypeHeros;

/**
 * Classe représentant le jeu "Beat them all".
 * Cette classe contient la logique principale pour le démarrage, le déroulement et la fin de la partie.
 * Elle gère également la création de la carte, des combats, et les interactions du joueur.
 */
public class Jeu {
    public Heros hero;
    private Carte carte;
    private final List<Combat> combats = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Jeu.class.getName());
    private boolean modeTest = false;
    Faker faker = new Faker();
    ListeQuestions listeQuestions = new ListeQuestions("questions_culture_generale.csv");

    public void activerModeTest() { this.modeTest = true; }

    /**
     * Démarre le jeu en initialisant le héros, la carte, et les combats.
     * Cette méthode guide le joueur dans la sélection d'un héros et du niveau de difficulté,
     * puis génère la carte et place les combats avant de commencer la partie.
     */
    public void demarrageJeu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Entrez votre nom : ");
        String heroName = scanner.nextLine();
        Jeu.attendre(500);
        System.out.println("Sélectionne ton héro : ");
        Jeu.attendre(500);
        TypeHeros.afficherPossibilites();
        System.out.println("Choisissez un nombre entre 1 et 4 :");
        int choixType = scanner.nextInt();
        Jeu.attendre(500);

        TypeHeros type = switch (choixType) {
            case 2 -> TypeHeros.MAGE;
            case 3 -> TypeHeros.SOIGNEUR;
            case 4 -> TypeHeros.ASSASSIN;
            default -> TypeHeros.BARBARE;
        };

        hero = new Heros(heroName, type);
        System.out.println("Vous avez choisi un héro de type " + hero.getTypeHeros());
        logger.info("Hero ajouté (" + hero.getName() + ") = PV / Puissance / Capacité : " + hero.getPv() + " / " + hero.getForceAttaque() + " / " + hero.getTypeHeros());

        System.out.println("Choississez un niveau de difficulté : ");
        System.out.println("1. Facile\n2. Moyen\n3. Difficile");
        int choixNiveau = scanner.nextInt();
        int longueurCarte = switch (choixNiveau) {
            case 2 -> faker.number().numberBetween(10, 20);
            case 3 -> faker.number().numberBetween(20, 40);
            default -> faker.number().numberBetween(5, 10);
        };

        carte = new Carte(faker.streetFighter().stages(), longueurCarte);
        logger.info("main.Carte ajoutée (" + carte.getNom() + ") = Longueur : " + carte.getLongueur());

        this.genererListeCombats(longueurCarte);

        System.out.println("\nDébut de la partie !");

        carte.placerHero(hero);
        carte.placerCombat(combats);
        carte.afficherCarte();
    }

    /**
     * Génère une liste de combats en fonction de la longueur de la carte.
     * 
     * @param longueurCarte Longueur de la carte utilisée pour déterminer le nombre de combats.
     */
    private void genererListeCombats(int longueurCarte) {
        int nbCombat = (int) faker.number().numberBetween(1, (longueurCarte / 1.5));
        for (int i = 0; i < nbCombat; i++) {
            combats.add(new Combat(hero));
        }
    }

    /**
     * Exécute un tour de jeu.
     * Le joueur peut choisir d'avancer ou de quitter le jeu. Si le joueur rencontre des ennemis,
     * il peut choisir de combattre ou de fuir (si il fuit la partie est terminée).
     */
    public void jouerTour() {
        Scanner scanner = new Scanner(System.in);
        int choix = -1;
    
        // Boucle pour obtenir un choix valide
        do {
            try {
                System.out.println("Que voulez-vous faire ?");
                System.out.println("1. Avancer\n2. Quitter");
                choix = scanner.nextInt();
    
                if (choix < 1 || choix > 2) {
                    System.out.println("Choix invalide. Veuillez saisir 1 ou 2.");
                }
            } catch (Exception e) {
                System.out.println("Entrée invalide. Veuillez saisir un nombre.");
                scanner.nextLine();
            }
        } while (choix < 1 || choix > 2);
    
        switch (choix) {
            case 1 -> {
                if (carte.getCase(hero.getPosition() + 1).equals("[!]")) {
                    System.out.println("Vous avez rencontré un groupe d'ennemis !");
                    Jeu.attendre(250);
    
                    int choixCombat = -1;
                    do {
                        try {
                            System.out.println("Voulez-vous combattre ou abandonner ?");
                            System.out.println("1. Combattre\n2. Abandonner");
                            choixCombat = scanner.nextInt();
    
                            if (choixCombat < 1 || choixCombat > 2) {
                                System.out.println("Choix invalide. Veuillez saisir 1 ou 2.");
                            }
                        } catch (Exception e) {
                            System.out.println("Entrée invalide. Veuillez saisir un nombre.");
                            scanner.nextLine();
                        }
                    } while (choixCombat < 1 || choixCombat > 2);
    
                    if (choixCombat == 1) {
                        carte.getPositionsCombats().get(hero.getPosition() + 1).derouleCombat(listeQuestions);
                        if (!hero.estMort()) {
                            System.out.println("\n\u001B[32mVous avez gagné le combat !\u001B[0m");
                            int positionCombat = hero.getPosition() + 1;
                            carte.supprimerCombat(positionCombat);
                            hero.avance(carte);
                        }else finJeu();
                    } else {
                        hero.setPv(0);
                        finJeu();
                    }
                } else {
                    hero.avance(carte);
                }
            }
            case 2 -> {
                hero.setPv(0);
                finJeu();
            }
        }
    
        carte.afficherCarte();
    
        if (hero.getPosition() == carte.getPositionArrivee() - 1) {
            finJeu();
        }
    }
    

    /**
     * Gère la fin de la partie.
     * Affiche un message de victoire si les ennemis ont été vaincus ou de défaite si le héros est mort.
     * Le programme se termine après l'affichage.
     */
    public void finJeu() {
        if (hero.estMort()) {
            System.out.println("\u001B[31mDéfaite.\u001B[0m Le héros est mort !");
            logger.info("Héros mort. Défaite.");
        } else {
            System.out.println("\u001B[32mVictoire.\u001B[0m Les ennemis ont été vaincus !");
            logger.info("Ennemis vaincus. Victoire.");
        }
        System.out.println("Merci d'avoir joué !");
        if (!modeTest) {
            System.exit(0);
        }
    }

    /**
     * Met en pause l'exécution du jeu pour un nombre donné de millisecondes.
     * 
     * @param ms Durée de la pause en millisecondes.
     */
    public static void attendre(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("Le thread a été interrompu : " + e.getMessage());
        }
    }
}
