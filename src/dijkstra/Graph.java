package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import utils.Tuple;

public class Graph {

	private final Map<Integer, List<Vertex>> vertices;

	public Graph() {
		this.vertices = new HashMap<Integer, List<Vertex>>();
	}

	public void addVertex(Integer id, List<Vertex> vertex) {
		this.vertices.put(id, vertex);
	}

	public Tuple<List<Integer>, Long> getShortestPath(Integer start, Integer finish) {
		final Map<Integer, Long> distances = new HashMap<Integer, Long>();
		final Map<Integer, Vertex> previous = new HashMap<Integer, Vertex>();
		PriorityQueue<Vertex> nodes = new PriorityQueue<Vertex>();
		//  distances  = (0, MAX, MAX)
		//  previous  = (null, null, null)
		//  nodes  = (node1, node2, node3)
		for (Integer vertex : vertices.keySet()) {
			if (vertex == start) {
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
			if (smallest.getId() == finish) {
				final List<Integer> path = new ArrayList<Integer>();
				while (previous.get(smallest.getId()) != null) {
					path.add(smallest.getId());
					smallest = previous.get(smallest.getId());
				}
				return new Tuple<List<Integer>, Long>(path, distances.get(finish));
			}

			if (distances.get(smallest.getId()) == Long.MAX_VALUE) {
				break;
			}

			for (Vertex neighbor : vertices.get(smallest.getId())) {
				long alt = distances.get(smallest.getId()) + neighbor.getDistance();
				if (alt < distances.get(neighbor.getId())) {
					distances.put(neighbor.getId(), alt);
					previous.put(neighbor.getId(), smallest);

					forloop: for (Vertex n : nodes) {
						if (n.getId() == neighbor.getId()) {
							nodes.
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