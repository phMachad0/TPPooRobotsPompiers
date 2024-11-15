package classes;

public class Pattes extends Robot {
    public Pattes(Case initialPosition, Carte carte) {
        super(initialPosition, Integer.MAX_VALUE, Integer.MAX_VALUE, 30, 0, 1, 10, carte);
    }

    @Override
    public void remplirReservoir() {
        // Les robots à pattes n'ont pas de réservoir
    }

    public int effectuerInterventionUnitaire(Incendie incendie) {
        int volumeNecessaire = incendie.getLitreNecessaires();
        int volumeDeversee = Math.min(this.getVolumeInterventionUnitaire(), volumeNecessaire);
        incendie.setLitreNecessaires(incendie.getLitreNecessaires() - volumeDeversee);
        return volumeDeversee;
    }

    public void deplacerVers(Direction direction) {
        Case newPosition = this.carte.getVoisin(this.getPosition(), direction);
        if (newPosition != null) {
            this.setPosition(newPosition);
        }
    }

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
}
