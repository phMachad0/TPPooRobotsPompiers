package classes;

public class Case {
    private int ligne;
    private int colonne;
    
    private NatureTerrain nature;
    
    public Case(int ligne, int colonne, NatureTerrain nature) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.nature = nature;
    }

    public int getLigne() {
        return this.ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }
    
    public int getColonne() {
        return this.colonne;
    }
    
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    public NatureTerrain getNature() {
        return this.nature;
    }
    
    public void setNature(NatureTerrain nature) {
        this.nature = nature;
    }

    @Override
    public String toString() {
        return "Case [ligne=" + ligne + ", colonne=" + colonne + ", nature=" + nature + "]";
    }
}