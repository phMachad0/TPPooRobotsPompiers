package classes;
public class Incendie {
    private Case position;
    private int litresNecessaires;

    public Incendie(Case position, int litresNecessaires) {
        this.position = position;
        this.litresNecessaires = litresNecessaires;
    }

    public Case getPosition(){
        return this.position;
    }

    public void setPosition(Case position) {
        this.position = position;
    }
    
    public int getLitreNecessaires(){
        return this.litresNecessaires;
    }

    public void setLitreNecessaires(int litresNecessaires) {
        this.litresNecessaires = litresNecessaires;
    }

    @Override
    public String toString() {
        return "\n\t\tIncendie [position=" + position + ", litresNecessaires=" + litresNecessaires + "]";
    }
}
