import java.util.Arrays;

/**
 * @File: EvenOdd.java
 * 
 *        Implements a method that puts all of the even elements of an array
 *        before all of the odd elements. Includes both an iterative and
 *        recursive solution.
 * 
 * @Author Alex Swindle
 * @Email aswindle@email.arizona.edu
 * 
 * @Date: Mar 21, 2018
 * @Last modified: 10:54:09 AM
 */

public class EvenOdd {
	public static void main(String[] args) {
		// Check multiple different arrays
		int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
		int[] b = { 2, 4, 6, 8, 10, 12 };
		int[] c = { 1, 3, 5, 7, 9, 11 };
		int[] d = { 2, 4, 6, 1, 3, 5 };
		System.out.println(Arrays.toString(evenOdd(a)));
		System.out.println(Arrays.toString(evenOdd(b)));
		System.out.println(Arrays.toString(evenOdd(c)));
		System.out.println(Arrays.toString(evenOdd(d)));
	}

	/**
	 * Iterative version of the algorithm
	 * 
	 * @param list
	 *            to sort
	 * @return sorted list with evens before odds
	 * @author Alex Swindle
	 */
	public static int[] evenOddIterative(int[] list) {
		// Evens start at the beginning of the new sorted array, odds start at
		// the end
		int[] sorted = new int[list.length];
		int even = 0;
		int odd = list.length - 1;
		for (int cur : list) {
			// Place evens into the list, increment their position
			if (cur % 2 == 0) {
				sorted[even] = cur;
				even++;
			}
			// Place odds into the list, decrement their position
			else {
				sorted[odd] = cur;
				odd--;
			}
		}
		return sorted;
	}

	/**
	 * Sets up the evenOddHelper function with the correct starting values
	 * 
	 * @param list
	 * @return sorted version with evens before odds
	 * @author Alex Swindle
	 */
	public static int[] evenOdd(int[] list) {
		// Initialize the sorted array, begin evens at the start and odds at the
		// end
		int[] sorted = new int[list.length];
		int even = 0;
		int odd = list.length - 1;
		evenOddHelper(list, sorted, 0, even, odd);
		return sorted;
	}

	/**
	 * Recursive version of the evenOdd function
	 * 
	 * @param list
	 *            to be sorted
	 * @param sorted
	 *            that will be the final result
	 * @param cur
	 *            index that's currently being processed
	 * @param even
	 *            index at which evens will be placed
	 * @param odd
	 *            index at which odds will be placed
	 * @author Alex Swindle
	 */
	public static void evenOddHelper(int[] list, int[] sorted, int cur, int even, int odd) {
		// Base case: if cur has reached the end of the list, it's done
		if (cur == list.length) {
			return;
		}
		// Recursive case: at least one element left. Process that one, recurse
		// into remaining list
		else {
			// Even #s go at the current even index, index is incremented
			if (list[cur] % 2 == 0) {
				sorted[even] = list[cur];
				even++;
			}
			// Odds go at the current odd index, index is decremented
			else {
				sorted[odd] = list[cur];
				odd--;
			}
			// Increase the current index, recurse into a one-element-smaller
			// version of the problem
			cur++;
			evenOddHelper(list, sorted, cur, even, odd);
		}
	}
}
