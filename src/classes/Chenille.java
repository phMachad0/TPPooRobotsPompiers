package classes;

public class Chenille extends Robot {
    public Chenille(Case initialPosition, Carte carte) {
        super(initialPosition, 0, 2000, 60, 5*60, 8, 100, carte);
    }

    public Chenille(Case initialPosition, double vitesseLueDansLeFicher, Carte carte) {
        super(initialPosition, 0, 2000, Math.min(vitesseLueDansLeFicher, 80), 5*60, 8, 100, carte);
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
        NatureTerrain natureCurrentPosition = src.getNature();

        if (natureVoisin == NatureTerrain.EAU || natureVoisin == NatureTerrain.ROCHE) {
            return Long.MAX_VALUE;
        }

        if (natureCurrentPosition == NatureTerrain.FORET) {
            return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / this.getVitesseDefaut()*0.5);
        }
        
        return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / this.getVitesseDefaut());
    }
}
