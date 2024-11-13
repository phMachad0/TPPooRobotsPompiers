import java.util.zip.DataFormatException;

import classes.*;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import io.LecteurDonnees;

import java.awt.Color;
import java.io.FileNotFoundException;

public class TestSimulateur {
    
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation ds = LecteurDonnees.lire(args[0]);
            Simulateur simulateur = new Simulateur(gui, ds);
            System.out.println(ds);

            simulateur.ajouteEvenement(new EvenementDeplacement(1, ds.getRobots().get(1), Direction.NORD));
            simulateur.ajouteEvenement(new EvenementDeplacement(2, ds.getRobots().get(1), Direction.NORD));
            simulateur.ajouteEvenement(new EvenementDeplacement(3, ds.getRobots().get(1), Direction.NORD));
            simulateur.ajouteEvenement(new EvenementDeplacement(4, ds.getRobots().get(1), Direction.NORD));
            simulateur.ajouteEvenement(new EvenementDeplacement(5, ds.getRobots().get(1), Direction.NORD));
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }


    }
}
