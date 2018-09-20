

/**
 * @File: GraphClient.java
 *
 *        Create and perform operations on a Graph object by creating and
 *        connecting Vertex objects
 * 
 * @Author: Alex Swindle
 * @Email: aswindle@email.arizona.edu
 *
 * @Date: Apr 9, 2018
 */

public class GraphClient {
	public static void main(String[] args) {
		/*
		 * BUILD THE GRAPH
		 * 
		 * Methods to build it:
		 * 
		 * addVertex(vertex)
		 * removeVertex(vertex)
		 * connect(vertex start, vertex stop, weight): directed edge
		 * biconnect(vertex a, vertex b, weight): directed edges in both directions
		 * disconnect(edge) (difficult to get to particular edge)
		 */
		Graph G = new Graph();

		// Create vertices, add them to the graph
		Vertex a = new Vertex("A");
		G.addVertex(a);
		Vertex b = new Vertex("B");
		G.addVertex(b);
		Vertex c = new Vertex("C");
		G.addVertex(c);
		Vertex d = new Vertex("D");
		G.addVertex(d);
		Vertex e = new Vertex("E");
		G.addVertex(e);
//		Vertex f = new Vertex("F");
//		G.addVertex(f);
//		Vertex g = new Vertex("G");
//		G.addVertex(g);
		
		// Connect the vertices, giving each connection a weight
		G.connect(a, b, 2);
		G.connect(a, d, 8);
		G.connect(b, c, 3);
		G.connect(c, d, 1);
		G.connect(b, e, 4);
		G.connect(d, e, 5);
		G.connect(e, a, 10);

		/*
		 * CHECK THE GRAPH
		 * 
		 * Methods to use:
		 * 
		 * printEdges()
		 * printVertices()
		 * DFS(start)
		 * BFS(start)
		 * printDijkstra(start)
		 */

		G.printDijkstra(a);
		G.printDijkstra(e);
		G.BFS(b);
		G.DFS(a);
		
	}
}
