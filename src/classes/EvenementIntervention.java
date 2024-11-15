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
        int volumeDeversee = this.robot.effectuerInterventionUnitaire(this.incendie);
        System.out.println("Le robot " + this.robot.getClass().getName() + " a deverse " + volumeDeversee
                + " litres sur l'incendie " + this.incendie);

        if (this.robot.resevoirVide()) {
            System.out.println("Le robot " + this.robot.getClass().getName() + " est vide");
            this.robot.setOccupe(false);
        } else {
            if (this.incendie.getLitreNecessaires() == 0) {
                this.robot.setOccupe(false);
            } else {
                this.simulateur.ajouteEvenement(new EvenementIntervention(
                        this.getDate() + this.robot.getTempsInterventionIncendie(this.incendie), this.robot,
                        this.incendie, this.simulateur));
            }
        }
    }
}
