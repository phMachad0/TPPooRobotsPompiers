
import io.LecteurDonnees;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import classes.DonneesSimulation;

public class TestLecteurDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation ds = LecteurDonnees.lire(args[0]);
            System.out.println(ds);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}

