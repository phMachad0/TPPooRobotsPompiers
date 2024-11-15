package classes;
import java.util.List;
import java.util.ArrayList;


public class PlusCourtChemin {
    

    public static List <Case> Astar(Robot robot, Case arrivee, Carte carte) {
        Case current = robot.getPosition();
        Case last = new Case(10000, 10000, NatureTerrain.TERRAIN_LIBRE);
        List <Case> path = new ArrayList<Case>();
        
        while (current != arrivee) {
            List<Double> costs = new ArrayList<Double>();
            Direction[] directions = {Direction.EST, Direction.OUEST, Direction.NORD, Direction.SUD};
            
            for (Direction dir : directions) {
                if (!carte.caseIsthme(carte.getVoisin(current, dir), robot) && carte.voisinExiste(current, dir)) {
                    costs.add(fctf(current, arrivee, dir, robot));
                } else {
                    costs.add(Double.POSITIVE_INFINITY);
                }
            }

            int minIndex = costs.indexOf(costs.stream().min(Double::compare).orElse(Double.POSITIVE_INFINITY));
            Direction bestDirection = directions[minIndex];
            if (!carte.caseIsthme(current, robot) && carte.getVoisin(current, bestDirection) == last){
                costs.set(minIndex, Double.POSITIVE_INFINITY);
                minIndex = costs.indexOf(costs.stream().min(Double::compare).orElse(Double.POSITIVE_INFINITY));
                bestDirection = directions[minIndex];
            }
            last = current;
            current = carte.getVoisin(current, bestDirection);
            path.add(current);
        }
        return path;
    }

    public static double fctf(Case a, Case b, Direction dir, Robot robot){
        Robot robotTemp = robot;
        robotTemp.setPosition(a);
        double g = robot.tempsDeplacement(dir);
        robotTemp.deplacerVers(dir);
        Case c = robotTemp.getPosition();

        double h = Math.sqrt(Math.pow(c.getLigne() - b.getLigne(), 2) + Math.pow(c.getColonne() - b.getColonne(), 2));

        return g+h;
            
    };

}