

/**
 * @File: Edge.java
 * 
 *        Edge of a graph
 *
 * @Author: Alex Swindle
 * @Email: aswindle@email.arizona.edu
 *
 * @Date: Apr 9, 2018
 */

public class Edge {
	// Instance variables: endpoint Vertices and weight of path
	protected Vertex start;
	protected Vertex stop;
	protected int weight;

	// Will be used in BFS and DFS; initially None; later Discovery, Back or
	// Cross
	protected String type;

	/**
	 * Constructor
	 * 
	 * @param start:
	 *            endpoint #1
	 * @param stop:
	 *            endpoint #2
	 * @param weight:
	 *            length of path
	 */
	public Edge(Vertex start, Vertex stop, int weight) {
		this.start = start;
		this.stop = stop;
		this.weight = weight;
		this.type = "None";
	}

	/**
	 * Return the vertex across from the input vertex
	 * 
	 * @param across:
	 *            vertex at one end of this edge
	 * @return: vertex at other end of this edge
	 * @author Alex Swindle
	 */
	public Vertex opposite(Vertex across) {
		if (this.start == across) {
			return stop;
		}
		else {
			return start;
		}
	}

	/**
	 * String representation. Gives the endpoints, weight, and type of this edge
	 */
	public String toString() {
		return String.format("The edge connecting %s and %s: Weight %d, Type %s", start.name, stop.name, weight, type);
	}
}
