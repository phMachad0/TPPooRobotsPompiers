import java.util.zip.DataFormatException;

import classes.*;
import gui.GUISimulator;
import io.LecteurDonnees;

import java.awt.Color;
import java.io.FileNotFoundException;

public class TestSimulateur {
    
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(2000, 2000, Color.BLACK);

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            String carte = args[0];
            Simulateur simulateur = new Simulateur(gui, carte);

            // simulateur.ajouteEvenement(new EvenementDeplacement(1, ds.getRobots().get(0), Direction.NORD));
            // simulateur.ajouteEvenement(new EvenementDeplacement(2, ds.getRobots().get(0), Direction.NORD));
            // simulateur.ajouteEvenement(new EvenementDeplacement(3, ds.getRobots().get(0), Direction.NORD));
            // simulateur.ajouteEvenement(new EvenementDeplacement(4, ds.getRobots().get(0), Direction.NORD));
            // simulateur.ajouteEvenement(new EvenementDeplacement(5, ds.getRobots().get(0), Direction.NORD));

            // simulateur.ajouteEvenement(new EvenementDeplacement(1, ds.getRobots().get(1), Direction.NORD));
            // simulateur.ajouteEvenement(new EvenementIntervention(2, ds.getRobots().get(1), ds.getIncendies().get(0), simulateur));
            // simulateur.ajouteEvenement(new EvenementDeplacement(4, ds.getRobots().get(1), Direction.OUEST));
            // simulateur.ajouteEvenement(new EvenementDeplacement(5, ds.getRobots().get(1), Direction.OUEST));
            // simulateur.ajouteEvenement(new EvenementRemplissage(6, ds.getRobots().get(1)));
            // chefPompier.planifierEvenementsDeplacement(simulateur.getDate() ,simulateur.getDonnes().getRobots().get(1), simulateur.getDonnes().getIncendies().get(1).getPosition());
            // chefPompier.planifierEvenementsIntervention(simulateur.getDate() ,simulateur.getDonnes().getRobots().get(1), simulateur.getDonnes().getIncendies().get(1));
            // System.out.println(PlusCourtChemin.Astar(simulateur.getDonnes().getRobots().get(1), simulateur.getDonnes().getIncendies().get(1).getPosition(), simulateur.getDonnes().getCarte()));
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
