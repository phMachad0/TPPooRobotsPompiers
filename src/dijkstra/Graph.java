package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import utils.Tuple;

/**
 * Représente un graphe.
 */
public class Graph {

	private final Map<Integer, List<Vertex>> vertices;

	/**
	 * Constructeur par défaut de la classe Graph.
	 */
	public Graph() {
		this.vertices = new HashMap<Integer, List<Vertex>>();
	}

	/**
	 * Ajoute un sommet au graphe.
	 * 
	 * @param id     l'identifiant du sommet
	 * @param vertex la liste des sommets adjacents
	 */
	public void addVertex(Integer id, List<Vertex> vertex) {
		this.vertices.put(id, vertex);
	}

	/**
	 * Calcule le plus court chemin entre deux sommets du graphe.
	 * 
	 * @param start  le sommet de départ
	 * @param finish le sommet de destination
	 * @return un tuple contenant le chemin le plus court et la distance totale
	 */
	public Tuple<List<Integer>, Long> getShortestPath(Integer start, Integer finish) {
		final Map<Integer, Long> distances = new HashMap<Integer, Long>();
		final Map<Integer, Vertex> previous = new HashMap<Integer, Vertex>();
		PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>();
		for (Integer vertex : vertices.keySet()) {
			if (vertex.equals(start)) {
				distances.put(vertex, Long.valueOf(0));
				nodes.add(new Vertex(vertex, 0));
			} else {
				distances.put(vertex, Long.MAX_VALUE);
				nodes.add(new Vertex(vertex, Long.MAX_VALUE));
			}
			previous.put(vertex, null);
		}

		while (!nodes.isEmpty()) {
			Vertex smallest = nodes.poll();
			if (finish.equals(smallest.getId())) {
				final List<Integer> path = new ArrayList<Integer>();
				while (previous.get(smallest.getId()) != null) {
					path.add(smallest.getId());
					smallest = previous.get(smallest.getId());
				}
				return new Tuple<List<Integer>, Long>(path, distances.get(finish));
			}

			if (distances.get(smallest.getId()).equals(Long.MAX_VALUE)) {
				break;
			}

			for (Vertex neighbor : vertices.get(smallest.getId())) {
				long alt = distances.get(smallest.getId()) + neighbor.getDistance();
				if (alt < distances.get(neighbor.getId())) {
					distances.put(neighbor.getId(), alt);
					previous.put(neighbor.getId(), smallest);

					forloop: for (Vertex n : nodes) {
						if (n.getId().equals(neighbor.getId())) {
							nodes.remove(n);
							n.setDistance(alt);
							nodes.add(n);
							break forloop;
						}
					}
				}
			}
		}

		return new Tuple<List<Integer>, Long>(new ArrayList<Integer>(distances.keySet()), distances.get(finish));
	}

	@Override
	public String toString() {
		return "Graph [vertices=" + vertices + "]";
	}

}