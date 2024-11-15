package classes;

import enums.Direction;

/**
 * La classe Drone représente un type de robot utilisé pour combattre les incendies.
 */
public class Drone extends Robot {
    /**
     * Constructeur de la classe Drone.
     * 
     * @param initialPosition La position initiale du drone sur la carte.
     * @param carte La carte sur laquelle le drone se déplace.
     */
    public Drone(Case initialPosition, Carte carte) {
        super(initialPosition, 10000, 10000, 100, 30*60, 30, 10000, carte);
    }

    /**
     * Constructeur de la classe Drone.
     * 
     * @param initialPosition La position initiale du drone sur la carte.
     * @param vitesseLueDansLeFicher La vitesse lue dans le fichier pour le drone.
     * @param carte La carte sur laquelle le drone se déplace.
     */
    public Drone(Case initialPosition, double vitesseLueDansLeFicher, Carte carte) {
        super(initialPosition, 10000, 10000, Math.min(vitesseLueDansLeFicher, 150), 30*60, 30, 10000, carte);
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
        return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / (long)this.getVitesseDefaut());
    }

    @Override
    public String toString() {
        String res = super.toString();
        return "Drone " + res;
    }
}
