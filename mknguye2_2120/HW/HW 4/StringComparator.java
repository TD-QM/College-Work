public class StringComparator{
    public static int compare(final String s1, final String s2){
        String string1 = s1.toLowerCase();
        String string2 = s2.toLowerCase();
        if(string1.length() == 0 && string2.length() == 0){
            return 0;
        } else if(string1.length() == 0){
            return -1;
        } else if(string2.length() == 0){
            return 1;
        } else if(string1.charAt(0) == string2.charAt(0)){
            return compare(string1.substring(1), string2.substring(1));
        }

        return Character.compare(string1.charAt(0), string2.charAt(0));
    }

    public static String findMinimum(final String[] stringArray, int numStrings){
        if(numStrings == 2){
            if(compare(stringArray[0], stringArray[1]) < 0){
                return stringArray[0];
            } else if(compare(stringArray[0], stringArray[1]) > 0){
                return stringArray[1];
            } else {
                return stringArray[0];
            }
        } else {
            String[] newStringArr = new String[stringArray.length - 1];
            for(int i = 0; i < stringArray.length - 1; i++){
                newStringArr[i] = stringArray[i];
            }

            String s1 = findMinimum(newStringArr, newStringArr.length);
            String s2 = stringArray[stringArray.length - 1];

            int compareValue = compare(s1, s2);

            if(compareValue < 0){
                return s1;
            } else if(compareValue > 0){
                return s2;
            } else {
                return s1;
            }
        }
    }

    public static String findMinimumWithMergeSort(final String[] stringArray, int numStrings){
        return mergeSort(stringArray, numStrings)[0];
    }

    public static String[] mergeSort(final String[] stringArray, int numStrings){
        if(numStrings == 1){
            return stringArray;
        } else {
            String[] strArr1 = copyArray(stringArray, 0, stringArray.length / 2);
            String[] strArr2 = copyArray(stringArray, stringArray.length / 2, stringArray.length);

            return sortTwoStrArr(mergeSort(strArr1, strArr1.length), (mergeSort(strArr2, strArr2.length)));
        }


    }

    public static String[] sortTwoStrArr(String[] arr1, String[] arr2){
        String[] sortedStringArray = new String[arr1.length + arr2.length];
        int index1 = 0;
        int index2 = 0;

        for(int i = 0; i < sortedStringArray.length; i++){
            if(index1 == arr1.length){
                sortedStringArray[i] = arr2[index2];
                index2++;
            } else if(index2 == arr2.length){
                sortedStringArray[i] = arr1[index1];
                index1++;
            } else if(compare(arr1[index1], arr2[index2]) < 0){
                sortedStringArray[i] = arr1[index1];
                index1++;
            } else if(compare(arr1[index1], arr2[index2]) > 0){
                sortedStringArray[i] = arr2[index2];
                index2++;
            } else {
                sortedStringArray[i] = arr1[index1];
                index1++;
            }
        }
        
        return sortedStringArray;
    }

    public static String[] copyArray(String[] array, int beginIndex, int endIndex){
        String[] copiedArray = new String[endIndex - beginIndex];
        for(int i = 0; i < copiedArray.length; i++){
            copiedArray[i] = array[beginIndex];
            beginIndex++;
        }
        return copiedArray;
    }
}