package classes;

public class EvenementIntervention extends Evenement {
    private Robot robot;
    private Incendie incendie;
    private Simulateur simulateur;

    public EvenementIntervention(long date, Robot robot, Incendie incendie, Simulateur simulateur) {
        super(date);
        this.robot = robot;
        this.incendie = incendie;
        this.simulateur = simulateur;
    }

    @Override
    public void execute() {
        int volumeDeversee = this.robot.interventionUnitaire(this.incendie);
        this.incendie.setLitreNecessaires(volumeDeversee);

        if (this.robot.resevoirVide()) {
            // TODO
            // On ajoute un nouvel evenement deplacement (EvenementDeplacement) vers la case eau la plus proche
            // On ajoute un nouvel evenement remplissage (EvenementRemplissage) a la fin de l'evenement deplacement
        } else {
            if (this.incendie.getLitreNecessaires() == 0) {
                this.robot.setOccupe(false);
            } else {
                this.simulateur.ajouteEvenement(new EvenementIntervention(this.getDate() + this.robot.getTempsInterventionUnitaire(), this.robot, this.incendie, this.simulateur));
            }
        }
    }
}
