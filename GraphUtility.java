package assign07;

import java.util.List;

/**
 * This class uses graphs to do basic utilities
 * 
 * @author Cameron McKay and Daler Turyssov
 * @version 2025-06-22
 */
public class GraphUtility<Type> {

	/**
	 * Checks if two vertices are connected in a graph
	 * 
	 * @param <Type>       - The type of objects in the graph
	 * @param sources      - The sources of edges
	 * @param destinations - The destinations of edges
	 * @param srcData      - Starting point of search
	 * @param dstData      - Target of search
	 * @return boolean true if connected false if not
	 */
	public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) {

		Graph<Type> graph = buildGraph(sources, destinations);

		return graph.depthFirstSearch(srcData, dstData);
	}

	/**
	 * Finds the shortest path between two vertices in a graph
	 * 
	 * @param <Type>       - The type of objects in the graph
	 * @param sources      - The sources of edges
	 * @param destinations - The destinations of edges
	 * @param srcData      - Starting point of search
	 * @param dstData      - Target of search
	 * @return list of the smallest path between two vertices
	 */
	public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData,
			Type dstData) {

		Graph<Type> graph = buildGraph(sources, destinations);

		List<Type> returnList = graph.breadthFirstSearch(srcData, dstData);

		// Path doesnt exist
		if (returnList.size() == 0) {
			throw new IllegalArgumentException("Path doesnt exist!");
		}

		return returnList;
	}

	/**
	 * Sorts a graph topologically
	 * 
	 * @param <Type>       - The type of objects in the graph
	 * @param sources      - The sources of edges
	 * @param destinations - The destinations of edges
	 * @return List of all data sorted topologically
	 */
	public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) {

		Graph<Type> graph = buildGraph(sources, destinations);

		List<Type> returnList = graph.topoSort();

		// Checks for cycles
		if (returnList.size() != graph.getGraphSize()) {
			throw new IllegalArgumentException("Contains a cycle!");
		}

		return returnList;
	}

	/**
	 * Builds a graph object by putting edges between vertices from sources and
	 * destinations
	 * 
	 * @param <Type>       - The type of objects in the graph
	 * @param sources      - The sources of edges
	 * @param destinations - The destinations of edges
	 * @return Graph with vertices for each source and edges between destinations
	 */
	public static <Type> Graph<Type> buildGraph(List<Type> sources, List<Type> destinations) {

		// Checks uneven sources and destinations
		if (!(sources.size() == destinations.size())) {
			throw new IllegalArgumentException("Different sources than destinations");
		}

		Graph<Type> buildGraph = new Graph<>();

		for (int i = 0; i < sources.size(); i++) {
			buildGraph.addEdge(sources.get(i), destinations.get(i));
		}

		return buildGraph;
	}

}
