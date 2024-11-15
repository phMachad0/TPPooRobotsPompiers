package classes;

public class EvenementRemplissage extends Evenement {
    private Robot robot;

    public EvenementRemplissage(long date, Robot robot) {
        super(date);
        System.out.println("EvenementRemplissage created at " + date); 
        this.robot = robot;
    }

    @Override
    public void execute() {
        this.robot.remplirReservoir();
        this.robot.setOccupe(false);
    }
}
