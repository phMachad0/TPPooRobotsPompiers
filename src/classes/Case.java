package classes;

import enums.NatureTerrain;

/**
 * La classe Case représente une case dans une carte.
 */
public class Case {
    private int ligne;
    private int colonne;
    
    private NatureTerrain nature;
    
    /**
     * Constructeur de la classe Case.
     * 
     * @param ligne la ligne de la case
     * @param colonne la colonne de la case
     * @param nature la nature du terrain de la case
     */
    public Case(int ligne, int colonne, NatureTerrain nature) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nature = nature;
    }

    /**
     * Retourne la ligne de la case.
     * 
     * @return la ligne de la case
     */
    public int getLigne() {
        return this.ligne;
    }

    /**
     * Modifie la ligne de la case.
     * 
     * @param ligne la nouvelle ligne de la case
     */
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }
    
    /**
     * Retourne la colonne de la case.
     * 
     * @return la colonne de la case
     */
    public int getColonne() {
        return this.colonne;
    }
    
    /**
     * Modifie la colonne de la case.
     * 
     * @param colonne la nouvelle colonne de la case
     */
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    /**
     * Retourne la nature du terrain de la case.
     * 
     * @return la nature du terrain de la case
     */
    public NatureTerrain getNature() {
        return this.nature;
    }
    
    /**
     * Modifie la nature du terrain de la case.
     * 
     * @param nature la nouvelle nature du terrain de la case
     */
    public void setNature(NatureTerrain nature) {
        this.nature = nature;
    }

    @Override
    public String toString() {
        return "Case [" + ligne + ", " + colonne + " - " + nature + "]";
    }
}