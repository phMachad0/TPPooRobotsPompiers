package classes;

import enums.Direction;
import enums.NatureTerrain;

/**
 * La classe Pattes représente un type de robot utilisé pour combattre les incendies.
 */
public class Pattes extends Robot {
    /**
     * Constructeur de la classe Pattes.
     * 
     * @param initialPosition La position initiale du robot à pattes sur la carte.
     * @param carte La carte sur laquelle le robot à pattes se déplace.
     */
    public Pattes(Case initialPosition, Carte carte) {
        super(initialPosition, Integer.MAX_VALUE, Integer.MAX_VALUE, 30, 0, 1, 10, carte);
    }

    @Override
    public void effectuerInterventionUnitaire(Incendie incendie) {
        int volumeDeversee = this.getVolumeDeversee(incendie);
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

        if (natureVoisin == NatureTerrain.EAU) {
            return Long.MAX_VALUE;
        }

        if (natureCurrentPosition == NatureTerrain.ROCHE) {
            return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / 10);
        }
        
        return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / this.getVitesseDefaut());
    }

    @Override
    public String toString() {
        String res = super.toString();
        return "Pattes " + res;
    }
}
