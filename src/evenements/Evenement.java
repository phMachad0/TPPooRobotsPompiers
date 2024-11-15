package evenements;

/**
 * Classe abstraite représentant un événement.
 */
public abstract class Evenement {
    long date;

    /**
     * Constructeur de la classe Evenement.
     * @param date La date de l'événement.
     */
    public Evenement(long date) {
        this.date = date;
    }

    /**
     * Obtient la date de l'événement.
     * @return La date de l'événement.
     */
    public long getDate() {
        return this.date;
    }

    /**
     * Méthode abstraite pour exécuter l'événement.
     */
    public abstract void execute();
}
