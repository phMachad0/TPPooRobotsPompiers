package classes;
import java.util.List;
import java.util.ArrayList;


public class PlusCourtChemin {
    

    public static Tuple <List <Case>, Long> Astar(Robot robot, Case arrivee, Carte carte) {
        Case current = robot.getPosition();
        Case last = new Case(10000, 10000, NatureTerrain.TERRAIN_LIBRE);
        List <Case> path = new ArrayList<Case>();
        List <Direction> dirs = new ArrayList<Direction>();
        path.add(current);
        Long coutchemin = 0L;
        
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
            dirs.add(bestDirection);
            path.add(current);
        }
        for (int i = 0; i < path.size(); i++){ {
            Case c = path.get(i);
            if(c!= arrivee){}
                Robot robotTemp = robot;
                robotTemp.setPosition(c);
                coutchemin += robotTemp.tempsDeplacement(dirs.get(i));
            }
        }
        
        Tuple<List<Case>, Long> tuple = new Tuple<>(path, coutchemin);
        return tuple;
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