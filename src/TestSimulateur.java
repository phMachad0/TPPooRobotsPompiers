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
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
