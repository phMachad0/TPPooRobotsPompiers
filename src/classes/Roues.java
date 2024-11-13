package classes;

public class Roues extends Robot {
    public Roues(Case initialPosition, Carte carte) {
        super(initialPosition, 0, 5000, 100, 10, 5, 100, carte);
    }

    public Roues(Case initialPosition, double vitesseLueDansLeFicher, Carte carte) {
        super(initialPosition, 0, 5000, vitesseLueDansLeFicher, 10, 5, 100, carte);
    }

    @Override
    public void remplirReservoir() {
        if (this.getPosition().getNature() == NatureTerrain.EAU)
            this.actuelVolumeEau = this.maxVolumeEau;
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

        if (natureVoisin == NatureTerrain.ROCHE || natureVoisin == NatureTerrain.EAU
                || natureVoisin == NatureTerrain.FORET) {
            return Double.POSITIVE_INFINITY;
        }

        return Math.round(3600 * (this.carte.getTailleCases() / 1000) / this.getVitesseDefaut());
    }
}
