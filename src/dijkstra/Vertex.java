package dijkstra;

/**
 * Représente un sommet dans un graphe.
 */
public class Vertex implements Comparable<Vertex> {

	private Integer id;
	private long distance;

	/**
	 * Constructeur de la classe Vertex.
	 * 
	 * @param id       l'identifiant du sommet
	 * @param distance la distance du sommet par rapport à un autre sommet
	 */
	public Vertex(Integer id, long distance) {
		super();
		this.id = id;
		this.distance = distance;
	}

	/**
	 * Retourne l'identifiant du sommet.
	 * 
	 * @return l'identifiant du sommet
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Retourne la distance du sommet.
	 * 
	 * @return la distance du sommet
	 */
	public long getDistance() {
		return distance;
	}

	/**
	 * Modifie l'identifiant du sommet.
	 * 
	 * @param id le nouvel identifiant du sommet
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Modifie la distance du sommet.
	 * 
	 * @param distance la nouvelle distance du sommet
	 */
	public void setDistance(long distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + ", distance=" + distance + "]";
	}

	@Override
	public int compareTo(Vertex o) {
		if (this.distance < o.distance)
			return -1;
		else if (this.distance > o.distance)
			return 1;
		else
			return this.getId().compareTo(o.getId());
	}

}