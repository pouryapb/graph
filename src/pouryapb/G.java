package pouryapb;

import java.util.Collections;
import java.util.LinkedList;

public class G {

	class Vertex {

		private String V;
		private LinkedList<Edge> edges = new LinkedList<Edge>();

		public Vertex(String V) {
			this.V = V;
		}

		public String getName() {
			return V;
		}

		public void connect(Vertex V, int weight, EdgeObject e) {
			Edge edge = new Edge(weight, this, V, e);
			edges.add(edge);
			V.edges.add(edge);
		}
	}

	class Edge implements Comparable<Edge>{

		private int weight;
		private Vertex v1, v2;
		private EdgeObject e;

		public Edge (int weight, Vertex v1, Vertex v2, EdgeObject e) {
			this.weight = weight;
			this.v1 = v1;
			this.v2 = v2;
			this.e = e;
		}

		public EdgeObject getEdgeObject() {
			return e;
		}

		public int getWeight() {
			return weight;
		}

		public Vertex getV1() {
			return v1;
		}

		public Vertex getV2() {
			return v2;
		}

		public int compareTo(Edge o) {
			if (this.weight > o.weight)
				return 1;
			if (this.weight < o.weight)
				return -1;
			return 0;
		}
	}

	private LinkedList<Vertex> vertices = new LinkedList<Vertex>();

	public void addVertex(String name) {
		vertices.add(new Vertex(name));
	}

	public boolean connnect(String v1, String v2, int weight, EdgeObject e) {

		Vertex V1 = null, V2 = null;

		for (int i = 0; i < vertices.size(); i++) {

			if (vertices.get(i).getName().equals(v1)) {
				V1 = vertices.get(i);
			}
			else if (vertices.get(i).getName().equals(v2)) {
				V2 = vertices.get(i);
			}
		}

		if (V1 == null || V2 == null)
			return false;

		V1.connect(V2, weight, e);
		return true;
	}

	public void clear() {
		vertices.clear();
	}

	public LinkedList<Edge> boruvka(){

		LinkedList<Edge> pickedEdges = new LinkedList<Edge>();

		LinkedList<LinkedList<Vertex>> bigVs = new LinkedList<LinkedList<Vertex>>();

		// first adding all nodes
		for (int i = 0; i < vertices.size(); i++) {
			bigVs.add(new LinkedList<Vertex>());
			bigVs.getLast().add(vertices.get(i));
		}

		// while bigVs has more than one item in it
		// algorithm should run
		while (bigVs.size() > 1) {
			
			// pick the smallest edge from each node
			for (int i = 0; i < bigVs.size(); i++) {
				Edge temp = pickSmallest(bigVs.get(i));
				if (!pickedEdges.contains(temp))
					pickedEdges.add(temp);
			}
			
			// SEEING connected nodes as a single node
			bigVs = newList(pickedEdges, bigVs);
		}

		return pickedEdges;
	}

	private LinkedList<LinkedList<Vertex>> newList(LinkedList<Edge> pickedEdges, LinkedList<LinkedList<Vertex>> bigVs) {
		
		for (int i = 0; i < bigVs.size(); i++) {
			for (int j = i + 1; j < bigVs.size(); j++) {
				for (int k = 0; k < pickedEdges.size(); k++) {
					if (isConnectedWith(bigVs.get(i), bigVs.get(j), pickedEdges.get(k))) {
						bigVs.get(i).addAll(bigVs.get(j));
						bigVs.remove(j);
						j = i + 1;
						break;
					}
				}
			}
		}

		return bigVs;
	}
	
	private boolean isConnectedWith(LinkedList<Vertex> v1, LinkedList<Vertex> v2, Edge e) {
		
		boolean v1ContainsEdge = false;
		boolean v2ContainsEdge = false;
		
		for (int i = 0; i < v1.size(); i++) {
			if (v1.get(i).edges.contains(e)) {
				v1ContainsEdge = true;
				break;
			}
		}
		
		for (int i = 0; i < v2.size(); i++) {
			if (v2.get(i).edges.contains(e)) {
				v2ContainsEdge = true;
				break;
			}
		}
		
		return (v1ContainsEdge && v2ContainsEdge);
	}

	private Edge pickSmallest(LinkedList<Vertex> bigVs) {

		LinkedList<Edge> edges = new LinkedList<Edge>();

		// making a list of all edges to sort them
		for (int i = 0; i < bigVs.size(); i++) {
			Vertex current = bigVs.get(i);

			for (int j = 0; j < current.edges.size(); j++) {
				if (!edges.contains(current.edges.get(j)))
					edges.add(current.edges.get(j));
			}
		}

		Collections.sort(edges);

		while (hasCycle(edges.getFirst(), bigVs))
			edges.removeFirst();

		return edges.getFirst();
	}

	private boolean hasCycle(Edge e, LinkedList<Vertex> Vs) {

		return (Vs.contains(e.getV1()) && Vs.contains(e.getV2())) ? true : false;
	}

	public LinkedList<Edge> prim() {

		LinkedList<Vertex> bigV = new LinkedList<Vertex>();
		LinkedList<Edge> pickedEdges = new LinkedList<Edge>();

		bigV.add(vertices.get(0));

		while (bigV.size() < vertices.size()) {

			Edge chosen = Collections.min(choosable(bigV));
			pickedEdges.add(chosen);

			if (!bigV.contains(chosen.getV1()))
				bigV.add(chosen.getV1());
			else
				bigV.add(chosen.getV2());

		}

		return pickedEdges;
	}

	/**
	 * removes edges that make a loop  from the list
	 * @param vertices
	 * @return choosable edges from list of edges
	 */
	private LinkedList<Edge> choosable(LinkedList<Vertex> vertices) {

		LinkedList<Edge> res = new LinkedList<Edge>();

		for (int i = 0; i < vertices.size(); i++) {

			Vertex current = vertices.get(i);
			for (int j = 0; j < current.edges.size(); j++) {

				// find loops!!
				if (!res.contains(current.edges.get(j))) {
					res.add(current.edges.get(j));
				}
				else {
					res.remove(current.edges.get(j));
				}
			}
		}

		return res;
	}
}
