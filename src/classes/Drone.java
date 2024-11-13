package classes;
public class Drone extends Robot {
    public Drone(Case initialPosition, Carte carte) {
        super(initialPosition, 0, 10000, 100, 30, 30, 1000, carte);
    }

    public Drone(Case initialPosition, double vitesseLueDansLeFicher, Carte carte) {
        super(initialPosition, 0, 10000, Math.min(vitesseLueDansLeFicher, 150), 30, 30, 1000, carte);
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
        return Math.round(3600 * (this.carte.getTailleCases() / 1000) / this.getVitesseDefaut());
    }
}
