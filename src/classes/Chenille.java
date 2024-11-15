package classes;

import enums.Direction;
import enums.NatureTerrain;

/**
 * La classe Chenille représente un type de robot utilisé pour combattre les incendies.
 */
public class Chenille extends Robot {
    /**
     * Constructeur de la classe Chenille.
     * 
     * @param initialPosition La position initiale du robot à chenilles sur la carte.
     * @param carte La carte sur laquelle le robot à chenilles se déplace.
     */
    public Chenille(Case initialPosition, Carte carte) {
        super(initialPosition, 0, 2000, 60, 5*60, 8, 100, carte);
    }

    /**
     * Constructeur de la classe Chenille.
     * 
     * @param initialPosition La position initiale du robot à chenilles sur la carte.
     * @param vitesseLueDansLeFicher La vitesse lue dans le fichier pour le robot à chenilles.
     * @param carte La carte sur laquelle le robot à chenilles se déplace.
     */
    public Chenille(Case initialPosition, double vitesseLueDansLeFicher, Carte carte) {
        super(initialPosition, 0, 2000, Math.min(vitesseLueDansLeFicher, 80), 5*60, 8, 100, carte);
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
        NatureTerrain natureCurrentPosition = src.getNature();

        if (natureVoisin == NatureTerrain.EAU || natureVoisin == NatureTerrain.ROCHE) {
            return Long.MAX_VALUE;
        }

        if (natureCurrentPosition == NatureTerrain.FORET) {
            return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / (this.getVitesseDefaut()*0.5));
        }
        
        return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / this.getVitesseDefaut());
    }

    @Override
    public String toString() {
        String res = super.toString();
        return "Chenille " + res;
    }
}
