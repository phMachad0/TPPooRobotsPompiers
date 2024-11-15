package classes;

import java.util.ArrayList;
import java.util.List;

import enums.Direction;

/**
 * La classe Carte représente une carte composée de cases.
 */
public class Carte {

    private int tailleCases, nbLignes, nbColonnes;
    private Case[][] cases;

    /**
     * Constructeur de la classe Carte.
     * 
     * @param tailleCases la taille des cases de la carte
     * @param nbLignes le nombre de lignes de la carte
     * @param nbColonnes le nombre de colonnes de la carte
     * @param cases la matrice de cases de la carte
     */
    public Carte(int tailleCases, int nbLignes, int nbColonnes, Case[][] cases) {   
        this.tailleCases = tailleCases;
        this.nbColonnes = nbColonnes;
        this.nbLignes = nbLignes;
        this.cases = cases;
    }

    /**
     * Récupère la case à la position spécifiée.
     * 
     * @param lig la ligne de la case
     * @param col la colonne de la case
     * @return la case à la position spécifiée, ou null si les coordonnées sont invalides
     */
    public Case getCase(int lig, int col) {
        if (lig >=  this.nbLignes || col >= this.nbColonnes || lig < 0 || col < 0) {
            return null;
        }
        return this.cases[lig][col];
    }

    /**
     * Vérifie si un voisin existe pour une case donnée dans une direction donnée.
     * 
     * @param src la case source
     * @param dir la direction du voisin
     * @return true si un voisin existe, false sinon
     */
    public boolean voisinExiste(Case src, Direction dir) {
        int currentCaseLigne = src.getLigne();
        int currentCaseColonne = src.getColonne();

        switch (dir) {
            case NORD:
                return (getCase(currentCaseLigne-1, currentCaseColonne) != null);
            case SUD:
                return (getCase(currentCaseLigne+1, currentCaseColonne) != null);
            case EST:
                return (getCase(currentCaseLigne, currentCaseColonne+1) != null);
            case OUEST:
                return (getCase(currentCaseLigne, currentCaseColonne-1) != null);
            default:
                return false;
        }
    }

    /**
     * Récupère le voisin d'une case donnée dans une direction donnée.
     * 
     * @param src la case source
     * @param dir la direction du voisin
     * @return le voisin de la case source dans la direction spécifiée, ou null si le voisin n'existe pas
     */
    public Case getVoisin(Case src, Direction dir) {
        int currentCaseLigne = src.getLigne();
        int currentCaseColonne = src.getColonne();

        switch (dir) {
            case NORD:
                return getCase(currentCaseLigne-1, currentCaseColonne);
            case SUD:
                return getCase(currentCaseLigne+1, currentCaseColonne);
            case EST:
                return getCase(currentCaseLigne, currentCaseColonne+1);
            case OUEST:
                return getCase(currentCaseLigne, currentCaseColonne-1);
            default:
                return null;
        }
    }

    /**
     * Vérifie si une case est un isthme pour un robot donné.
     * 
     * @param src la case source
     * @param robot le robot
     * @return true si la case est un isthme, false sinon
     */
    public boolean caseIsthme(Case src, Robot robot){
        List<Double> terrainttransponible = new ArrayList<Double>();
        Direction[] directions = {Direction.EST, Direction.OUEST, Direction.NORD, Direction.SUD};
        for (Direction dir : directions) {
            if (this.voisinExiste(robot.getPosition(), dir) != false) {
                if (robot.tempsDeplacement(robot.getPosition(), dir) == Double.POSITIVE_INFINITY) {
                    
                } 
                else {
                    terrainttransponible.add(1.0);
                }
            } 
        }
        return terrainttransponible.size() == 1;
    }

    /**
     * Obtient le nombre de lignes de la carte.
     * 
     * @return le nombre de lignes de la carte
     */
    public int getNbLignes() {
        return this.nbLignes;
    }

    /**
     * Définit le nombre de lignes de la carte.
     * 
     * @param nbLignes le nombre de lignes de la carte
     */
    public void setNbLignes(int nbLignes) {
        this.nbLignes = nbLignes;
    }

    /**
     * Obtient le nombre de colonnes de la carte.
     * 
     * @return le nombre de colonnes de la carte
     */
    public int getNbColonnes() {
        return this.nbColonnes;
    }

    /**
     * Définit le nombre de colonnes de la carte.
     * 
     * @param nbColonnes le nombre de colonnes de la carte
     */
    public void setNbColonnes(int nbColonnes) {
        this.nbColonnes = nbColonnes;
    }

    /**
     * Obtient la taille des cases de la carte.
     * 
     * @return la taille des cases de la carte
     */
    public int getTailleCases() {
        return this.tailleCases;
    }
    
    /**
     * Définit la taille des cases de la carte.
     * 
     * @param tailleCases la taille des cases de la carte
     */
    public void setTailleCases(int tailleCases) {
        this.tailleCases = tailleCases;
    }

    /**
     * Obtient la matrice de cases de la carte.
     * 
     * @return la matrice de cases de la carte
     */
    public Case[][] getCases() {
        return cases;
    }

    /**
     * Définit la matrice de cases de la carte.
     * 
     * @param cases la matrice de cases de la carte
     */
    public void setCases(Case[][] cases) {
        this.cases = cases;
    }
    
    /**
     * Obtient une liste de toutes les cases de la carte.
     * 
     * @return une liste de toutes les cases de la carte
     */
    public ArrayList<Case> getListCases() {
        ArrayList<Case> listCases = new ArrayList<Case>();
        for (int i = 0; i < this.nbLignes; i++) {
            for (int j = 0; j < this.nbColonnes; j++) {
                listCases.add(cases[i][j]);
            }
        }
        return listCases;
    }

    /**
     * Obtient l'identifiant d'une case.
     * 
     * @param c la case
     * @return l'identifiant de la case
     */
    public int getCaseId(Case c) {
        return c.getLigne() * this.nbColonnes + c.getColonne();
    }

    /**
     * Obtient la case correspondant à un identifiant.
     * 
     * @param id l'identifiant de la case
     * @return la case correspondant à l'identifiant
     */
    public Case getCaseById(int id) {
        return this.getListCases().get(id);
    }

    @Override
    public String toString() {
        return "Carte [" + nbLignes + "x" + nbColonnes + ", " + tailleCases + "]";
    }
}
