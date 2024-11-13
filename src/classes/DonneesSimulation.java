package classes;

import java.util.List;

public class DonneesSimulation {
    private Carte carte;
    private List<Incendie> incendies;
    private List<Robot> robots;

    
    public DonneesSimulation(Carte carte, List<Incendie> incendies, List<Robot> robots) {
        this.carte = carte;
        this.incendies = incendies;
        this.robots = robots;
    }
    
    public Carte getCarte() {
        return this.carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }

    public List<Incendie> getIncendies() {
        return incendies;
    }

    public void setIncendies(List<Incendie> incendies) {
        this.incendies = incendies;
    }

    @Override
    public String toString() {
        return "DonneesSimulation [carte=" + carte + ", incendies=" + incendies + ", robots=" + robots + "]";
    }
}
