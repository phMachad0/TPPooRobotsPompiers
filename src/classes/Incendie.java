package classes;
/**
 * La classe Incendie représente un incendie à éteindre.
 */
public class Incendie {
    private Case position;
    private int litresNecessaires;

    /**
     * Constructeur de la classe Incendie.
     * 
     * @param position La position de l'incendie.
     * @param litresNecessaires Le nombre de litres d'eau nécessaires pour éteindre l'incendie.
     */
    public Incendie(Case position, int litresNecessaires) {
        this.position = position;
        this.litresNecessaires = litresNecessaires;
    }

    /**
     * Retourne la position de l'incendie.
     * 
     * @return La position de l'incendie.
     */
    public Case getPosition(){
        return this.position;
    }

    /**
     * Modifie la position de l'incendie.
     * 
     * @param position La nouvelle position de l'incendie.
     */
    public void setPosition(Case position) {
        this.position = position;
    }
    
    /**
     * Retourne le nombre de litres d'eau nécessaires pour éteindre l'incendie.
     * 
     * @return Le nombre de litres d'eau nécessaires pour éteindre l'incendie.
     */
    public int getLitresNecessaires(){
        return this.litresNecessaires;
    }

    /**
     * Modifie le nombre de litres d'eau nécessaires pour éteindre l'incendie.
     * 
     * @param litresNecessaires Le nouveau nombre de litres d'eau nécessaires pour éteindre l'incendie.
     */
    public void setLitresNecessaires(int litresNecessaires) {
        this.litresNecessaires = litresNecessaires;
    }

    @Override
    public String toString() {
        return "Incendie [" + position + ", il reste " + litresNecessaires + "L à éteindre]";
    }
}
