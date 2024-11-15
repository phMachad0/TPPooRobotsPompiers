package classes;
public class Drone extends Robot {
    public Drone(Case initialPosition, Carte carte) {
        super(initialPosition, 10000, 10000, 100, 30*60, 30, 10000, carte);
    }

    public Drone(Case initialPosition, double vitesseLueDansLeFicher, Carte carte) {
        super(initialPosition, 10000, 10000, Math.min(vitesseLueDansLeFicher, 150), 30*60, 30, 10000, carte);
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
        return Math.round(3600 * ((double)this.carte.getTailleCases() / 1000) / (long)this.getVitesseDefaut());
    }
}
