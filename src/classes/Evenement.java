package classes;

public abstract class Evenement {
    long date;

    public Evenement(long date) {
        this.date = date;
    }

    public long getDate() {
        return this.date;
    }

    public abstract void execute();
}
