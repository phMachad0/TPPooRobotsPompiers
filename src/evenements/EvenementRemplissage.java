package evenements;

import classes.Robot;

/**
 * Cette classe représente un événement de remplissage dans la simulation des robots pompiers.
 * Un événement de remplissage est déclenché lorsque le robot pompeur arrive à la (ou à côte) case d'eau
 * et doit remplir son réservoir d'eau.
 */
public class EvenementRemplissage extends Evenement {
    private Robot robot;

    /**
     * Constructeur de la classe EvenementRemplissage.
     * @param date La date de l'événement.
     * @param robot Le robot à remplir.
     */
    public EvenementRemplissage(long date, Robot robot) {
        super(date);
        System.out.println("EvenementRemplissage créé à " + date); 
        this.robot = robot;
    }

    /**
     * Exécute l'événement de remplissage en appelant la méthode remplirReservoir()
     * du robot et en mettant le statut du robot à non occupé.
     */
    @Override
    public void execute() {
        this.robot.remplirReservoir();
        this.robot.setOccupe(false);
    }
}
