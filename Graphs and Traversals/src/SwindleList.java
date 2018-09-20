/**
 * @File: SwindleList.java
 *
 *        Creates a simple implementation of a doubly-linked list with only the
 *        methods required for Graph objects
 * 
 * @Author: Alex Swindle
 * @Email: aswindle@email.arizona.edu
 *
 * @Date: Apr 10, 2018
 */

/*
 * I referenced https://docs.oracle.com/javase/tutorial/java/generics/types.html
 * for how to create a class with a generic type
 */
public class SwindleList<T> {
	private class Node {
		T data;
		Node next;
		Node prev;

		private Node(T o) {
			data = o;
			next = null;
			prev = null;
		}
	}

	// Instance variables for list: front, back, current size
	private Node front;
	private Node back;
	private int size;

	/**
	 * Constructor. Creates an empty list
	 */
	public SwindleList() {
		front = null;
		back = null;
		size = 0;
	}

	/**
	 * @return current size of list
	 * @author Alex Swindle
	 */
	public int size() {
		return size;
	}

	/**
	 * Get the Object at a particular index
	 * 
	 * @param index
	 * @return Object at index
	 * @author Alex Swindle
	 */
	public T get(int index) {
		int i = 0;
		Node cur = front;
		while (i != index) {
			cur = cur.next;
			i++;
		}
		return cur.data;
	}

	/**
	 * Add an object to the end of the list
	 * 
	 * @param o:
	 *            object to add
	 * @author Alex Swindle
	 */
	public void add(T o) {
		Node newNode = new Node(o);
		// Empty list: insert at front
		if (front == null) {
			front = newNode;
			back = newNode;
		}
		// All other lists: insert at back
		else {
			back.next = newNode;
			newNode.prev = back;
			back = newNode;
		}
		size++;
	}

	/**
	 * Remove an object from the list
	 * 
	 * @param o:
	 *            object to remove
	 * @author Alex Swindle
	 */
	public void remove(T o) {
		Node cur = front;
		while (cur != null) {
			// Found data at current location
			if (cur.data == o) {
				// Special case: remove front of list
				if (cur == front) {
					front = cur.next;
					// If the list is size 1, back also needs to be updated
					if (size == 1) {
						back = null;
					}
					else {
						front.prev = null;
					}
				}
				// Remove from elsewhere in the list
				else {
					cur.prev.next = cur.next;
					if (cur == back) {
						back = back.prev;
					}
					else {
						cur.next.prev = cur.prev;
					}
				}
				// Decrement size of list
				size--;
				return;
			}
			// Didn't find at current location
			else {
				cur = cur.next;
			}
		}
	}

	/**
	 * @return whether the list is empty or not
	 * @author Alex Swindle
	 */
	public boolean isEmpty() {
		return front == null;
	}

	/**
	 * Return whether the list contains a particular object
	 * 
	 * @param o
	 * @return
	 * @author Alex Swindle
	 */
	public boolean contains(T o) {
		boolean found = false;
		Node cur = front;
		for (int i = 0; i < size; i++) {
			if (cur.data == o) {
				found = true;
			}
			cur = cur.next;
		}
		return found;
	}

	/**
	 * @return the T currently at the front of the list
	 * @author Alex Swindle
	 */
	public T pop() {
		T retval = null;
		if (size > 0) {
			retval = get(0);
			remove(retval);
		}
		return retval;
	}

	public String toString() {
		String retval = "[";
		Node cur = front;
		while (cur != null) {
			retval += cur.data.toString() + ", ";
			cur = cur.next;
		}
		if (retval.length() > 1) {
			retval = retval.substring(0, retval.length() - 2);
		}
		retval += "]";
		return retval;
	}
}
