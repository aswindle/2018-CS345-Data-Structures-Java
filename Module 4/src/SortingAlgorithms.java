
/**
 * @File: SortingAlgorithms.java
 * 
 * Contains the implementation for Merge, Quick, Selection, and Heap Sort
 * 
 * @Author Alex Swindle
 * @Email aswindle@email.arizona.edu
 * 
 * @Date: Mar 1, 2018
 * @Last modified: 4:24:18 PM
 */
import java.util.Arrays;

public class SortingAlgorithms {
	// Variables that will tally function calls and give a rough estimate of the
	// number of operations performed
	static int operations;
	static int calls;

	/**
	 * Allows quicksort to be called only using the list as a parameter and
	 * ignoring indices
	 * 
	 * @param list
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static void quickSort(int[] list) {
		quicksort(list, 0, list.length - 1);
	}

	/**
	 * Recursive implementation of Quick Sort
	 * 
	 * @param list
	 *            to sort
	 * @param start
	 *            index
	 * @param stop
	 *            index
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static void quicksort(int[] list, int start, int stop) {
		calls++;
		// Only have to do quicksort if the list has more than one element in it
		if (stop - start > 0) {
			// Partition the list; we know index 'pivot' is now correct
			int pivot = partition(list, start, stop);
			// Sort the left half, not including pivot
			quicksort(list, start, pivot - 1);
			// Sort the right half, not including pivot
			quicksort(list, pivot + 1, stop);
			operations += 4;
		}
		// The array will have been sorted in place, so no need to return the
		// array
		operations++;
		return;
	}

	/**
	 * Partition portion of the quicksort algorithm, deterministically choosing
	 * the last element to be the pivot
	 * 
	 * @param list
	 * @param start
	 * @param stop
	 * @return the current position of the pivot
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static int partition(int[] list, int start, int stop) {
		calls++;
		int pValue = list[stop];
		int ppoint = start;
		operations++;
		// Loop through the whole array; if current value belongs left of the
		// pivot, swap it and the current position of the pivot pointer;
		// increment pivot pointer by 1
		for (int i = start; i < stop; i++) {
			if (list[i] <= pValue) {
				swap(list, i, ppoint);
				ppoint++;
				operations += 3;
			}
		}
		// Finally, swap the item at the pivot pointer that we know is > pivot
		// value with the actual pivot, which is still at the end of the list
		// where it started
		swap(list, ppoint, stop);
		// Return current location of the pivot pointer
		operations++;
		return ppoint;
	}

	/**
	 * Swap two elements in a list
	 * 
	 * @param list
	 * @param a
	 * @param b
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static void swap(int[] list, int a, int b) {
		// Choosing not to increment calls here, as this is always a constant
		// time operation and is only written as a function to save work
		int temp = list[a];
		list[a] = list[b];
		list[b] = temp;
		operations += 3;
	}

	/**
	 * Perform mergesort on an integer array
	 * 
	 * @param list
	 * @return the sorted list
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static int[] mergeSort(int[] list) {
		calls++;
		// List is sorted if it contains 0 or 1 elements
		if (list.length <= 1) {
			operations++;
			return list;
		}
		else {
			// If not, sort the left half and the right half and merge the two
			int[] left = mergeSort(Arrays.copyOfRange(list, 0, list.length / 2));
			int[] right = mergeSort(Arrays.copyOfRange(list, list.length / 2, list.length));
			operations += 3;
			return merge(left, right);
		}
	}

	/**
	 * Merge two lists in order and return the result
	 * 
	 * @param a
	 *            first list
	 * @param b
	 *            second list
	 * @return the two merged together
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static int[] merge(int[] a, int[] b) {
		calls++;
		// Resulting list will be the combined size of a and b
		int[] result = new int[a.length + b.length];
		// Start by looking at the start of each list
		int apoint = 0;
		int bpoint = 0;
		operations += 3;
		for (int i = 0; i < result.length; i++) {
			// Both still have elements; take the smaller one, add to return
			// list
			if (apoint < a.length && bpoint < b.length) {
				if (a[apoint] <= b[bpoint]) {
					result[i] = a[apoint];
					apoint++;
					operations += 4;
				}
				else {
					result[i] = b[bpoint];
					bpoint++;
					operations += 4;
				}
			}
			// One of the lists is now emptied; take the rest of the other list
			// in order
			else if (apoint < a.length) {
				result[i] = a[apoint];
				apoint++;
				operations += 3;
			}
			else {
				result[i] = b[bpoint];
				bpoint++;
				operations += 3;
			}
		}
		operations++;
		return result;
	}

	/**
	 * Implementation of SelectionSort that is NOT in-place
	 * 
	 * @param list
	 * @return
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static int[] selectionSort(int[] list) {
		int[] retval = new int[list.length];
		for (int i = 0; i < list.length; i++) {
			// Place the smallest element from the array at the next slot in the
			// new array
			retval[i] = replaceMin(list);
		}
		return retval;
	}

	/**
	 * Find the smallest element in the list and return it. Also replace its
	 * location with the MAX_VALUE constant
	 * 
	 * @param list
	 * @return minimum value of the array
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static int replaceMin(int[] list) {
		int minVal = list[0];
		int minIndex = 0;
		// Find the smallest value in the array
		for (int i = 0; i < list.length; i++) {
			if (list[i] < minVal) {
				minVal = list[i];
				minIndex = i;
			}
		}
		// Replace the smallest value with the max integer value, and return the
		// minimum
		list[minIndex] = Integer.MAX_VALUE;
		return minVal;
	}

	/**
	 * Implementation of an in-place version of selection sort
	 * 
	 * @param list
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static void selectionSortInPlace(int[] list) {
		// Loop through the array and put the smallest index at the start index
		// i
		// Each pass, one more element will be in order
		for (int i = 0; i < list.length; i++) {
			int minIndex = i;
			int minValue = list[i];
			// Find the minimum value in list[i:end], put it at space i by
			// swapping it with current value at i
			for (int j = i; j < list.length; j++) {
				if (list[j] < minValue) {
					minValue = list[j];
					minIndex = j;
				}
			}
			swap(list, i, minIndex);
		}
	}
}
