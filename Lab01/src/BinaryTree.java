/**
 * Program: BinaryTree.java
 * 
 * Purpose: Creates a BinaryTree of Strings
 * 
 * @author Alex Swindle, aswindle@email.arizona.edu
 * 
 *         This code was built by referencing a BinarySearchTree I implemented
 *         in Python for CSC120.
 * 
 *         I referenced
 *         http://www.algolist.net/Data_structures/Binary_search_tree/Removal
 *         for help in designing my removeNode method, though the code is all my
 *         own
 */

public class BinaryTree {
	// Private helper class for nodes of tree
	private class Node {
		private String value;
		private Node left;
		private Node right;
		private int count;

		private Node(String val) {
			this.value = val;
			this.left = null;
			this.right = null;
			count = 1;
		}
	}

	private Node root;

	/**
	 * Constructor
	 */
	public BinaryTree() {
		root = null;
	}

	/**
	 * @return whether the tree is empty or not
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Add a new element to the tree
	 * 
	 * @param word
	 *            to add to the tree
	 */
	public void add(String word) {
		root = addRoot(this.root, word);
	}

	/*
	 * Private helper method to add to the tree
	 */
	private Node addRoot(Node root, String newVal) throws IllegalArgumentException {
		Node result;
		if (root == null) {
			result = new Node(newVal);
		}
		else {
			result = root;
			// If the value is already in the tree, incrase its count
			if (root.value.equals(newVal)) {
				root.count++;
			}
			else if (newVal.compareTo(root.value) < 0) {
				root.left = addRoot(root.left, newVal);
			}
			else {
				root.right = addRoot(root.right, newVal);
			}
		}
		return result;
	}

	/**
	 * Prints an inorder traversal of the tree
	 */
	public void printTree() {
		System.out.println("Current words: " + printTreeRoot(root));
	}

	/*
	 * Private helper method for printing inorder traversal
	 */
	private String printTreeRoot(Node root) {
		String result = "";
		if (root == null) {
			result = "";
		}
		else {
			result = printTreeRoot(root.left);
			// Return 'count' copies of the current node; only needed for
			// repeats
			for (int i = 0; i < root.count; i++) {
				result += root.value + " ";
			}
			result += printTreeRoot(root.right);
		}
		return result;
	}

	/**
	 * @param word
	 *            to search for
	 * @return whether that word is in the tree or not
	 */
	public boolean search(String word) {
		return (getNode(root, word) != null);
	}

	/*
	 * Private helper method for searching the tree
	 */
	private Node getNode(Node root, String word) {
		Node result;
		// word isn't in tree
		if (root == null) {
			result = null;
		}
		// word is in tree, at current node
		else if (root.value.equals(word)) {
			result = root;
		}
		// word < current node
		else if (word.compareTo(root.value) < 0) {
			result = getNode(root.left, word);
		}
		// word > current node
		else {
			result = getNode(root.right, word);
		}
		return result;
	}

	/*
	 * Private helper method; returns the parent of the node containing word
	 */
	private Node getParent(String word) {
		Node targetChild = getNode(root, word);
		Node current = this.root;
		// While the current node isn't the parent of the target, move the right
		// direction towards the child
		while (current.left != targetChild && current.right != targetChild) {
			if (word.compareTo(current.value) < 0) {
				current = current.left;
			}
			else {
				current = current.right;
			}
		}
		return current;
	}

	/**
	 * @param word
	 *            to remove
	 * @return true if removed, false if not
	 */
	public boolean remove(String word) {
		return removeNode(root, word);
	}

	/*
	 * Private helper method to remove a node
	 */
	private boolean removeNode(Node root, String word) {
		boolean result;
		Node target = getNode(root, word);
		if (target == null) {
			result = false;
		}
		else {
			result = true;
			/*
			 * 4 cases
			 * 
			 * 1. Node has a count greater than 1, in which case just decrement
			 * count by 1
			 * 
			 * 2. Node is a leaf, in which case just set its parent pointer to
			 * null
			 * 
			 * 3. Node has one child, in which case make parent point to that
			 * child
			 * 
			 * 4. Node has two children, so find the minimum of right subtree,
			 * swap the node value with that minimum, and then run remove again
			 * on the right subtree
			 */

			// Case 1
			if(target.count > 1) {
				target.count--;
			}
			// Case 2
			else if (target.left == null && target.right == null) {
				// Special case: removing the last item in the tree
				if (target == this.root) {
					this.root = null;
				}
				else {
					Node parent = getParent(word);
					if (parent.left == target) {
						parent.left = null;
					}
					else {
						parent.right = null;
					}
				}
			}
			// Case 3
			// Only a left child
			else if (target.left != null && target.right == null) {
				// Special case: removing root, so root is now left child
				if (target == this.root) {
					this.root = this.root.left;
				}
				else {
					Node parent = getParent(word);
					if (parent.left == target) {
						parent.left = target.left;
					}
					else {
						parent.right = target.left;
					}
				}
			}
			// Only a right child
			else if (target.left == null && target.right != null) {
				// Special case: removing root, so root is now right child
				if (target == this.root) {
					this.root = this.root.right;
				}
				else {
					Node parent = getParent(word);
					if (parent.left == target) {
						parent.left = target.right;
					}
					else {
						parent.right = target.right;
					}
				}
			}
			// Case 4
			else {
				// Get the minimum of the right subtree
				Node rightMin = target.right;
				while (rightMin.left != null) {
					rightMin = rightMin.left;
				}
				// Swap the target and minimum Strings
				String temp = target.value;
				target.value = rightMin.value;
				rightMin.value = temp;
				// Remove the word again from right subtree, which is now either
				// case 1 or case 2
				removeNode(target.right, word);
			}
		}
		return result;
	}
}
