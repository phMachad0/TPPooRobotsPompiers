package classes;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import dijkstra.*;
import utils.Tuple;

  
public class ChefPompier {
    private Simulateur simulateur;
    private DonneesSimulation donnees;
    private Map<Incendie, Robot> incendiesTraites;
    private int incendiesActifs = 0;

    public ChefPompier(Simulateur simulateur, DonneesSimulation donnees) {
        this.simulateur = simulateur;
        this.donnees = donnees;
        this.incendiesTraites = new HashMap<>();
        for (Incendie incendie : this.donnees.getIncendies()) {
            this.incendiesTraites.put(incendie, null);
        }
    }

    public void planifier() {
        for (Robot robot : this.donnees.getRobots()) {
            if (robot.resevoirVide() && !robot.isOccupe()) {
                for (Incendie incendie : incendiesTraites.keySet()) {
                    if (incendiesTraites.get(incendie) == robot) {
                        incendiesTraites.put(incendie, null);
                    }
                }
                robot.setOccupe(true);
                chercherEauLaPlusProche(robot);
            }
        }
        
        for (Robot robot : this.donnees.getRobots()) {
            if (!robot.isOccupe()) {
                robot.setOccupe(true);
                chercherIncendieLePlusProche(robot);
            }
        }
        
        this.incendiesActifs = 0;
        for (Incendie incendie : this.donnees.getIncendies()) {
            if (incendie.getLitreNecessaires() != 0) {
                this.incendiesActifs++;
            }
        }
        
    }

    public void chercherIncendieLePlusProche(Robot robot) {
        Tuple<List<Direction>, Long> chemin = new Tuple<>(new ArrayList<>(), Long.MAX_VALUE);
        Incendie incendiePlusProche = null;
        for (Incendie incendie : this.donnees.getIncendies()) {
            if (incendiesTraites.get(incendie) == null || this.incendiesActifs < this.donnees.getRobots().size()) {
                if (incendie.getLitreNecessaires() != 0) {
                    Tuple<List<Direction>, Long> tempChemin = chercherCheminLePlusCourt(robot, incendie.getPosition());
                    if (tempChemin.y < chemin.y) {
                        chemin = tempChemin;
                        incendiePlusProche = incendie;
                    }
                }
            }
        }
        if (incendiePlusProche != null) {
            long tempDate = planifierEvenementsDeplacement(chemin.x, this.simulateur.getDate(), robot);
            planifierEvenementsIntervention(tempDate, robot, incendiePlusProche);
            incendiesTraites.put(incendiePlusProche, robot);
        }
    }

    public void chercherEauLaPlusProche(Robot robot) {
        Tuple<List<Direction>, Long> chemin = new Tuple<>(new ArrayList<>(), Long.MAX_VALUE);
        long tempDate;
        if (robot.getClass().getName().equals("classes.Drone")) {
            for (Case c : this.donnees.getCarte().getListCases()) {
                if (c.getNature() == NatureTerrain.EAU) {
                    System.out.println(c);
                    Tuple<List<Direction>, Long> tempChemin = chercherCheminLePlusCourt(robot, c);
                    System.out.println(tempChemin.x);
                    if (tempChemin.y < chemin.y) {
                        chemin = tempChemin;
                    }
                }
            }

            System.out.println("robot " + robot.getClass().getName() + " va faire le chemin " + chemin.x +" pour aller chercher de l'eau");
            tempDate = planifierEvenementsDeplacement(chemin.x, this.simulateur.getDate(), robot);
        } else {
            for (Case c : this.donnees.getCarte().getListCases()) {
                if (c.getNature() == NatureTerrain.EAU) {
                    for (Direction direction : Direction.values()) {
                        Case voisin = this.donnees.getCarte().getVoisin(c, direction);
                        if (voisin != null && voisin.getNature() != NatureTerrain.EAU) {
                            Tuple<List<Direction>, Long> tempChemin = chercherCheminLePlusCourt(robot, voisin);
                            System.out.println(tempChemin.x);
                            if (tempChemin.y < chemin.y) {
                                chemin = tempChemin;
                            }
                        }
                    }
                }
            }

            System.out.println("robot " + robot.getClass().getName() + " va faire le chemin " + chemin.x +" pour aller chercher de l'eau");
            tempDate = planifierEvenementsDeplacement(chemin.x, this.simulateur.getDate(), robot);
        }
        planifierEvenementsRemplissage(tempDate, robot);
    }

    public void chercherRobotLePlusProche(Incendie incendie) {
        Tuple<List<Direction>, Long> chemin = new Tuple<>(new ArrayList<>(), Long.MAX_VALUE);
        Robot robotPlusProche = null;
        for (Robot robot : this.donnees.getRobots()) {
            if (robot.isOccupe() == false) {
                Tuple<List<Direction>, Long> tempChemin = chercherCheminLePlusCourt(robot, incendie.getPosition());
                if (tempChemin.y < chemin.y) {
                    chemin = tempChemin;
                    robotPlusProche = robot;
                }
            }
        }
        if (robotPlusProche == null) {
            return;
        }
        robotPlusProche.setOccupe(true);
        long tempDate = planifierEvenementsDeplacement(chemin.x, this.simulateur.getDate(), robotPlusProche);
        planifierEvenementsIntervention(tempDate, robotPlusProche, incendie);
    }

    public Tuple<List<Direction>, Long> chercherCheminLePlusCourt(Robot robot, Case dest) {
        Graph g = new Graph();
        Case src = robot.getPosition();
        for (Case c : this.donnees.getCarte().getListCases()) {
            List<Vertex> voisins = new ArrayList<>();
            for (Direction direction : Direction.values()) {
                Case voisin = this.donnees.getCarte().getVoisin(c, direction);
                if (voisin != null) {
                    long temps = robot.tempsDeplacement(c, direction);
                    if (temps != Long.MAX_VALUE) {
                        // System.out.println("temps deplacement de " + c + " vers " + voisin + " = " + temps);
                        voisins.add(new Vertex(this.donnees.getCarte().getCaseId(voisin), temps));
                    }
                }
            }
            g.addVertex(this.donnees.getCarte().getCaseId(c), voisins);
        }


        Tuple<List<Integer>, Long> shortestPathIntegers = g.getShortestPath(this.donnees.getCarte().getCaseId(src), this.donnees.getCarte().getCaseId(dest));
        System.out.println("Chemin le plus court de " + src + " vers " + dest + " = " + shortestPathIntegers.x);
        List<Direction> shortestPath = new ArrayList<>();
        Case c = src;
        for (int i = shortestPathIntegers.x.size() - 1; i >= 0; i--) {
            for (Direction direction : Direction.values()) {
                Case voisin = this.donnees.getCarte().getVoisin(c, direction);
                if (voisin != null && this.donnees.getCarte().getCaseId(voisin) == shortestPathIntegers.x.get(i)) {
                    shortestPath.add(direction);
                    c = voisin;
                    break;
                }
            }
        }
        // System.out.println("Chemin le plus court de " + src + " vers " + dest + " = " + shortestPath);
        return new Tuple<>(shortestPath, shortestPathIntegers.y);
    }

    public Tuple<List<Direction>, Long> chercherCheminLePlusCourtAstar(Robot robot, Case dest) {
        Tuple<List<Case>, Long> cases = PlusCourtChemin.Astar(robot, dest, this.donnees.getCarte());
        List<Direction> shortestPath = new ArrayList<>();
        Case c = robot.getPosition();
        for (Case case1 : cases.x) {
            for (Direction direction : Direction.values()) {
                Case voisin = this.donnees.getCarte().getVoisin(c, direction);
                if (voisin != null && voisin == case1) {
                    shortestPath.add(direction);
                    c = voisin;
                    break;
                }
            }
        }
        return new Tuple<>(shortestPath, cases.y);
    }

    public long planifierEvenementsDeplacement(List<Direction> shortestPath, long initialDate, Robot robot) {
        Case c = robot.getPosition();
        long tempDate = initialDate;
        for (Direction direction : shortestPath) {
            tempDate += robot.tempsDeplacement(c, direction);
            simulateur.ajouteEvenement(new EvenementDeplacement(tempDate, robot, direction));
            c = this.donnees.getCarte().getVoisin(c, direction);
        }
        return tempDate;
    }

    public void planifierEvenementsIntervention(long initialDate, Robot robot, Incendie incendie) {
        simulateur.ajouteEvenement(new EvenementIntervention(initialDate + robot.getTempsInterventionIncendie(incendie), robot, incendie, simulateur));
    }

    public void planifierEvenementsRemplissage(long initialDate, Robot robot) {
        simulateur.ajouteEvenement(new EvenementRemplissage(initialDate + robot.getTempsDeRemplissage(), robot));
    }
}
