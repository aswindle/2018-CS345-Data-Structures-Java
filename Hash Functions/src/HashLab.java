
/**
 * @File: HashLab.java
 * 
 * @Author Alex Swindle
 * @Email aswindle@email.arizona.edu
 * 
 * @Date: Mar 29, 2018
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class HashLab {
	// Private class that will be the elements of the hash table
	// The idea for the state variable came from this paper:
	// https://arxiv.org/ftp/arxiv/papers/0909/0909.2547.pdf
	private static class HashCell {
		/**
		 * Each element of the hash table will have a value and a state
		 * 
		 * States:
		 * 
		 * 0: Empty, never used; 1: Full; 2: Empty, but previously filled
		 */
		private String value;
		private int state;

		private HashCell() {
			value = "";
			state = 0;
		}
	}

	// Menu prompt
	private static String MENU = "Make a selection:\n1: Insert a name\n2: Search for a name\n3: Delete a name\n"
			+ "4: Read from a file\n5: Print the table\n6: Exit\n";
	// Hash table built from HashCells
	private static HashCell[] hashTable = new HashCell[53];

	/**
	 * Main program loop, which implements a menu-based interface for the hash
	 * table
	 * 
	 * @author Alex Swindle
	 */
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		// Create the hash table
		for (int i = 0; i < 53; i++) {
			hashTable[i] = new HashCell();
		}
		// Main menu loop; goes until option 6, exit, is chosen
		boolean exit = false;
		while (!exit) {
			System.out.print(MENU);
			String attempt = kb.next();
			// Keep prompting until an int from 1 to 6 is chosen
			while (!isValid(attempt, 1, 6)) {
				System.out.print(MENU);
				attempt = kb.next();
			}
			// Execute chosen menu item
			int action = Integer.parseInt(attempt);
			switch (action) {
			// File
			case 4:
				System.out.println("Filename must be *.csv, and must be in src/ folder.");
				System.out.print("Filename to parse: ");
				String filename = kb.next();
				parseFile(filename);
				break;
			// Print
			case 5:
				printTable();
				break;
			// Exit
			case 6:
				exit = true;
				System.out.println("Exiting...");
				break;
			// Other 3 require a name
			default:
				System.out.print("Name: ");
				String name = kb.next();
				switch (action) {
				// Insert
				case 1:
					insert(name);
					break;
				// Search
				case 2:
					System.out.printf("%s found: %b\n", name, searchDelete(name, false));
					break;
				// Delete
				case 3:
					System.out.printf("%s deleted: %b\n", name, searchDelete(name, true));
					break;
				}
			}
		}
		kb.close();
		System.exit(0);
	}

	/**
	 * Hash function.
	 * 
	 * @param s:
	 *            string to calculate hash value for
	 * @return hash value for s
	 * @author Alex Swindle
	 */
	/*
	 * I referenced
	 * 
	 * https://www.tutorialspoint.com/java/java_string_hashcode.htm
	 * 
	 * and
	 * 
	 * https://stackoverflow.com/questions/113511/best-implementation-for-
	 * hashcode-method
	 * 
	 * to build a hash function that's modeled on Java's built-in hashCode()
	 * method
	 */
	private static int hashFn(String s) {
		// Turn the string into an integer
		// Each character of the string will be multiplied by a prime a
		// decreasing number of times
		// Index 0 will be multiplied n-1 times, index 1 (n-2) times, etc
		int k = 0;
		for (int i = 0; i < s.length(); i++) {
			char cur = s.charAt(i);
			k = k * 7 + cur;
		}
		// Calculate the hash value modulo table length so everything fits
		int hashVal = k % hashTable.length;
		// Ensure the hash value is positive to avoid negative index errors
		if (hashVal < 0) {
			hashVal = -hashVal;
		}
		return hashVal;
	}

	/**
	 * Insert a string into the hash table
	 * 
	 * @param s:
	 *            string to insert
	 * @author Alex Swindle
	 */
	private static void insert(String s) {
		// Force lower-case to prevent duplicates with different cases
		s = s.toLowerCase();

		// Check if s is already in the table; print message if so
		if (searchDelete(s, false)) {
			System.out.printf("Error: %s is already in the table\n", s);
		}

		// If s isn't already in the table, then attempt to insert it
		else {
			int hashVal = hashFn(s);
			boolean inserted = false;
			int j = 0;
			while (!inserted && j < hashTable.length) {
				// Each pass will look at hashValue + j^2, implementing
				// quadratic
				// probing
				int curIndex = (hashVal + j * j) % hashTable.length;
				// If that space is empty (either never been filled or deleted),
				// insert there
				if (hashTable[curIndex].state == 0 || hashTable[curIndex].state == 2) {
					hashTable[curIndex].state = 1;
					hashTable[curIndex].value = s;
					inserted = true;
				}
				// Otherwise, a collision occurred and we have to loop again
				else {
					System.out.println("Collision occurred at index " + curIndex);
					j++;
				}
			}
			// If we've reached here, the insertion failed
			// Stops after j reaches the size of the table, based on this:
			// https://stackoverflow.com/a/12121364/9397611
			if (!inserted) {
				System.out.println("Inserting " + s + " failed.");
			}
		}
	}

	/**
	 * Search for and optionally delete a string in the hash table
	 * 
	 * @param s:
	 *            string to search for
	 * @param delete:
	 *            whether or not to delete the string once found
	 * @return true if s is present; false otherwise
	 * @author Alex Swindle
	 */
	private static boolean searchDelete(String s, boolean delete) {
		s = s.toLowerCase();
		int hashVal = hashFn(s);
		boolean found = false;
		boolean keepGoing = true;
		int j = 0;
		// Stops after j reaches the size of the table, based on this:
		// https://stackoverflow.com/a/12121364/9397611
		while (keepGoing && j < hashTable.length) {
			// Quadratic probing: add j^2
			int curIndex = (hashVal + j * j) % hashTable.length;
			// If we've reached an empty space that's never been filled, the key
			// isn't found
			if (hashTable[curIndex].state == 0) {
				keepGoing = false;
			}
			else {
				// If the current space is the key, it was found
				if (hashTable[curIndex].value.equals(s)) {
					found = true;
					// Delete the current space if delete is turned on
					if (delete) {
						hashTable[curIndex].value = "";
						hashTable[curIndex].state = 2;
					}
					keepGoing = false;
				}
				// If not, keep checking
				else {
					j++;
				}
			}
		}
		return found;
	}

	/**
	 * Print the hashTable, one index per line
	 * 
	 * @author Alex Swindle
	 */
	public static void printTable() {
		for (int i = 0; i < hashTable.length; i++) {
			System.out.printf("Index %02d: %s\n", i, hashTable[i].value);
		}
	}

	/**
	 * Read names from a CSV file and insert them all into the hash table
	 * 
	 * @param filename
	 * @author Alex Swindle
	 */
	private static void parseFile(String filename) {
		try {
			// Attempt to open the file and parse each line
			File csv = new File("src/" + filename);
			Scanner fileRead = new Scanner(csv);
			while (fileRead.hasNextLine()) {
				// Split the line at each comma, add each name to the table
				// after removing whitespace
				String line = fileRead.nextLine();
				String[] names = line.split(",");
				for (String name : names) {
					insert(name.trim());
				}
			}
			fileRead.close();
		}
		catch (IOException e) {
			System.out.println("Invalid file name.");
		}

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
