package classes;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import io.LecteurDonnees;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

public class Simulateur implements Simulable {
    private DonneesSimulation donnes;
    private String carte;
    private ChefPompier chefPompier;

    private long dateSimulation = 0;
    List<Evenement> evenements;
    /** L'interface graphique associée */
    private GUISimulator gui;		

    /**
     * Crée un Invader et le dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param color la couleur de l'simulateur
     * @throws DataFormatException 
     * @throws FileNotFoundException 
     */
    public Simulateur(GUISimulator gui, String carte) throws FileNotFoundException, DataFormatException {
        this.gui = gui;
        gui.setSimulable(this);
        this.carte = carte;
        this.donnes = LecteurDonnees.lire(carte);
        this.evenements = new ArrayList<Evenement>();
        this.chefPompier = new ChefPompier(this, this.getDonnes());
        chefPompier.planifier();
        draw();
    }

    public DonneesSimulation getDonnes() {
        return donnes;
    }

    public void incrementeDate() {
        dateSimulation++;
        List<Evenement> evenementsAExecuter = new ArrayList<Evenement>();
        Iterator<Evenement> iterator = evenements.iterator();
        while (iterator.hasNext()) {
            Evenement e = iterator.next();
            if (e.getDate() == dateSimulation) {
                evenementsAExecuter.add(e);
                iterator.remove();
            }
        }
        for (Evenement e : evenementsAExecuter) {
            e.execute();
        }

    }

    public void ajouteEvenement(Evenement e) {
        evenements.add(e);
    }

    public boolean simulationTerminee() {
        return evenements.isEmpty();
    }

    public long getDate() {
        return dateSimulation;
    }

    @Override
    public void next() {
        incrementeDate();
        System.out.println("Date: " + dateSimulation);
        draw();
        chefPompier.planifier();
    }

    @Override
    public void restart() {
        try {
            donnes = LecteurDonnees.lire(this.carte);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        draw();
    }


    /**
     * Dessine l'simulateur.
     */
    private void draw() {
        gui.reset();	// clear the window

        for (Case c : donnes.getCarte().getListCases()) {
            int x = 50 + c.getColonne() * 50;
            int y = 50 + c.getLigne() * 50;

            switch (c.getNature()) {
                case EAU:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.BLUE, 50));
                    break;
                case FORET:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.GREEN, 50));
                    break;
                case ROCHE:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.GRAY, 50));
                    break;
                case TERRAIN_LIBRE:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.decode("#a57e5a"), 50));
                    break;
                case HABITAT:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.YELLOW, 50));
                    break;
                default:
                    break;
            }  
        }

        for (Incendie incendie : donnes.getIncendies()) {
            Case c = incendie.getPosition();
            int x = 25 + c.getColonne() * 50;
            int y = 25 + c.getLigne() * 50;
            gui.addGraphicalElement(new ImageElement(x, y, "imgs/fire.png", 50, 50, null));
            gui.addGraphicalElement(new Text(x+25, y+25, Color.BLACK, Integer.toString(incendie.getLitreNecessaires())));
        }

        for (Robot robot : donnes.getRobots()) {
            Case c = robot.getPosition();
            int x = 25 + c.getColonne() * 50;
            int y = 25 + c.getLigne() * 50;
            
            switch (robot.getClass().getName()) {
                case "classes.Drone":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/drone.png", 50, 50, null));
                    break;
                case "classes.Roues":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/roues.png", 50, 50, null));
                    break;
                case "classes.Chenille":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/chenille.png", 50, 50, null));
                    break;
                case "classes.Pattes":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/pattes.png", 50, 50, null));
                    break;
                default:
                    break;
            }
            gui.addGraphicalElement(new Text(x+25, y+25, Color.RED, Integer.toString(robot.getActuelVolumeEau())));
        }
    }
}
