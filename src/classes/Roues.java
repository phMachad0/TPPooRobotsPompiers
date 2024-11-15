package classes;

import enums.Direction;
import enums.NatureTerrain;

/**
 * La classe Roues représente un type de robot utilisé pour combattre les incendies.
 */
public class Roues extends Robot {
    /**
     * Constructeur de la classe Roues.
     * 
     * @param initialPosition La position initiale du robot.
     * @param carte La carte sur laquelle le robot se déplace.
     */
    public Roues(Case initialPosition, Carte carte) {
        super(initialPosition, 5000, 5000, 100, 10*60, 5, 100, carte);
    }

    /**
     * Constructeur de la classe Roues.
     * 
     * @param initialPosition La position initiale du robot.
     * @param vitesseLueDansLeFicher La vitesse du robot lue depuis un fichier.
     * @param carte La carte sur laquelle le robot se déplace.
     */
    public Roues(Case initialPosition, double vitesseLueDansLeFicher, Carte carte) {
        super(initialPosition, 5000, 5000, vitesseLueDansLeFicher, 10*60, 5, 100, carte);
    }

    @Override
    public void effectuerInterventionUnitaire(Incendie incendie) {
        int volumeDeversee = this.getVolumeDeversee(incendie);
        this.deverserEau(volumeDeversee);
        incendie.setLitresNecessaires(incendie.getLitresNecessaires() - volumeDeversee);
    }

    @Override
    public void deplacerVers(Direction direction) {
        Case newPosition = this.carte.getVoisin(this.getPosition(), direction);
        if (newPosition != null) {
            this.setPosition(newPosition);
        }
    }

    @Override
    public long tempsDeplacement(Case src, Direction direction) {
        NatureTerrain natureVoisin = this.getCarte().getVoisin(src, direction).getNature();

        if (natureVoisin == NatureTerrain.ROCHE || natureVoisin == NatureTerrain.EAU
                || natureVoisin == NatureTerrain.FORET) {
            return Long.MAX_VALUE;
        }

        return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / this.getVitesseDefaut());
    }

    @Override
    public String toString() {
        String res = super.toString();
        return "Roues " + res;
    }
}
