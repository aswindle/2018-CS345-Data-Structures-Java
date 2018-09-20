
/**
 * @File: EulerClient.java
 *
 * @Author: Alex Swindle
 * @Email: aswindle@email.arizona.edu
 *
 * @Date: Apr 23, 2018
 */
import java.util.Scanner;

public class EulerClient {

	// Menu prompt
	private static String MENU = "Make a selection:\n1: Add a Vertex\n2: Add an Edge\n3: Display the Graph\n"
			+ "4: Find an Eulerian Tour\n5: Exit\n";

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);

		EulerGraph G = new EulerGraph();

		// Main menu loop; goes until option 5, exit, is chosen
		boolean exit = false;
		while (!exit) {
			System.out.print(MENU);
			String attempt = kb.next();
			// Keep prompting until an int from 1 to 5 is chosen
			while (!isValid(attempt, 1, 5)) {
				System.out.print(MENU);
				attempt = kb.next();
			}
			// Execute chosen menu item
			int action = Integer.parseInt(attempt);
			switch (action) {

			/*
			 * **********
			 * 
			 * ADD VERTEX
			 * 
			 * **********
			 */
			case 1:
				System.out.print("Enter a name for the vertex: ");
				String newVert = kb.next();

				// Check to see if a vertex with that name already exists
				boolean vertexPresent = false;
				for (int i = 0; i < G.V.size(); i++) {
					Vertex v = G.V.get(i);
					if (v.name.equals(newVert)) {
						vertexPresent = true;
						System.out.println("Error: a vertex with that name already exists.");
					}
				}
				if (!vertexPresent) {
					G.addVertex(new Vertex(newVert));
				}
				break;

			/*
			 *********
			 *
			 * ADD EDGE
			 *
			 ********* 
			 */
			case 2:
				// Get the starting vertex
				System.out.println("Start vertex:");
				G.printVertexChoices();
				attempt = kb.next();
				while (!isValid(attempt, 0, G.V.size() - 1)) {
					System.out.print("Start vertex: ");
					G.printVertexChoices();
					attempt = kb.next();
				}
				int startIndex = Integer.parseInt(attempt);
				Vertex start = G.V.get(startIndex);

				// Get the ending vertex
				System.out.println("Stop vertex (can't be the same as start):");
				G.printVertexChoices();
				attempt = kb.next();
				// Invalid if not in range or if it's the same as start
				while (!isValid(attempt, 0, G.V.size() - 1) || Integer.parseInt(attempt) == startIndex) {
					System.out.println("Stop vertex (can't be the same as start):");
					G.printVertexChoices();
					attempt = kb.next();
				}
				int stopIndex = Integer.parseInt(attempt);
				Vertex stop = G.V.get(stopIndex);

				// Check to see if start and stop already connect
				boolean alreadyConnected = false;
				for (int i = 0; i < start.out.size(); i++) {
					Edge e = start.out.get(i);
					if (e.stop == stop) {
						alreadyConnected = true;
						System.out.println("Error: These 2 vertices are already connected.");
					}
				}

				// If they don't, connect them
				if (!alreadyConnected) {
					// The weight of the edges doesn't actually matter for
					// Eulerian tours, so we'll default to a weight of 0
					G.connect(start, stop, 0);

				}
				break;

			/*
			 * ***********
			 * 
			 * PRINT GRAPH
			 * 
			 * ***********
			 */
			case 3:
				G.printEulerGraph();
				break;

			/*
			 * ******************
			 * 
			 * FIND EULERIAN TOUR
			 * 
			 * ******************
			 */
			case 4:
				G.Euler();
				break;
			/*
			 * ****
			 * 
			 * EXIT
			 * 
			 * ****
			 */
			case 5:
				System.out.println("Exiting");
				exit = true;
				break;
			}
		}
		kb.close();
		System.exit(0);
	}

	/**
	 * Checks to see if a string can be converted to int between a lower and
	 * upper bound; reused from CSCV 335
	 * 
	 * @param attempt:
	 *            String to try to parse as an integer
	 * @param lowerBound:
	 *            smallest acceptable int
	 * @param upperBound:
	 *            largest acceptable int
	 * @return true if valid, false if not
	 */
	private static boolean isValid(String attempt, int lowerBound, int upperBound) {
		boolean valid = false;
		// Try to parse attempt as an int; if possible, check if it's between
		// lower and upper bounds
		try {
			int action = Integer.parseInt(attempt);
			if (action >= lowerBound && action <= upperBound) {
				valid = true;
			}
			else {
				System.out.println("Error: choice must be between  " + lowerBound + " and " + upperBound);
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Error: enter an integer between " + lowerBound + " and " + upperBound);
		}
		return valid;
	}
}
