public class Main {
    public static void main(String[] args){
        String[] sa1 = {"Blob",  "Blorg", "Blurf", "Zorpy", "Galoge", "Mulfry", "Plettarg", "Blub", "Ascog", "Asfog", "Rhalps", "Fypio", "Lomphry"};
        String[] sa2 = {"A", "a", "aaaaAAAA", "Decay is an extant form of life."};
        String[] sa3 = {"This is a test", "This is also a test", "This is yet another test."};
        
        String[] sa4 = {"a", "c", "d", "f"};
        String[] sa5 = {"b", "e", "g", "h"};

        System.out.println(arrayToString(StringComparator.mergeSort(sa1, sa1.length)));
        System.out.println(StringComparator.findMinimumWithMergeSort(sa1, sa1.length) + "\n");
        System.out.println(arrayToString(StringComparator.mergeSort(sa2, sa2.length)));
        System.out.println(StringComparator.findMinimumWithMergeSort(sa2, sa2.length) + "\n");
        System.out.println(arrayToString(StringComparator.mergeSort(sa3, sa3.length)));
        System.out.println(StringComparator.findMinimumWithMergeSort(sa3, sa3.length) + "\n");
    }

    public static String arrayToString(String[] strArr){
        String output = "";

        for(String str : strArr){
            output += str + "\n";
        }

        return output;
    }
}
