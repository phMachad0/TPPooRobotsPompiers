package evenements;

import classes.Robot;
import enums.Direction;

/**
 * Cette classe représente un événement de déplacement elementar d'un robot dans une direction donnée.
 */
public class EvenementDeplacement extends Evenement {
    private Robot robot;
    private Direction direction;

    /**
     * Constructeur de la classe EvenementDeplacement.
     * @param date la date de l'événement
     * @param robot le robot concerné par l'événement
     * @param direction la direction dans laquelle le robot doit se déplacer
     */
    public EvenementDeplacement(long date, Robot robot, Direction direction) {
        super(date);
        this.robot = robot;
        this.direction = direction;
    }

    /**
     * Exécute l'événement de déplacement en appelant la méthode deplacerVers() du robot.
     */
    @Override
    public void execute() {
        this.robot.deplacerVers(this.direction);
    }
}
