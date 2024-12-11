import main.Carte;
import main.Combat;
import main.Jeu;
import org.junit.jupiter.api.Test;
import personnages.Ennemi;
import personnages.Heros;
import utils.TypeEnnemi;
import utils.TypeHeros;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestJeu {

    @Test
    void testHeroMort(){
        Jeu jeu = new Jeu();
        jeu.activerModeTest();
        Heros h = new Heros("Walid", TypeHeros.BARBARE);
        h.setPv(10);
        jeu.hero = h;
        h.setPv(0);
        assertTrue(h.estMort());
        jeu.finJeu();
    }

    @Test
    void testHeroGagne(){
        Jeu jeu = new Jeu();
        jeu.activerModeTest();
        Heros hero = new Heros("Ben", TypeHeros.BARBARE);
        jeu.hero = hero;
        Carte carte = new Carte("victoire", 3);
        carte.placerHero(hero);
        while (hero.getPosition() < carte.getPositionArrivee() - 1) hero.avance(carte);
        assertEquals(carte.getPositionArrivee() - 1, hero.getPosition(), "Héro proche de l'arrivée");
        hero.avance(carte);
        assertEquals(carte.getPositionArrivee(), hero.getPosition(), "Le héro a atteint la ligne d'arrivée");
        jeu.finJeu();
        assertFalse(hero.estMort(), "Le héros doit être vivant à la fin.");
    }

    @Test
    void testHeroAttaqueEnnemiAvecPVExactes() {
        Heros hero = new Heros("Arthur", TypeHeros.BARBARE);
        Ennemi ennemi = new Ennemi("Gobelin", (hero.getForceAttaque() * hero.getNombreAttaque()), 0, TypeEnnemi.BRIGAND);

        Combat combat = new Combat(hero);
        combat.addEnnemis(ennemi);
        hero.attaque(ennemi);

        assertEquals(0, ennemi.getPv());
        assertTrue(ennemi.estMort());

        Carte carte = new Carte("test", 5);
        carte.placerHero(hero);
        HashMap<Integer, Combat> positionsCombats = carte.getPositionsCombats();
        int positionCombat = 2;
        positionsCombats.put(positionCombat, combat);

        assertEquals(0, ennemi.getPv(), "Les PV de l'ennemi doivent être 0 après l'attaque.");
        assertTrue(ennemi.estMort(), "L'ennemi doit être mort.");

        carte.supprimerCombat(positionCombat);
        assertFalse(positionsCombats.containsKey(positionCombat), "Le combat doit être supprimé de la carte.");
    }

    @Test
    void testUtilisationCapaciteSpeciale() {
        Heros h = new Heros("Benjamin", TypeHeros.SOIGNEUR);
        assertFalse(h.getAUtiliseSaCapaciteSpeciale(), "La capacité spéciale doit être disponible au début.");
        h.setAUtiliseSaCapaciteSpeciale(true);
        assertTrue(h.getAUtiliseSaCapaciteSpeciale(), "Capacité utilisée avec succès.");
    }


    @Test
    void testDeplacementHeroSurCarte() {
        Heros hero = new Heros("Lancelot", TypeHeros.BARBARE);
        Carte carte = new Carte("test", 5);
        carte.placerHero(hero);
        int positionInitiale = hero.getPosition();
        hero.avance(carte);
        assertEquals(positionInitiale + 1, hero.getPosition(), "Le héros doit avancer d'une case.");
        assertTrue(hero.getPosition() < carte.getLongueur(), "Le héros ne doit pas dépasser la longueur de la carte.");
    }
}
