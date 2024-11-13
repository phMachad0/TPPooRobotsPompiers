package classes;
public abstract class Robot {

    protected Case position;
    protected int actuelVolumeEau;
    protected int maxVolumeEau;
    protected double vitesseDefaut;
    protected int tempsDeRemplissage;
    protected int tempsInterventionUnitaire;
    protected int volumeInterventionUnitaire;
    protected Carte carte;
    protected boolean occupe = false;

    public Robot(Case initialPosition, int actuelVolumeEau, int maxVolumeEau, double vitesseDefaut,
            int tempsDeRemplissage, int tempsInterventionUnitaire, int volumeInterventionUnitaire, Carte carte) {
        this.position = initialPosition;
        this.actuelVolumeEau = actuelVolumeEau;
        this.maxVolumeEau = maxVolumeEau;
        this.vitesseDefaut = vitesseDefaut;
        this.tempsDeRemplissage = tempsDeRemplissage;
        this.tempsInterventionUnitaire = tempsInterventionUnitaire;
        this.volumeInterventionUnitaire = volumeInterventionUnitaire;
        this.carte = carte;
    }
    

    /**
     * Déverse un volume spécifié d'eau du robot.
     * Si le volume spécifié est supérieur au volume d'eau actuel,
     * le volume actuel est mis à 0.
     *
     * @param vol le volume d'eau à déverser
     */
    public void deverserEau(int vol) {
        this.actuelVolumeEau = Math.max(this.actuelVolumeEau - vol, 0);
    }
    
    public abstract void remplirReservoir();

    public Case getPosition() {
        return this.position;
    }

    public void setPosition(Case newPosition) {
        this.position = newPosition;
    }

    public int getActuelVolumeEau() {
        return this.actuelVolumeEau;
    }

    public void setActuelVolumeEau(int actuelVolumeEau) {
        this.actuelVolumeEau = actuelVolumeEau;
    }

    public int getMaxVolumeEau() {
        return this.maxVolumeEau;
    }

    public void setMaxVolumeEau(int maxVolumeEau) {
        this.maxVolumeEau = maxVolumeEau;
    }

    public double getVitesseDefaut() {
        return this.vitesseDefaut;
    }

    public void setVitesseDefaut(double vitesseDefaut) {
        this.vitesseDefaut = vitesseDefaut;
    }

    public int getTempsDeRemplissage() {
        return this.tempsDeRemplissage;
    }

    public void setTempsDeRemplissage(int tempsDeRemplissage) {
        this.tempsDeRemplissage = tempsDeRemplissage;
    }

    public int getTempsInterventionUnitaire() {
        int volumeDeversee = Math.min(this.getVolumeInterventionUnitaire(), this.getActuelVolumeEau());
        return this.tempsInterventionUnitaire * (volumeDeversee / this.getVolumeInterventionUnitaire());
    }

    public void setTempsInterventionUnitaire(int tempsInterventionUnitaire) {
        this.tempsInterventionUnitaire = tempsInterventionUnitaire;
    }

    public double getVitesse() {
        return this.getVitesseDefaut();
    }

    public int getVolumeInterventionUnitaire() {
        return volumeInterventionUnitaire;
    }

    public void setVolumeInterventionUnitaire(int volumeInterventionUnitaire) {
        this.volumeInterventionUnitaire = volumeInterventionUnitaire;
    }
    
    @Override
    public String toString() {
        return "\n\t\tRobot [position=" + position + ", actuelVolumeEau=" + actuelVolumeEau + ", maxVolumeEau=" + maxVolumeEau
                + ", vitesseDefaut=" + vitesseDefaut + ", tempsDeRemplissage=" + tempsDeRemplissage
                + ", tempsInterventionUnitaire=" + tempsInterventionUnitaire + ", volumeInterventionUnitaire="
                + volumeInterventionUnitaire + "]";
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public boolean resevoirVide() {
        return this.actuelVolumeEau == 0;
    }

    public boolean isOccupe() {
        return occupe;
    }

    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

    /**
     * Déplace le robot dans la direction spécifiée.
     *
     * @param direction la direction dans laquelle le robot doit se déplacer
     */
    public abstract void deplacerVers(Direction direction);

    /**
     * Calcule le temps nécessaire au robot pour se déplacer dans la direction spécifiée.
     *
     * @param direction la direction dans laquelle le robot se déplace
     * @return le temps nécessaire au robot pour se déplacer dans la direction spécifiée
     */
    public abstract double tempsDeplacement(Direction direction);

    /**
     * Effectue une intervention unitaire sur un incendie.
     * 
     * @param incendie L'incendie sur lequel intervenir.
     * @return Le volume d'eau versé sur l'incendie.
     */
    public abstract int interventionUnitaire(Incendie incendie);
}
