/**
 * @File: Graph.java
 * 
 *        Implements an adjacency list Graph, along with DFS, BFS, and
 *        Dijkstra's algorithm
 *
 * @Author: Alex Swindle
 * @Email: aswindle@email.arizona.edu
 *
 * @Date: Apr 9, 2018
 */

public class Graph {
	// Constant used as 'positive infinity' for Dijkstra's algorithm
	protected int inf = Integer.MAX_VALUE;
	// Counter for how many vertices are visited in a search. Used for finding
	// unconnected graphs
	protected int verticesVisited;

	// Lists of edges and vertices in the graph
	protected SwindleList<Edge> E;
	protected SwindleList<Vertex> V;

	/**
	 * Constructor. Initializes lists of edges and vertices to be empty
	 */
	public Graph() {
		E = new SwindleList<Edge>();
		V = new SwindleList<Vertex>();
		verticesVisited = 0;
	}

	/**
	 * Add a vertex to the graph
	 * 
	 * @param v:
	 *            Vertex object to add
	 * @author Alex Swindle
	 */
	public void addVertex(Vertex v) {
		V.add(v);
	}

	/**
	 * Remove a vertex and all of its associated edges from the graph
	 * 
	 * @param v:
	 *            vertex to remove
	 * @author Alex Swindle
	 */
	public void removeVertex(Vertex v) {
		// Remove each of v's edges from the graph's list of edges
		for (int i = 0; i < v.out.size(); i++) {
			Edge e = v.out.get(i);
			E.remove(e);
		}
		// Remove the vertex from the list of vertices
		V.remove(v);
	}

	/**
	 * Connect 2 vertices with a new DIRECTED edge
	 * 
	 * @param start:
	 *            source vertex
	 * @param stop:
	 *            destination vertex
	 * @param weight:
	 *            weight (distance) of the edge
	 * @author Alex Swindle
	 */
	public void connect(Vertex start, Vertex stop, int weight) {
		Edge newEdge = new Edge(start, stop, weight);
		start.addOutEdge(newEdge);
		stop.addInEdge(newEdge);
		E.add(newEdge);
	}

	/**
	 * Connect 2 vertices in both directions
	 * 
	 * @param a
	 *            endpoint vertex 1
	 * @param b
	 *            endpiont vertex 2
	 * @param weight
	 *            weight of edge
	 * @author Alex Swindle
	 */
	public void biconnect(Vertex a, Vertex b, int weight) {
		connect(a, b, weight);
		connect(b, a, weight);
	}

	/**
	 * Remove an edge from the graph
	 * 
	 * @param e:
	 *            edge to remove
	 * @author Alex Swindle
	 */
	public void disconnect(Edge e) {
		// Remove the edge object from the start and stop vertices' list of
		// edges
		e.start.removeOutEdge(e);
		e.stop.removeInEdge(e);
		// Remove from the graph itself

		E.remove(e);
	}

	/**
	 * Reset the visited flag of every vertex to false and the Dijkstra distance
	 * to 0
	 * 
	 * @author Alex Swindle
	 */
	public void resetVertices() {
		for (int i = 0; i < V.size(); i++) {
			Vertex v = V.get(i);
			v.visited = false;
			v.DDist = 0;
			v.level = 0;
			v.path = "";
		}
	}

	/**
	 * Reset the 'type' variable of every edge back to None
	 * 
	 * @author Alex Swindle
	 */
	public void resetEdges() {
		for (int i = 0; i < E.size(); i++) {
			Edge e = E.get(i);
			e.type = "None";
		}
	}

	/**
	 * Resets all edges to "None", all vertices to unvisited, count of visited
	 * vertices to 0
	 * 
	 * @author Alex Swindle
	 */
	public void resetAll() {
		verticesVisited = 0;
		resetVertices();
		resetEdges();
	}

	/**
	 * Performs a DFS and prints results
	 * 
	 * @param start
	 * @author Alex Swindle
	 */
	public void DFS(Vertex start) {
		resetAll();
		DFSrecurse(start);
		if (verticesVisited != V.size()) {
			System.out.println("DFS error: Not all vertices visited");
		}
		else {
			System.out.printf("DFS starting at vertex %s:\n", start.name);
			printEdges();
		}
	}

	/**
	 * Helper method that is the heart of DFS. Labels each edge as a "Back" or
	 * "Discovery" edge. Based on the book's implementation.
	 * 
	 * @param start
	 * @author Alex Swindle
	 */
	public void DFSrecurse(Vertex start) {
		start.visited = true;
		verticesVisited++;
		for (int i = 0; i < start.out.size(); i++) {
			Edge e = start.out.get(i);
			if (e.type.equals("None")) {
				Vertex end = e.stop;
				if (end.visited == false) {
					e.type = "Discovery";
					DFSrecurse(end);
				}
				else {
					e.type = "Back";
				}
			}
		}
	}

	/**
	 * Perform a breadth-first search starting at a certain vertex. All edges
	 * will be labeled with number of links away from start
	 * 
	 * @param start
	 * @author Alex Swindle
	 */
	public void BFS(Vertex start) {
		resetAll();
		start.visited = true;
		verticesVisited++;
		SwindleList<Vertex> queue = new SwindleList<Vertex>();
		queue.add(start);
		while (!queue.isEmpty()) {
			Vertex cur = queue.pop();
			// Loop through cur's adjacent vertices. Unvisited ones are
			// discovered and added to the queue; visited ones are cross edges
			// and ignored
			for (int i = 0; i < cur.out.size(); i++) {
				Edge e = cur.out.get(i);
				// Only deal with edges that weren't already classified
				if (e.type.equals("None")) {
					Vertex v = e.stop;
					// Previously visited: cross edge
					if (v.visited) {
						e.type = "Cross";
					}
					// Unvisited: add to queue, discovery edge found
					else {
						v.level = cur.level + 1;
						v.visited = true;
						verticesVisited++;
						queue.add(v);
						e.type = "Discovery Level " + v.level;
					}
				}
			}
		}
		System.out.printf("BFS starting at vertex %s:\n", start.name);
		if (verticesVisited != V.size()) {
			System.out.println("BFS Error: Not all vertices visited");
		}
		else {
			printEdges();
		}
	}

	/**
	 * Print current status of all edges
	 * 
	 * @author Alex Swindle
	 */
	public void printEdges() {
		for (int i = 0; i < E.size(); i++) {
			Edge e = E.get(i);
			System.out.println(e);
		}
	}

	/**
	 * Print current connections of all vertices
	 * 
	 * @author Alex Swindle
	 */
	public void printVertices() {
		for (int i = 0; i < V.size(); i++) {
			Vertex v = V.get(i);
			System.out.println(v);
		}
	}

	/**
	 * Use Dijkstra's algorithm to find the shortest path from a given vertex to
	 * every other vertex in the graph.
	 * 
	 * Based on the textbook's implementation, but uses a SwindleList instead of
	 * a PriorityQueue
	 * 
	 * @param start:
	 *            vertex to use as starting point of Dijkstra's algorithm
	 * @return: whether the algorithm was able to run (only false if negative
	 *          edges present)
	 * @author Alex Swindle
	 */
	public boolean Dijkstra(Vertex start) {
		// Check whether algorithm can be performed; invalid if any negative
		// edges
		boolean valid = true;
		for (int i = 0; i < E.size(); i++) {
			Edge e = E.get(i);
			if (e.weight < 0) {
				valid = false;
				System.out.println("Error: Dijkstra's algorithm only works on positive-weighted edges.");
			}
		}
		if (valid) {
			// Put all of the vertices in G into the queue to process
			SwindleList<Vertex> queue = new SwindleList<Vertex>();

			// Initialize all distances. 0 for start vertex, infinity for all
			// others
			for (int i = 0; i < V.size(); i++) {
				Vertex v = V.get(i);
				if (v != start) {
					v.DDist = inf;
				}
				else {
					start.DDist = 0;
					start.path = start.name;
				}
				queue.add(v);
			}

			// Heart of Dijkstra's algorithm: remove closest remaining vertex
			// from queue and possibly update the distance to its adjacent
			// vertices
			while (!queue.isEmpty()) {
				// Get the vertex with the minimum DDist in the queue
				Vertex min = (Vertex) queue.get(0);
				for (int i = 0; i < queue.size(); i++) {
					Vertex v = queue.get(i);
					if (v.DDist < min.DDist) {
						min = v;
					}
				}
				queue.remove(min);

				// Potentially update distances for all vertices adjacent to min
				// that are still in the queue
				for (int i = 0; i < min.out.size(); i++) {
					Edge e = min.out.get(i);
					// Get each adjacent vertex using min's list of edges
					Vertex z = e.stop;

					// Check for a shorter path to z that goes through min
					// Idea for path:
					// https://tech.io/playgrounds/1608/shortest-paths-with-dijkstras-algorithm/keeping-track-of-paths
					if (queue.contains(z)) {
						if (min.DDist + e.weight < z.DDist) {
							// Update the distance and the path
							z.DDist = min.DDist + e.weight;
							z.path = min.path + "-" + z.name;
						}
					}

				}
			}
		}
		return valid;
	}

	/**
	 * Use Dijkstra's algorithm with a given starting vertex; print results for
	 * every vertex afterwards
	 * 
	 * @param start
	 * @author Alex Swindle
	 */
	public void printDijkstra(Vertex start) {
		resetAll();
		boolean done = Dijkstra(start);
		if (done) {
			System.out.println("Shortest path from vertex " + start.name + " to:");
			for (int i = 0; i < V.size(); i++) {
				Vertex v = V.get(i);
				String dist = "" + v.DDist;
				if (v.DDist == inf) {
					dist = "Infinite (unconnected)";
				}
				System.out.println(String.format("%s: %s, Path: %s", v.name, dist, v.path));
			}
		}
	}
}