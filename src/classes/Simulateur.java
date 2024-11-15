package classes;

import gui.*;
import io.LecteurDonnees;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.zip.DataFormatException;
import evenements.Evenement;

public class Simulateur implements Simulable {
    private DonneesSimulation donnes;
    private String carte;
    private ChefPompier chefPompier;

    private long dateSimulation = 0;
    PriorityQueue<Evenement> evenements;
    /** L'interface graphique associée */
    public GUISimulator gui;		

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
        this.evenements = new PriorityQueue<>(new Comparator<Evenement>(){
            @Override
            public int compare(Evenement j1, Evenement j2) {
                return Long.compare(j1.getDate(), j2.getDate());
            }
        });
        this.chefPompier = new ChefPompier(this, this.getDonnes());
        chefPompier.planifier();
        draw();
    }

    public DonneesSimulation getDonnes() {
        return donnes;
    }

    public void incrementeDate() {
        dateSimulation++;
        while (!evenements.isEmpty() && evenements.peek().getDate() == dateSimulation) {
            Evenement e = evenements.poll();
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
            int x = 37 + c.getColonne() * 37;
            int y = 37 + c.getLigne() * 37;

            switch (c.getNature()) {
                case EAU:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.BLUE, 37));
                    break;
                case FORET:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.GREEN, 37));
                    break;
                case ROCHE:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.GRAY, 37));
                    break;
                case TERRAIN_LIBRE:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.decode("#a57e5a"), 37));
                    break;
                case HABITAT:
                    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.YELLOW, 37));
                    break;
                default:
                    break;
            }  
        }

        for (Incendie incendie : donnes.getIncendies()) {
            Case c = incendie.getPosition();
            int x = 19 + c.getColonne() * 37;
            int y = 19 + c.getLigne() * 37;
            gui.addGraphicalElement(new ImageElement(x, y, "imgs/fire.png", 37, 37, null));
            gui.addGraphicalElement(new Text(x+19, y+19, Color.BLACK, Integer.toString(incendie.getLitresNecessaires())));
        }

        for (Robot robot : donnes.getRobots()) {
            Case c = robot.getPosition();
            int x = 19 + c.getColonne() * 37;
            int y = 19 + c.getLigne() * 37;
            
            switch (robot.getClass().getName()) {
                case "classes.Drone":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/drone.png", 37, 37, null));
                    gui.addGraphicalElement(new Text(x+19, y+19, Color.RED, Integer.toString(robot.getActuelVolumeEau())));
                    break;
                case "classes.Roues":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/roues.png", 37, 37, null));
                    gui.addGraphicalElement(new Text(x+19, y+19, Color.RED, Integer.toString(robot.getActuelVolumeEau())));
                    break;
                case "classes.Chenille":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/chenille.png", 37, 37, null));
                    gui.addGraphicalElement(new Text(x+19, y+19, Color.RED, Integer.toString(robot.getActuelVolumeEau())));
                    break;
                case "classes.Pattes":
                    gui.addGraphicalElement(new ImageElement(x, y, "imgs/pattes.png", 37, 37, null));
                    gui.addGraphicalElement(new Text(x+19, y+19, Color.RED, "Inf"));
                    break;
                default:
                    break;
            }
        }
    }
}
