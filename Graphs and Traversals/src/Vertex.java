
/**
 * @File: Vertex.java
 * 
 *        Vertex of a graph
 *
 * @Author: Alex Swindle
 * @Email: aswindle@email.arizona.edu
 *
 * @Date: Apr 9, 2018
 */

public class Vertex {
	// Lists of edges attached to this vertex
	protected SwindleList<Edge> out;
	protected SwindleList<Edge> in;
	// Name of the vertex
	protected String name;
	// Flag for whether this vertex has been visited; used for DFS
	protected boolean visited;
	// Distance used in Dijkstra's algorithm
	protected int DDist;
	// How far away from a start vertex; used in BFS
	protected int level;
	// String to hold shortest path in Dijkstra's algorithm
	// Reference:
	// https://tech.io/playgrounds/1608/shortest-paths-with-dijkstras-algorithm/keeping-track-of-paths
	protected String path;

	/**
	 * Constructor. Each vertex will be named with a String
	 * 
	 * @param name
	 */
	public Vertex(String name) {
		this.name = name;
		in = new SwindleList<Edge>();
		out = new SwindleList<Edge>();
		visited = false;
		DDist = 0;
		path = "";
	}

	/**
	 * Add an incoming edge to the list of edges. Will be invoked by the Graph
	 * class
	 * 
	 * @param e
	 * @author Alex Swindle
	 */
	public void addInEdge(Edge e) {
		in.add(e);
	}

	/**
	 * Add an outgoing edge to the list of edges
	 * 
	 * @param e
	 * @author Alex Swindle
	 */
	public void addOutEdge(Edge e) {
		out.add(e);
	}

	/**
	 * Remove an incoming edge from the list of edges. Will be invoked by the
	 * Graph class
	 * 
	 * @param e
	 * @author Alex Swindle
	 */
	public void removeInEdge(Edge e) {
		in.remove(e);
	}

	/**
	 * Remove an outgoing edge from the list of edges
	 * 
	 * @param e
	 * @author Alex Swindle
	 */
	public void removeOutEdge(Edge e) {
		out.remove(e);
	}

	/**
	 * Returns whether a vertex is adjacent to this one
	 * 
	 * @param b:
	 *            vertex to check adjacency for
	 * @return if b has an edge with this vertex
	 * @author Alex Swindle
	 */
	public boolean isAdjacent(Vertex b) {
		boolean result = false;
		for (int i = 0; i < in.size(); i++) {
			Edge e = in.get(i);
			if (e.start == b || e.stop == b) {
				result = true;
			}
		}
		for (int i = 0; i < out.size(); i++) {
			Edge e = out.get(i);
			if (e.start == b || e.stop == b) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * String representation of a vertex, which gives information on which other
	 * vertices it connects to
	 */
	public String toString() {
		String retval = "Vertex " + this.name + " connects to: ";
		for (int i = 0; i < out.size(); i++) {
			Edge e = out.get(i);
			retval += e.opposite(this).name + ";";
		}
		return retval;
	}
}
