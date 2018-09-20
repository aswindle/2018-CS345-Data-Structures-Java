/**
 * @File: EulerGraph.java
 *
 * @Author: Alex Swindle
 * @Email: aswindle@email.arizona.edu
 *
 * @Date: Apr 23, 2018
 */

/**
 * REFERENCES:
 * 
 * For Euler circuits working on any graph with all vertices having matching
 * in/out degrees:
 * 
 * https://www.math.upenn.edu/~mlazar/math170/notes05-6.pdf
 * 
 * Discussion of Hierholzer's algorithm:
 * 
 * http://stones333.blogspot.com/2013/11/find-eulerian-path-in-directed-graph.html
 * 
 * Heart of Hierholzer's algorithm:
 * 
 * https://www.geeksforgeeks.org/hierholzers-algorithm-directed-graph/
 *
 */
import java.util.Stack;

public class EulerGraph {
	// Lists of edges and vertices in the graph
	protected SwindleList<Edge> E;
	protected SwindleList<Vertex> V;

	/**
	 * Constructor. Initializes lists of edges and vertices to be empty
	 */
	public EulerGraph() {
		E = new SwindleList<Edge>();
		V = new SwindleList<Vertex>();
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
	 * Prints all of the current vertices, followed by all current edge
	 * connections
	 * 
	 * @author Alex Swindle
	 */
	public void printEulerGraph() {
		System.out.println("Current graph state:");
		System.out.println("Vertices:");
		for (int i = 0; i < V.size(); i++) {
			System.out.println(V.get(i).name);
		}
		System.out.println("Edges:");
		for (int i = 0; i < E.size(); i++) {
			Edge e = E.get(i);
			System.out.printf("%s -> %s\n", e.start.name, e.stop.name);
		}
	}

	/**
	 * Prints a numbered list of current vertices in the form "0: A\n1: B" etc
	 * 
	 * @author Alex Swindle
	 */
	public void printVertexChoices() {
		for (int i = 0; i < V.size(); i++) {
			System.out.printf("%d: %s\n", i, V.get(i).name);
		}
	}

	/**
	 * 
	 * @return whether a graph has an Eulerian cycle
	 * @author Alex Swindle
	 */
	public boolean isEulerian() {
		boolean result = true;
		// Graphs have Eulerian cycles iff each vertex's in and out degree is
		// the same
		for (int i = 0; i < V.size(); i++) {
			Vertex v = V.get(i);
			if (v.in.size() != v.out.size()) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * Checks to see if a vertex has any unvisited edges
	 * 
	 * @param v:
	 *            vertex to check
	 * @return if v has unvisited edges
	 * @author Alex Swindle
	 */
	public boolean hasUnvisited(Vertex v) {
		boolean result = false;
		for (int i = 0; i < v.out.size(); i++) {
			Edge e = v.out.get(i);
			if (e.type.equals("None")) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Gets an arbitrary unvisited outgoing edge from a given vertex
	 * 
	 * @param v:
	 *            vertex to check
	 * @return an unvisted edge from v. Will be the last unvisited edge in v's
	 *         outgoing list
	 * @author Alex Swindle
	 */
	public Edge getUnvisited(Vertex v) {
		Edge result = null;
		if (hasUnvisited(v)) {
			for (int i = 0; i < v.out.size(); i++) {
				Edge e = v.out.get(i);
				if (e.type.equals("None")) {
					result = e;
				}
			}
		}
		return result;
	}

	/**
	 * Prints an Eulerian tour (if possible), beginning at the first vertex
	 * added to the graph
	 * 
	 * Uses Hierholzer's algorithm, as described at
	 * 
	 * https://www.geeksforgeeks.org/hierholzers-algorithm-directed-graph/
	 * 
	 * but using my structures and classes
	 * 
	 * @author Alex Swindle
	 */
	public void Euler() {
		resetEdges();
		// Only works if all vertices have matching in/out degrees
		if (isEulerian()) {
			// Store the path in reversed order in a new list
			SwindleList<String> path = new SwindleList<String>();
			// Stack to store vertices, starts with first vertex
			Stack<Vertex> vstack = new Stack<Vertex>();
			vstack.push(V.get(0));
			// Start looking at the first vertex
			Vertex cur = V.get(0);

			// Continues until no vertices are left in the stack, meaning no
			// vertex has unvisited edges
			while (!vstack.isEmpty()) {
				// If cur has unvisited edges, it needs to go back in the stack
				// and build a new cycle from its unvisited edges
				if (hasUnvisited(cur)) {
					vstack.push(cur);
					// Get an unvisited edge and its corresponding endpoint
					Edge e = getUnvisited(cur);
					cur = e.stop;
					// Mark that edge visited
					e.type = "Visited";
				}
				// If cur doesn't have any more unvisited outgoing edges, it's the end of
				// the road for that part of the cycle; add it to the path, and
				// pop the next element
				else {
					path.add(cur.name);
					cur = vstack.pop();
				}
			}
			
			// Print reversed path
			String realPath = "";
			for (int i = 1; i < path.size() + 1; i++) {
				realPath += path.get(path.size() - i) + "->";
			}
			// Remove the last, unnecessary '->'
			realPath = realPath.substring(0, realPath.length() - 2);
			// Print the result
			System.out.println("Path: " + realPath);
		}
		else {
			System.out.println("No Eulerian tour is possible.");
		}
	}

}
