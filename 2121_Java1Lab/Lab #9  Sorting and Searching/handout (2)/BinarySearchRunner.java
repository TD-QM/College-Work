import java.security.SecureRandom;
import java.util.Arrays;


public class BinarySearchRunner {
    public static void main(String[] args){
        SecureRandom numGen = new SecureRandom();
		int[] data1 = {2, 3, 4, 7, 11};
        int[] data2 = {1, 2, 3, 4};

        System.out.printf("Array 1: %s%n", Arrays.toString(data1));
        System.out.println( BinarySearch.findKthPositive(data1, 5) );
        System.out.printf("Array 2: %s%n", Arrays.toString(data2));
        System.out.println( BinarySearch.findKthPositive(data2, 2) );
    }
}
