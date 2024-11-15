package classes;

public class Roues extends Robot {
    public Roues(Case initialPosition, Carte carte) {
        super(initialPosition, 5000, 5000, 100, 10*60, 5, 100, carte);
    }

    public Roues(Case initialPosition, double vitesseLueDansLeFicher, Carte carte) {
        super(initialPosition, 5000, 5000, vitesseLueDansLeFicher, 10*60, 5, 100, carte);
    }

    @Override
    public void remplirReservoir() {
        this.actuelVolumeEau = this.maxVolumeEau;
    }

    public int effectuerInterventionUnitaire(Incendie incendie) {
        int volumeNecessaire = incendie.getLitreNecessaires();
        int volumeDeversee = Math.min(Math.min(this.getVolumeInterventionUnitaire(), this.getActuelVolumeEau()), volumeNecessaire);
        this.deverserEau(volumeDeversee);
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

        if (natureVoisin == NatureTerrain.ROCHE || natureVoisin == NatureTerrain.EAU
                || natureVoisin == NatureTerrain.FORET) {
            return Long.MAX_VALUE;
        }

        return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / this.getVitesseDefaut());
    }
}
