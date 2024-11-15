package evenements;

import classes.Incendie;
import classes.Robot;
import classes.Simulateur;

/**
 * Cette classe représente un événement d'intervention dans la simulation des robots pompiers.
 * Un événement d'intervention est déclenché lorsque le robot pompeur arrive sur les lieux d'un incendie
 * et effectue une intervention pour éteindre l'incendie.
 */
public class EvenementIntervention extends Evenement {
    private Robot robot;
    private Incendie incendie;
    private Simulateur simulateur;

    /**
     * Constructeur de la classe EvenementIntervention.
     * @param date la date à laquelle l'événement d'intervention doit être exécuté
     * @param robot le robot qui effectue l'intervention
     * @param incendie l'incendie sur lequel le robot intervient
     * @param simulateur le simulateur de la simulation
     */
    public EvenementIntervention(long date, Robot robot, Incendie incendie, Simulateur simulateur) {
        super(date);
        this.robot = robot;
        this.incendie = incendie;
        this.simulateur = simulateur;
    }

    /**
     * Exécute l'événement d'intervention.
     * Le robot effectue une intervention sur l'incendie en déversant de l'eau sur celui-ci.
     * Si le réservoir du robot est vide, le robot devient disponible.
     * Si l'incendie est éteint, le robot devient disponible.
     * Sinon, un nouvel événement d'intervention est ajouté au simulateur pour une intervention future.
     */
    @Override
    public void execute() {
        this.robot.effectuerInterventionUnitaire(this.incendie);
        if (this.robot.reservoirVide()) {
            this.robot.setOccupe(false);
        } else {
            if (this.incendie.getLitresNecessaires() == 0) {
                this.robot.setOccupe(false);
            } else {
                this.simulateur.ajouteEvenement(new EvenementIntervention(
                        this.getDate() + this.robot.getTempsInterventionIncendie(this.incendie), this.robot,
                        this.incendie, this.simulateur));
            }
        }
    }
}
