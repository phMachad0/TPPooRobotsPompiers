package classes;

public class EvenementRemplissage extends Evenement {
    private Robot robot;

    public EvenementRemplissage(long date, Robot robot) {
        super(date);
        this.robot = robot;
    }

    @Override
    public void execute() {
        this.robot.remplirReservoir();
    }
    
}
