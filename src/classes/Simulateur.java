package classes;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Simulateur implements Simulable {
    private DonneesSimulation donnees;

    long dateSimulation = 0;
    List<Evenement> evenements;
    /** L'interface graphique associée */
    private GUISimulator gui;		

    /**
     * Crée un Invader et le dessine.
     * @param gui l'interface graphique associée, dans laquelle se fera le
     * dessin et qui enverra les messages via les méthodes héritées de
     * Simulable.
     * @param color la couleur de l'simulateur
     */
    public Simulateur(GUISimulator gui, DonneesSimulation donnees) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.donnees = donnees;
        this.evenements = new ArrayList<Evenement>();
        draw();
    }

    public void incrementeDate() {
        dateSimulation++;
        for (Evenement e : evenements) {
            if (e.getDate() == dateSimulation) {
                e.execute();
            }
        }
    }

    public void ajouteEvenement(Evenement e) {
        evenements.add(e);
    }

    public boolean simulationTerminee() {
        return evenements.isEmpty();
    }

    @Override
    public void next() {
        draw();
    }

    @Override
    public void restart() {
        draw();
    }


    /**
     * Dessine l'simulateur.
     */
    private void draw() {
        gui.reset();	// clear the window

        for (Case c : donnees.getCarte().getListCases()) {
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

        for (Incendie incendie : donnees.getIncendies()) {
            Case c = incendie.getPosition();
            int x = 25 + c.getColonne() * 50;
            int y = 25 + c.getLigne() * 50;
            gui.addGraphicalElement(new ImageElement(x, y, "imgs/fire.png", 50, 50, null));
            gui.addGraphicalElement(new Text(x+25, y+25, Color.BLACK, Integer.toString(incendie.getLitreNecessaires())));
        }

        for (Robot robot : donnees.getRobots()) {
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
                case "classes.Chenilles":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/chenilles.png", 50, 50, null));
                    break;
                case "classes.Pattes":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/pattes.png", 50, 50, null));
                    break;
                default:
                    break;
            }
            System.out.println("Robot en " + c + " de type " + robot.getClass().getName());
        }
    }
}
