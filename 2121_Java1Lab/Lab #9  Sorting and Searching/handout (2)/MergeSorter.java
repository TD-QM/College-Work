/**
* A class that models the merge sort algorithm recursively.
* All methods are static and belong to the class.
*
* @author	YOUR NAME HERE
* @version	THE DATE HERE
*/
import java.util.Arrays;
public class MergeSorter {

	/**
	* Calls the recursive sortArray() method passing 
	* in an integer array of data.
	*
	* @param	int[] 	data
	*/
	public static int[] mergeSort(int[] data){
		sortArray(data, 0, data.length - 1);
		return data;
	}
	
	public static int[] sortArray(int[] data, int low, int high){
		//If the size of an array is greater than one element...
		if ((high - low) >= 1){
			//Initialize the middle1 index with the low plus high indices divided by two.
			//Initialize the middle2 index with the middle1 plus one.
			int middle1 = (low + high) / 2;
			int middle2 = middle1 + 1;

			System.out.printf("Split:  %s%n", subarrayString(data, low, high));
			System.out.printf("        %s%n", subarrayString(data, low, middle1));
			System.out.printf("        %s%n%n", subarrayString(data, middle2, high));
			
			//Split the array in half with the following two steps:
			//Recursively send the first half back into sortArray().
			//Recursively send the second half back into sortArray().
			sortArray(data, low, middle1);
			sortArray(data, middle2, high);
			
			//Each splitting of the array requires a merging of the sorted left & right arrays:
			//Call the merge() method with the appropriate arguments.
			merge(data, low, middle1, middle2, high);

		}
        return data;
	}
	
	/**
	* Compares, sorts, and merges the "left array" & the "right array".
	*
	* @param	int[]	data
	* @param	int 	left
	* @param	int 	middle1
	* @param	int 	middle2
	* @param	int 	right
	*/
	public static void merge(int[] data, int left, int middle1, int middle2, int right){
		//Initialize leftIndex by assigning it the "left" value.
		//Initialize rightIndex by assigning it the "middle2" value.
		//Initialize combinedIndex by assigning it the "left" value--index into temp working array.
		//Initialize the working int array named "combined" and set its size as the length of the data.
		int leftIndex = left;
		int rightIndex = middle2;
		int combinedIndex = left;
		int[] combined = new int[data.length];
		
		System.out.printf("Merge:   %s%n", subarrayString(data, left, middle1));
		System.out.printf("         %s%n", subarrayString(data, middle2, right));
		// System.out.println("Data: " + toString(data));
		// System.out.println("Left: " + left);
		// System.out.println("Mid1: " + middle1);
		// System.out.println("Mid2: " + middle2);
		// System.out.println("Right: " + right);
		
		//Merge the two array until reaching the end of either one.
		//WHILE the leftIndex <= middle1 AND rightIndex <= right...
			//IF the data at the leftIndex <= the data at the rightIndex...
				//Put the data at the leftIndex in the combined array at the combinedIndex,
				//incrementing both the leftIndex and the combinedIndex.
			//ELSE
				//Put the data at the rightIndex in the combined array at the combinedIndex,
				//incrementing both the rightIndex and the combinedIndex.
		while(leftIndex <= middle1 && rightIndex <= right){
			if(data[leftIndex] < data[rightIndex]){
				combined[combinedIndex] = data[leftIndex];
				leftIndex++;
			} else {
				combined[combinedIndex] = data[rightIndex];
				rightIndex++;
			}
			combinedIndex++;
		}
		
		//IF the left array is empty...
			//WHILE the rightIndex <= the right...
				//Assign the data at the rightIndex to the combined array at the combinedIndex,
				//incrementing both the rightIndex and the combinedIndex.
		//ELSE (The right array is empty!)
			//WHILE the leftIndex <= middle1...
				//Assign the data at the leftIndex to the combined array at the combinedIndex,
				//incrementing both the leftIndex and the combinedIndex.
		if(leftIndex > middle1){
			while(rightIndex <= right){
				combined[combinedIndex] = data[rightIndex];
				rightIndex++;
				combinedIndex++;
			}
		} else if (rightIndex > right){
			while(leftIndex <= middle1){
				combined[combinedIndex] = data[leftIndex];
				leftIndex++;
				combinedIndex++;
			}
		}
		// System.out.println("Data: " + Arrays.toString(data));
		// System.out.println("Combined: " + Arrays.toString(combined));
		// System.out.println("LeftIndex: " + leftIndex);
		// System.out.println("Mid1: " + middle1);
		// System.out.println("RightIndex: " + rightIndex);
		// System.out.println("Right: " + right);

		
		//Copy the values in the combined array back into the original data array:
		//FOR each index beginning with "left" until you get to "right"...
			//Assign value at each index in combined array to the same index in the data array.
		for(int i = left; i <= right; i++){
			data[i] = combined[i];
		}

		System.out.printf("         %s%n%n", subarrayString(data, left, right)); 
	}
	
	/**
	* Prints out a String representation of an array.
	*
	* @param	int[]	data
	* @param	int		low
	* @param	int 	high
	* @return	String	
	*/
	private static String subarrayString(int[] data, int low, int high){
		StringBuilder temp = new StringBuilder();
		for(int i = 0; i < low; i++){
			temp.append("   ");
		}
		for(int i = low; i <= high; i++){
			temp.append(" " + data[i]);
		}
		return temp.toString();
	}

	public static String toString(int[] data){
		String x = "[";

		for(int y : data){
			x = x + y + ", ";
		}

		x += "]";

		return x;
	}

}
