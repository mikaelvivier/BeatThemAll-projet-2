import main.Jeu;

import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * Classe main de l'application "Beat them all".
 * Cette classe contient le point d'entrée du programme et gère l'initialisation 
 * ainsi que le déroulement principal du jeu.
 */
public class Main {

    /**
     * Méthode d'initialisation qui affiche les messages d'accueil.
     * Elle introduit le jeu et affiche les noms des développeurs.
     * Cette méthode est appelée au début de l'exécution du programme.
     */
    private static void init() {
        System.out.println("Bienvenue sur Beat them all !");
        Jeu.attendre(500); // Pause pour améliorer l'expérience utilisateur
        System.out.println("Développé par Matys LEPRETRE et Benjamin ZAWODA.");
    }

    /**
     * Point d'entrée principal du programme.
     * Cette méthode exécute les étapes suivantes :
     * <ul>
     * <li>Appelle la méthode {@link #init()} pour l'initialisation.</li>
     * <li>Crée une instance de la classe {@code main.Jeu} pour démarrer le jeu.</li>
     * <li>Démarre la boucle principale du jeu où chaque tour est joué successivement.</li>
     * </ul>
     *
     * @param args Arguments de la ligne de commande (non utilisés dans ce programme).
     */
    public static void main(String[] args) {
        init(); // Affiche les messages d'accueil

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("logging.properties")) {
            if (input == null) {
                System.err.println("Fichier logging.properties introuvable dans resources !");
                return;
            }
            LogManager.getLogManager().readConfiguration(input);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de logging.properties : " + e.getMessage());
        }

        Jeu.attendre(500); // Pause pour transition
        Jeu jeu = new Jeu(); // Initialisation du jeu
        jeu.demarrageJeu(); // Démarrage du jeu

        // Boucle principale du jeu
        while (true) {
            Jeu.attendre(1000); // Pause entre les tours
            jeu.jouerTour(); // Joue un tour de jeu
        }
    }
}
