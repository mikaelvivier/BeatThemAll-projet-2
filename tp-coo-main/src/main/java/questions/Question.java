package questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe représentant une question de type QCM avec plusieurs choix et une bonne réponse.
 */
public class Question {
    // Attributs de la classe
    private final String question;
    private final List<String> choix;
    private final String bonneReponse;

    /**
     * Constructeur pour initialiser une question avec ses choix et la bonne réponse.
     *
     * @param question     La question à poser.
     * @param choix        Une liste de choix pour la question.
     * @param bonneReponse La bonne réponse (doit correspondre exactement à l'un des choix).
     */
    public Question(String question, List<String> choix, String bonneReponse) {
        if (choix.size() != 4) {
            throw new IllegalArgumentException("Il doit y avoir exactement 4 choix.");
        }
        this.question = question;
        this.choix = new ArrayList<>(choix);
        this.bonneReponse = bonneReponse;
    }

    /**
     * Affiche la question et les choix dans la console.
     */
    public void afficherQuestion() {
        System.out.println("^^ " + question + " ^^");
        for (int i = 0; i < choix.size(); i++) {
            System.out.println("-  " + (i + 1) + ". " + choix.get(i));
        }
        System.out.print("Choisissez le numéro correspondant à la réponse correcte : ");
    }

    /**
     * Vérifie si le choix de l'utilisateur correspond à la bonne réponse.
     *
     * @param choixNum Le numéro du choix choisi par l'utilisateur.
     * @return true si le choix correspond à la bonne réponse, sinon false.
     */
    public boolean choixJoueurEstBonneReponse(int choixNum) {
        if (choixNum < 1 || choixNum > choix.size()) {
            System.out.println("Choix invalide !");
            return false;
        }

        String reponseJoueur = choix.get(choixNum - 1);
        if (reponseJoueur.equals(bonneReponse)) {
            System.out.println("BONNE REPONSE");
        } else {
            System.out.println("Mauvaise réponse. La bonne réponse est : " + bonneReponse);
        }
        return reponseJoueur.equals(bonneReponse);
    }

    /**
     * Méthode globale pour afficher la question, gérer l'entrée de l'utilisateur
     * et vérifier si la réponse est correcte.
     *
     * @param scanner Scanner utilisé pour lire l'entrée utilisateur.
     * @return true si l'utilisateur a donné la bonne réponse, sinon false.
     */
    public boolean poserQuestion(Scanner scanner) {
        int choixNum = -1;
        afficherQuestion();
        while (choixNum < 1 || choixNum > choix.size()) {
            try {
                choixNum = scanner.nextInt();
                scanner.nextLine(); // Vide le buffer
                if (choixNum < 1 || choixNum > choix.size()) {
                    System.out.print("Entrée invalide. Veuillez saisir un numéro entre 1 et 4 : ");
                }
            } catch (Exception e) {
                System.out.print("Entrée invalide. Veuillez saisir un numéro entre 1 et 4 : ");
                scanner.nextLine(); // Vide le buffer en cas d'erreur
            }
        }
        return choixJoueurEstBonneReponse(choixNum);
    }
}
