import java.util.Arrays;

/**
 * @File: MergeSort.java
 * 
 *        Contains the implementation of MergeSort that I used in Lab 4 (without
 *        the benchmark counters)
 * @Author Alex Swindle
 * @Email aswindle@email.arizona.edu
 * 
 * @Date: Mar 21, 2018
 * @Last modified: 9:06:04 AM
 */

public class MergeSort {
	/**
	 * Perform mergesort on an integer array
	 * 
	 * @param list
	 * @return the sorted list
	 * @author Alex Swindle
	 * @email aswindle@email.arizona.edu
	 */
	public static int[] mergeSort(int[] list) {
		// Base case: array is sorted if it contains 0 or 1 elements
		if (list.length <= 1) {
			return list;
		}
		// Recursive case: sort the left and right halves, merge them together
		else {
			int[] left = mergeSort(Arrays.copyOfRange(list, 0, list.length / 2));
			int[] right = mergeSort(Arrays.copyOfRange(list, list.length / 2, list.length));
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
		// Resulting list will be the combined size of a and b
		int[] result = new int[a.length + b.length];
		// Start by looking at the start of each list
		int apoint = 0;
		int bpoint = 0;
		for (int i = 0; i < result.length; i++) {
			// Both still have elements; take the smaller one, add to return
			// list
			if (apoint < a.length && bpoint < b.length) {
				if (a[apoint] <= b[bpoint]) {
					result[i] = a[apoint];
					apoint++;
				}
				else {
					result[i] = b[bpoint];
					bpoint++;
				}
			}
			// One of the lists is now emptied; take the rest of the other list
			// in order
			else if (apoint < a.length) {
				result[i] = a[apoint];
				apoint++;
			}
			else {
				result[i] = b[bpoint];
				bpoint++;
			}
		}
		return result;
	}
}