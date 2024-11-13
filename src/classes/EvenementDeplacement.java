package classes;

public class EvenementDeplacement extends Evenement {
    private Robot robot;
    private Direction direction;

    public EvenementDeplacement(long date, Robot robot, Direction direction) {
        super(date);
        this.robot = robot;
        this.direction = direction;
    }

    @Override
    public void execute() {
        this.robot.deplacerVers(this.direction);
    }
}
