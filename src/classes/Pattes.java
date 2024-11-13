package classes;

public class Pattes extends Robot {
    public Pattes(Case initialPosition, Carte carte) {
        super(initialPosition, 0, 0, 30, 0, 1, 10, carte);
    }

    @Override
    public void remplirReservoir() {
        // Les robots à pattes n'ont pas de réservoir
    }

    public int interventionUnitaire(Incendie incendie) {
        int volumeNecessaire = incendie.getLitreNecessaires();
        int volumeDeversee = Math.min(Math.min(this.getVolumeInterventionUnitaire(), this.getActuelVolumeEau()), volumeNecessaire);
        this.deverserEau(volumeDeversee);
        return volumeDeversee;
    }

    public void deplacerVers(Direction direction) {
        Case newPosition = this.carte.getVoisin(this.getPosition(), direction);
        if (newPosition != null) {
            this.setPosition(newPosition);
        }
    }

    public double tempsDeplacement(Direction direction) {
        NatureTerrain natureVoisin = this.getCarte().getVoisin(this.getPosition(), direction).getNature();
        NatureTerrain natureCurrentPosition = this.getPosition().getNature();

        if (natureVoisin == NatureTerrain.EAU) {
            return Double.POSITIVE_INFINITY;
        }

        if (natureCurrentPosition == NatureTerrain.ROCHE) {
            return Math.round(3600 * (this.carte.getTailleCases() / 1000) / 10);
        }
        
        return Math.round(3600 * (this.carte.getTailleCases() / 1000) / this.getVitesseDefaut());
    }
}
