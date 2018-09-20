
/**
 * Program: WordClient.java
 * 
 * Purpose: Menu-based interface for interacting with a binary tree of words
 * 
 * @author Alex Swindle, aswindle@email.arizona.edu
 */

import java.util.Scanner;

public class WordClient {
	static BinaryTree wordTree;
	static String PROMPT = "Enter the number that corresponds to your desired function:\n" + "1 Add a word\n"
			+ "2 Search for a word\n" + "3 Display all words in order\n" + "4 Delete a word\n" + "5 Exit";

	public static void main(String[] args) {
		wordTree = new BinaryTree();
		Scanner kb = new Scanner(System.in);

		// Keep running until the user chooses exit
		boolean finished = false;
		while (!finished) {
			System.out.println(PROMPT);
			String attempt = kb.next();

			// Validate input; loop terminates when an integer between 1 and 5
			// is given
			while (!isValid(attempt)) {
				System.out.println(PROMPT);
				attempt = kb.next();
			}

			// Perform action based on input
			int action = Integer.parseInt(attempt);
			switch (action) {
			// Add
			case 1:
				System.out.print("Word to add: ");
				String addWord = kb.next();
				addWord(addWord);
				break;

			// Search
			case 2:
				System.out.print("Word to search for: ");
				String searchWord = kb.next();
				searchWord(searchWord);
				break;

			// Print
			case 3:
				wordTree.printTree();
				break;

			// Remove
			case 4:
				System.out.print("Word to remove: ");
				String removeWord = kb.next();
				removeWord(removeWord);
				break;

			// Exit
			case 5:
				finished = true;
				System.out.println("Exiting");
				break;
			}
		}
		kb.close();
		System.exit(0);
	}

	/*
	 * Helper method to consolidate code for adding word
	 */
	private static void addWord(String word) {
		try {
			wordTree.add(word);
			System.out.println(word + " was added successfully.");
		}
		catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	/*
	 * Helper method to consolidate code for removing a word
	 */
	private static void removeWord(String word) {
		boolean removed = wordTree.remove(word);
		if (removed) {
			System.out.println(word + " was removed successfully");
		}
		else {
			System.out.println(word + " wasn't removed because it wasn't in the tree.");
		}
	}

	/*
	 * Helper method to consolidate code for searching for a word
	 */
	private static void searchWord(String word) {
		boolean found = wordTree.search(word);
		if (found) {
			System.out.println(word + " was found in the tree");
		}
		else {
			System.out.println(word + " was not found in the tree");
		}
	}

	/*
	 * Helper method that checks if an attempted input is a valid integer from 1
	 * to 5 Returns true if valid, false if not
	 */
	private static boolean isValid(String attempt) {
		boolean valid = false;
		// Try to parse it as an int; if possible, check if it's between 1 and 5
		try {
			int action = Integer.parseInt(attempt);
			if (action >= 1 && action <= 5) {
				valid = true;
			}
			else {
				System.out.println("Error: choice must be between 1 and 5");
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Error: enter an integer between 1 and 5");
		}
		return valid;
	}
}
