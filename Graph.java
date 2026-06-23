package assign07;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class contains the representation of graphs using Edges and Matrices
 * 
 * @author Cameron McKay and Daler Turyssov
 * @version 2025-06-22
 */
public class Graph<Type> {
	private HashMap<Type, Vertex> graphMap;

	/**
	 * Main constructor for Graph object
	 */
	public Graph() {
		this.graphMap = new HashMap<>();
	}

	/**
	 * Builds the graph by adding a vertex if it is doesnt exist
	 * 
	 * @param data - The data held by the vertex
	 */
	public void addVertex(Type data) {
		if (!(graphMap.containsKey(data))) {
			Vertex newVertex = new Vertex(data);
			graphMap.put(data, newVertex);
		}
	}

	/**
	 * Builds the graph by adding an edge between 2 vertices Adds verticies if they
	 * don't exist
	 * 
	 * @param source      - The Vertex adding the edge
	 * @param destination - The destination of the edge
	 */
	public void addEdge(Type source, Type destination) {
		// Makes sure they exist
		this.addVertex(source);
		this.addVertex(destination);

		Vertex vertexSource = graphMap.get(source);
		Vertex vertexDestination = graphMap.get(destination);
		vertexSource.addEdge(vertexDestination);
	}

	/**
	 * Searches if a path exists between two vertices on a graph
	 * 
	 * @param source      - Starting Vertex
	 * @param destination - Target Vertex
	 * @return boolean true if path exists false if not
	 */
	public boolean depthFirstSearch(Type source, Type destination) {
		HashMap<Type, Boolean> visited = new HashMap<>();
		return depthFirstSearchHelper(source, destination, visited);
	}

	/**
	 * Private recursive helper method for depth first search
	 * 
	 * @param source  the current vertex being searched
	 * @param target  the destination vertex being searched for
	 * @param visited that stores vertices that have already been visited
	 * @return true if there is a path from current vertex to the target or false if
	 *         not
	 */
	private boolean depthFirstSearchHelper(Type source, Type target, HashMap<Type, Boolean> visited) {
		if (source == target) {
			return true;
		}
		visited.put(source, true);
		Vertex sourceVertex = graphMap.get(source);
		List<Edge> outgoingEdges = sourceVertex.getEdges();
		for (Edge edgeDestination : outgoingEdges) {
			Vertex destinationVertex = edgeDestination.getDestination();
			if (!visited.containsKey(target)) {
				return depthFirstSearchHelper(destinationVertex.getData(), target, visited);
			}
		}
		return false;
	}

	/**
	 * Finds the shortest path between to vertices
	 * 
	 * @param source      - Starting Vertex
	 * @param destination - Target Vertex
	 * @return List<Type> a list of all vertices visted on fastest path
	 */
	public List<Type> breadthFirstSearch(Type source, Type destination) {
		List<Type> returnList = new LinkedList<Type>();
		return returnList;
	}

	/**
	 * Sorts a map of objects topologically Only works on acyclic graphs
	 * 
	 * @return List<Type> sorted list topologically
	 */
	public List<Type> topoSort() {
		List<Type> returnList = new LinkedList<Type>();
		return returnList;
	}

	/**
	 * A class that represents directed edges in between vertices Holds the
	 * destination data
	 */
	private class Edge {
		private Vertex destination;

		public Edge(Vertex destination) {
			this.destination = destination;
		}

		public Vertex getDestination() {
			return this.destination;
		}

	}

	/**
	 * A class that represents the vertices in a graph. Holds generic data and
	 * outgoing edges.
	 */
	private class Vertex {
		private List<Edge> outgoingEdges;
		private Type data;

		public Vertex(Type data) {
			this.data = data;
			this.outgoingEdges = new LinkedList<Edge>();

		}

		public void addEdge(Vertex destination) {
			Edge newEdge = new Edge(destination);
			outgoingEdges.add(newEdge);
		}

		public Type getData() {
			return this.data;
		}

		public List<Edge> getEdges() {
			return this.outgoingEdges;
		}

	}
}
