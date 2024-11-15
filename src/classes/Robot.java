package classes;

import enums.Direction;

/**
 * Classe abstraite représentant un robot.
 */
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

    /**
     * Constructeur de la classe Robot.
     *
     * @param initialPosition            la position initiale du robot
     * @param actuelVolumeEau            le volume d'eau actuel du robot
     * @param maxVolumeEau               le volume d'eau maximum du robot
     * @param vitesseDefaut              la vitesse par défaut du robot
     * @param tempsDeRemplissage         le temps de remplissage du réservoir du
     *                                   robot
     * @param tempsInterventionUnitaire  le temps d'intervention unitaire du robot
     * @param volumeInterventionUnitaire le volume d'intervention unitaire du robot
     * @param carte                      la carte sur laquelle le robot évolue
     */
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

    /**
     * Déplace le robot dans la direction spécifiée.
     *
     * @param direction la direction dans laquelle le robot doit se déplacer
     */
    public abstract void deplacerVers(Direction direction);

    /**
     * Calcule le temps nécessaire au robot pour se déplacer dans la direction
     * spécifiée.
     *
     * @param c         la case actuelle du robot
     * @param direction la direction dans laquelle le robot se déplace
     * @return le temps nécessaire au robot pour se déplacer dans la direction
     *         spécifiée
     */
    public abstract long tempsDeplacement(Case c, Direction direction);

    /**
     * Effectue une intervention unitaire sur un incendie.
     * 
     * @param incendie L'incendie sur lequel intervenir.
     */
    public abstract void effectuerInterventionUnitaire(Incendie incendie);

    /**
     * Remplit le réservoir du robot.
     */
    public void remplirReservoir() {
        this.actuelVolumeEau = this.maxVolumeEau;
    }

    /**
     * Retourne la position actuelle du robot.
     *
     * @return la position actuelle du robot
     */
    public Case getPosition() {
        return this.position;
    }

    /**
     * Définit la position du robot.
     *
     * @param newPosition la nouvelle position du robot
     */
    public void setPosition(Case newPosition) {
        this.position = newPosition;
    }

    /**
     * Retourne le volume d'eau actuel du robot.
     *
     * @return le volume d'eau actuel du robot
     */
    public int getActuelVolumeEau() {
        return this.actuelVolumeEau;
    }

    /**
     * Définit le volume d'eau actuel du robot.
     *
     * @param actuelVolumeEau le nouveau volume d'eau actuel du robot
     */
    public void setActuelVolumeEau(int actuelVolumeEau) {
        this.actuelVolumeEau = actuelVolumeEau;
    }

    /**
     * Retourne le volume d'eau maximum du robot.
     *
     * @return le volume d'eau maximum du robot
     */
    public int getMaxVolumeEau() {
        return this.maxVolumeEau;
    }

    /**
     * Définit le volume d'eau maximum du robot.
     *
     * @param maxVolumeEau le nouveau volume d'eau maximum du robot
     */
    public void setMaxVolumeEau(int maxVolumeEau) {
        this.maxVolumeEau = maxVolumeEau;
    }

    /**
     * Retourne la vitesse par défaut du robot.
     *
     * @return la vitesse par défaut du robot
     */
    public double getVitesseDefaut() {
        return this.vitesseDefaut;
    }

    /**
     * Définit la vitesse par défaut du robot.
     *
     * @param vitesseDefaut la nouvelle vitesse par défaut du robot
     */
    public void setVitesseDefaut(double vitesseDefaut) {
        this.vitesseDefaut = vitesseDefaut;
    }

    /**
     * Retourne le temps de remplissage du réservoir du robot.
     *
     * @return le temps de remplissage du réservoir du robot
     */
    public int getTempsDeRemplissage() {
        return this.tempsDeRemplissage;
    }

    /**
     * Définit le temps de remplissage du réservoir du robot.
     *
     * @param tempsDeRemplissage le nouveau temps de remplissage du réservoir du
     *                           robot
     */
    public void setTempsDeRemplissage(int tempsDeRemplissage) {
        this.tempsDeRemplissage = tempsDeRemplissage;
    }

    /**
     * Retourne le temps d'intervention unitaire du robot.
     *
     * @return le temps d'intervention unitaire du robot
     */
    public int getTempsInterventionUnitaire() {
        return this.tempsInterventionUnitaire;
    }

    /**
     * Définit le temps d'intervention unitaire du robot.
     *
     * @param tempsInterventionUnitaire le nouveau temps d'intervention unitaire du
     *                                  robot
     */
    public void setTempsInterventionUnitaire(int tempsInterventionUnitaire) {
        this.tempsInterventionUnitaire = tempsInterventionUnitaire;
    }

    /**
     * Retourne le temps d'intervention nécessaire pour éteindre un incendie.
     *
     * @param incendie l'incendie à éteindre
     * @return le temps d'intervention nécessaire pour éteindre l'incendie
     */
    public int getTempsInterventionIncendie(Incendie incendie) {
        int volumeDeversee = this.getVolumeDeversee(incendie);
        return this.tempsInterventionUnitaire * (volumeDeversee / this.getVolumeInterventionUnitaire());
    }

    /**
     * Retourne le volume d'eau à déverser pour une intervention à un incendie.
     *
     * @param incendie l'incendie à intervenir
     * @return le volume d'eau à déverser pour une intervention à un incendie
     */
    public int getVolumeDeversee(Incendie incendie) {
        int volumeNecessaire = incendie.getLitresNecessaires();
        return Math.min(Math.min(this.getVolumeInterventionUnitaire(), this.getActuelVolumeEau()), volumeNecessaire);
    }

    /**
     * Retourne la vitesse du robot.
     *
     * @return la vitesse du robot
     */
    public double getVitesse() {
        return this.getVitesseDefaut();
    }

    /**
     * Retourne le volume d'intervention unitaire du robot.
     *
     * @return le volume d'intervention unitaire du robot
     */
    public int getVolumeInterventionUnitaire() {
        return this.volumeInterventionUnitaire;
    }

    /**
     * Définit le volume d'intervention unitaire du robot.
     *
     * @param volumeInterventionUnitaire le nouveau volume d'intervention unitaire
     *                                   du robot
     */
    public void setVolumeInterventionUnitaire(int volumeInterventionUnitaire) {
        this.volumeInterventionUnitaire = volumeInterventionUnitaire;
    }

    /**
     * Retourne la carte sur laquelle le robot évolue.
     *
     * @return la carte sur laquelle le robot évolue
     */
    public Carte getCarte() {
        return carte;
    }

    /**
     * Définit la carte sur laquelle le robot évolue.
     *
     * @param carte la nouvelle carte sur laquelle le robot évolue
     */
    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    /**
     * Vérifie si le réservoir du robot est vide.
     *
     * @return true si le réservoir du robot est vide, false sinon
     */
    public boolean reservoirVide() {
        return this.actuelVolumeEau == 0;
    }

    /**
     * Vérifie si le robot est occupé.
     *
     * @return true si le robot est occupé, false sinon
     */
    public boolean isOccupe() {
        return occupe;
    }

    /**
     * Définit si le robot est occupé.
     *
     * @param occupe true si le robot est occupé, false sinon
     */
    public void setOccupe(boolean occupe) {
        this.occupe = occupe;
    }

    @Override
    public String toString() {
        return "Robot [" + position + ", a " + actuelVolumeEau + "L de" + maxVolumeEau
                + " Total, " + vitesseDefaut + "km/h , rempli en" + tempsDeRemplissage
                + "s, intervient en" + tempsInterventionUnitaire + "s avec"
                + volumeInterventionUnitaire + "]";
    }
}
