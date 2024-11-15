package dijkstra;

public class Vertex implements Comparable<Vertex> {

	private Integer id;
	private long distance;

	public Vertex(Integer id, long distance) {
		super();
		this.id = id;
		this.distance = distance;
	}

	public Integer getId() {
		return id;
	}

	public long getDistance() {
		return distance;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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