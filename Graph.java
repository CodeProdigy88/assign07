package assign07;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	 * Returns vertex with data in the graph, Throws an expection if not in graph
	 * 
	 * @param data
	 * @return
	 */
	public Vertex getVertex(Type data) {
		Vertex checkVertex = graphMap.get(data);
		if (checkVertex == null) {
			throw new IllegalArgumentException();
		}
		return checkVertex;
	}

	/**
	 * Getter method for graph size
	 * 
	 * @return int size of the graph
	 */
	public int getGraphSize() {
		return graphMap.size();
	}

	/**
	 * Searches if a path exists between two vertices on a graph
	 * 
	 * @param source      - Starting Vertex
	 * @param destination - Target Vertex
	 * @return boolean true if path exists false if not
	 */
	public boolean depthFirstSearch(Type source, Type destination) {

		// Throws exceptions if doesn't exist
		this.getVertex(source);
		this.getVertex(destination);

		HashMap<Type, Boolean> visited = new HashMap<>();
		return depthFirstSearchHelper(source, destination, visited);
	}

	/**
	 * Private recursive helper method for depth first search
	 * 
	 * @param source  the current vertex being searched
	 * @param target  the destination vertex being searched for
	 * @param visited that stores vertices that have already been visited
	 * @return boolean true if path exists false if not
	 */
	private boolean depthFirstSearchHelper(Type source, Type target, HashMap<Type, Boolean> visited) {
		if (source.equals(target)) {
			return true;
		}
		visited.put(source, true);
		Vertex sourceVertex = graphMap.get(source);

		List<Edge> outgoingEdges = sourceVertex.getEdges();

		for (Edge edgeDestination : outgoingEdges) {
			Vertex destinationVertex = edgeDestination.getDestination();

			if (!visited.containsKey(destinationVertex.getData())) {

				if (depthFirstSearchHelper(destinationVertex.getData(), target, visited)) {
					return true;
				}
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

		// Throws exceptions if doesn't exist
		this.addVertex(source);
		this.addVertex(destination);

		LinkedList<Type> returnList = new LinkedList<Type>();
		HashMap<Type, Type> cameFrom = new HashMap<>();
		Queue<Vertex> searchQueue = new LinkedList<Vertex>();

		Vertex targetVertex = graphMap.get(destination);

		// Sets up the lists with the source data
		searchQueue.add(graphMap.get(source));
		cameFrom.put(source, source);

		while (!searchQueue.isEmpty()) {
			Vertex current = searchQueue.poll();

			// Found target, breaks out of loop to make returnList
			if (current.getData().equals(targetVertex.getData())) {
				break;
			}

			List<Edge> outgoingEdges = current.getEdges();

			for (Edge edgeDestination : outgoingEdges) {

				Vertex destinationVertex = edgeDestination.getDestination();
				if (!cameFrom.containsKey(destinationVertex.getData())) {
					cameFrom.put(destinationVertex.getData(), current.getData());
					searchQueue.add(destinationVertex);
				}
			}
		}

		// If it didn't find the target and the source isnt the destination
		// There is no path
		if (!cameFrom.containsKey(destination) && !source.equals(destination)) {
			return returnList;
		}

		Type path = destination;
		while (!path.equals(source)) {
			returnList.addFirst(path);
			path = cameFrom.get(path);
		}
		returnList.addFirst(source);

		return returnList;
	}

	/**
	 * Sorts a map of objects topologically Only works on acyclic graphs
	 * 
	 * @return List<Type> sorted list topologically
	 */
	public List<Type> topoSort() {
		Queue<Vertex> searchQueue = new LinkedList<Vertex>();
		List<Type> returnList = new LinkedList<Type>();
		HashMap<Type, Integer> inDegree = new HashMap<>();

		// Sets all to 0
		for (Type key : graphMap.keySet()) {
			inDegree.put(key, 0);
		}

		// Assings amout of inDegrees for everything in map
		for (Vertex vertex : graphMap.values()) {
			for (Edge edge : vertex.getEdges()) {
				Type destination = edge.getDestination().getData();
				int newInDegree = inDegree.get(destination) + 1;
				inDegree.put(destination, newInDegree);
			}
		}
		// Pulls out all inDegree 0 to start
		for (Type key : inDegree.keySet()) {
			if (inDegree.get(key) == 0) {
				searchQueue.add(graphMap.get(key));
			}
		}

		while (!searchQueue.isEmpty()) {
			Vertex data = searchQueue.poll();
			// Adds indegree 0 things to returnList
			returnList.add(data.getData());

			// Iterates through subtracting indegrees
			for (Edge edge : data.getEdges()) {
				Type destination = edge.getDestination().getData();
				int newInDegree = inDegree.get(destination) - 1;
				inDegree.put(destination, newInDegree);
				if (newInDegree == 0) {
					searchQueue.add(graphMap.get(destination));
				}
			}
		}

		return returnList;
	}

	/**
	 * A class that represents directed edges in between vertices Holds the
	 * destination data
	 */
	private class Edge {
		private Vertex destination;

		/**
		 * Creates the edge class to connect vertices
		 * 
		 * @param destination - The vertex the edge is pointing at
		 */
		public Edge(Vertex destination) {
			this.destination = destination;
		}

		/**
		 * Getter for destination data
		 * 
		 * @return destination Vertex
		 */
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

		/**
		 * Creates a Vertex class to store data with outgoing edges
		 * 
		 * @param data - The data held inside the vertex
		 */
		public Vertex(Type data) {
			this.data = data;
			this.outgoingEdges = new LinkedList<Edge>();

		}

		/**
		 * Adds an edge to the Vertex
		 * 
		 * @param destination - The destination of the new outgoing edge
		 */
		public void addEdge(Vertex destination) {
			Edge newEdge = new Edge(destination);
			outgoingEdges.add(newEdge);
		}

		/**
		 * Getter method for data
		 * 
		 * @return - data inside the vertex
		 */
		public Type getData() {
			return this.data;
		}

		/**
		 * Getter method for outgoing edges
		 * 
		 * @return - List of outgoing edges
		 */
		public List<Edge> getEdges() {
			return this.outgoingEdges;
		}

	}
}
