package classes;

import java.util.ArrayList;
import java.util.List;

public class Carte {

    private int tailleCases, nbLignes, nbColonnes;
    private Case[][] cases;
    private ArrayList<Case> listCases;

    public Carte(int tailleCases, int nbLignes, int nbColonnes, Case[][] cases) {   
        this.tailleCases = tailleCases;
        this.nbColonnes = nbColonnes;
        this.nbLignes = nbLignes;
        this.cases = cases;
        this.listCases = new ArrayList<Case>();
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                this.listCases.add(cases[i][j]);
            }
        }
    }

    public Case getCase(int lig, int col) {
        if (lig >=  this.nbLignes || col >= this.nbColonnes ) {
            return null;
        }
        return this.cases[lig][col];
    }

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

    public boolean caseIsthme(Case src, Robot robot){
        List<Double> terrainttransponible = new ArrayList<Double>();
        Direction[] directions = {Direction.EST, Direction.OUEST, Direction.NORD, Direction.SUD};
        for (Direction dir : directions) {
            if (robot.tempsDeplacement(dir) == Double.POSITIVE_INFINITY) {
                
            } 
            else {
                terrainttransponible.add(1.0);
            }
        }
        return terrainttransponible.size() == 1;
    }

    public int getNbLignes() {
        return this.nbLignes;
    }

    public void setNbLignes(int nbLignes) {
        this.nbLignes = nbLignes;
    }

    public int getNbColonnes() {
        return this.nbColonnes;
    }

    public void setNbColonnes(int nbColonnes) {
        this.nbColonnes = nbColonnes;
    }

    public int getTailleCases() {
        return this.tailleCases;
    }
    
    public void setTailleCases(int tailleCases) {
        this.tailleCases = tailleCases;
    }

    public Case[][] getCases() {
        return cases;
    }

    public void setCases(Case[][] cases) {
        this.cases = cases;
        this.listCases = new ArrayList<Case>();
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                this.listCases.add(cases[i][j]);
            }
        }
    }
    
    public ArrayList<Case> getListCases() {
        return listCases;
    }
    
    public void setListCases(ArrayList<Case> listCases) {
        this.listCases = listCases;
        this.cases = new Case[nbLignes][nbColonnes];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                this.cases[i][j] = listCases.get(i*nbColonnes + j);
            }
        }
    }

    @Override
    public String toString() {
        return "\n\tCarte [tailleCases=" + tailleCases + ", nbLignes=" + nbLignes + ", nbColonnes=" + nbColonnes + "]";
    }
}
