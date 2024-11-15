package classes;

import java.util.List;

/**
 * La classe DonneesSimulation représente les données de simulation pour les robots pompiers.
 * Elle contient une carte, une liste d'incendies et une liste de robots.
 */
public class DonneesSimulation {
    private Carte carte;
    private List<Incendie> incendies;
    private List<Robot> robots;

    /**
     * Constructeur de la classe DonneesSimulation.
     * @param carte La carte de la simulation.
     * @param incendies La liste des incendies de la simulation.
     * @param robots La liste des robots de la simulation.
     */
    public DonneesSimulation(Carte carte, List<Incendie> incendies, List<Robot> robots) {
        this.carte = carte;
        this.incendies = incendies;
        this.robots = robots;
    }
    
    /**
     * Retourne la carte de la simulation.
     * @return La carte de la simulation.
     */
    public Carte getCarte() {
        return this.carte;
    }

    /**
     * Modifie la carte de la simulation.
     * @param carte La nouvelle carte de la simulation.
     */
    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    /**
     * Retourne la liste des robots de la simulation.
     * @return La liste des robots de la simulation.
     */
    public List<Robot> getRobots() {
        return robots;
    }

    /**
     * Modifie la liste des robots de la simulation.
     * @param robots La nouvelle liste des robots de la simulation.
     */
    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }

    /**
     * Retourne la liste des incendies de la simulation.
     * @return La liste des incendies de la simulation.
     */
    public List<Incendie> getIncendies() {
        return incendies;
    }

    /**
     * Modifie la liste des incendies de la simulation.
     * @param incendies La nouvelle liste des incendies de la simulation.
     */
    public void setIncendies(List<Incendie> incendies) {
        this.incendies = incendies;
    }

    @Override
    public String toString() {
        return "DonneesSimulation [carte=" + carte + ", incendies=" + incendies + ", robots=" + robots + "]";
    }
}
