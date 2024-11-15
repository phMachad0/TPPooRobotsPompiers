package classes;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import dijkstra.*;
import enums.*;
import evenements.*;
import utils.Tuple;
/**
 * La classe ChefPompier représente le chef des pompiers qui planifie les actions des robots pour éteindre les incendies.
 * 
 * Elle contient des méthodes pour planifier les déplacements des robots, chercher les incendies et les sources d'eau les plus proches,
 * ainsi que pour effectuer les interventions et les remplissages des réservoirs des robots.
 * Elle contient également des attributs pour stocker les incendies traités par les robots et le nombre d'incendies actifs.
 */
public class ChefPompier {
    private Simulateur simulateur;
    private DonneesSimulation donnees;
    private Map<Incendie, Robot> incendiesTraites;
    private int incendiesActifs = 0;

    /**
     * Constructeur de la classe ChefPompier.
     * @param simulateur Le simulateur utilisé pour la planification.
     * @param donnees Les données de simulation.
     */
    public ChefPompier(Simulateur simulateur, DonneesSimulation donnees) {
        this.simulateur = simulateur;
        this.donnees = donnees;
        this.incendiesTraites = new HashMap<>();
        for (Incendie incendie : this.donnees.getIncendies()) {
            this.incendiesTraites.put(incendie, null);
        }
    }

    /**
     * Planifie les actions des robots pour éteindre les incendies.
     */
    public void planifier() {
        for (Robot robot : this.donnees.getRobots()) {
            if (robot.reservoirVide() && !robot.isOccupe()) {
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
            if (incendie.getLitresNecessaires() != 0) {
                this.incendiesActifs++;
            }
        }
    }

    /**
     * Cherche l'incendie le plus proche et planifie le déplacement du robot pour l'éteindre.
     * @param robot Le robot qui va éteindre l'incendie.
     */
    public void chercherIncendieLePlusProche(Robot robot) {
        Tuple<List<Direction>, Long> chemin = new Tuple<>(new ArrayList<>(), Long.MAX_VALUE);
        Incendie incendiePlusProche = null;
        for (Incendie incendie : this.donnees.getIncendies()) {
            if (incendiesTraites.get(incendie) == null || this.incendiesActifs < this.donnees.getRobots().size()) {
                if (incendie.getLitresNecessaires() != 0) {
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

        System.out.println("robot " + robot.getClass().getName() + " va faire le chemin " + chemin.x +" pour aller eteindre l'incendie " + incendiePlusProche);
    }

    /**
     * Cherche la source d'eau la plus proche et planifie le déplacement du robot pour se remplir.
     * @param robot Le robot qui va se remplir d'eau.
     */
    public void chercherEauLaPlusProche(Robot robot) {
        Tuple<List<Direction>, Long> chemin = new Tuple<>(new ArrayList<>(), Long.MAX_VALUE);
        long tempDate;
        if (robot.getClass().getName().equals("classes.Drone")) {
            for (Case c : this.donnees.getCarte().getListCases()) {
                if (c.getNature() == NatureTerrain.EAU) {
                    Tuple<List<Direction>, Long> tempChemin = chercherCheminLePlusCourt(robot, c);
                    if (tempChemin.y < chemin.y) {
                        chemin = tempChemin;
                    }
                }
            }
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
        }
        tempDate = planifierEvenementsDeplacement(chemin.x, this.simulateur.getDate(), robot);
        planifierEvenementsRemplissage(tempDate, robot);
    }

    /**
     * Cherche le robot le plus proche de l'incendie et planifie son déplacement pour intervenir.
     * @param incendie L'incendie à éteindre.
     */
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
        if (robotPlusProche != null) {
            robotPlusProche.setOccupe(true);
            long tempDate = planifierEvenementsDeplacement(chemin.x, this.simulateur.getDate(), robotPlusProche);
            planifierEvenementsIntervention(tempDate, robotPlusProche, incendie);
        }
    }

    /**
     * Cherche le chemin le plus court entre la position du robot et une case de destination.
     * @param robot Le robot qui se déplace.
     * @param dest La case de destination.
     * @return Un tuple contenant le chemin le plus court sous forme de liste de directions et la durée du chemin.
     */
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
                        voisins.add(new Vertex(this.donnees.getCarte().getCaseId(voisin), temps));
                    }
                }
            }
            g.addVertex(this.donnees.getCarte().getCaseId(c), voisins);
        }

        Tuple<List<Integer>, Long> shortestPathIntegers = g.getShortestPath(this.donnees.getCarte().getCaseId(src), this.donnees.getCarte().getCaseId(dest));
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
        return new Tuple<>(shortestPath, shortestPathIntegers.y);
    }

    /**
     * Planifie les événements de déplacement du robot.
     * @param shortestPath Le chemin à suivre sous forme de liste de directions.
     * @param initialDate La date initiale de planification.
     * @param robot Le robot qui se déplace.
     * @return La date finale de planification.
     */
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

    /**
     * Planifie l'événement d'intervention du robot sur l'incendie.
     * @param initialDate La date initiale de planification.
     * @param robot Le robot qui intervient.
     * @param incendie L'incendie à éteindre.
     */
    public void planifierEvenementsIntervention(long initialDate, Robot robot, Incendie incendie) {
        simulateur.ajouteEvenement(new EvenementIntervention(initialDate + robot.getTempsInterventionIncendie(incendie), robot, incendie, simulateur));
    }

    /**
     * Planifie l'événement de remplissage du réservoir du robot.
     * @param initialDate La date initiale de planification.
     * @param robot Le robot qui se remplit d'eau.
     */
    public void planifierEvenementsRemplissage(long initialDate, Robot robot) {
        simulateur.ajouteEvenement(new EvenementRemplissage(initialDate + robot.getTempsDeRemplissage(), robot));
    }
}
