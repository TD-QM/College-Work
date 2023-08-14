public class StringUtility {

    /**
     *
     * This method takes in a sentence as a parameter and returns the reverse of the sentence by word. 
     *      For this particular problem, we will convert all our sentences to lowercase. The result 
     *      should only return the strings separated by a single space, although the input may 
     *      contain multiple spaces in between them.
     *
     * <br>
     * Example:
     *
     * <pre>
     * reverse("This is a SENTENCE") will return "sentence is a this".
     * reverse("This         SENTENCE") will return "sentence this".
     * </pre>
     *
     * @param sentence The original sentence to be reversed
     * @return Reverse of the given statement
     */
    public static String reverse(String sentence) {
        sentence = sentence.toLowerCase() + " ";
        System.out.println(sentence);
        String newString = "";
        String tempString = "";

        for(int i = 0; i < sentence.length(); i++){
            tempString += sentence.substring(i, i+1);
            if(sentence.substring(i, i+1).equals(" ")){
                if(i != 0){
                    newString = tempString + newString;
                }
                for(int j = i + 1; j < sentence.length(); j++){
                    if(sentence.substring(j, j+1).equals(" ")){
                        i++;
                    } else {
                        break;
                    }
                }
                tempString = "";
            }
        }

        newString = newString.substring(0, newString.length() - 1);

        return newString;
    }


    /**
     * This method takes a sentence and counts the max occurring character. 
     *      It ignores all the spaces, punctuations, digits and converts all 
     *      the uppercase to lowercase letters.
     *
     * Example:
     *
     * <pre>
     * maxOccuringCharacter("iiiiiii") = 'i'
     * maxOccuringCharacter("Iiijjj") = 'i'
     * </pre>
     *
     * @param sentence The original sentence from which we should find the max
     *                 occuring character.
     * @return The max occuring character. If there are multiple max occuring
     *         character return the first character by alphabetical order
     * @throws IllegalArgumentException if the given sentence is empty (length of
     *                                  sentence = 0)
     */
    public static char maxOccuringCharacter(String sentence) throws IllegalArgumentException {
        if(sentence.equals("")){
            throw new IllegalArgumentException();
        }

        sentence = sentence.toLowerCase();
        char tempMax= sentence.charAt(0);
        int tempMaxCounter = 0;
        char trueMax = sentence.charAt(0);
        int trueMaxCounter = 0;
        
        // Loop through each character in the string
        for(int i = 0; i < sentence.length(); i++){

            tempMaxCounter = 1; // Reset counter to 1
            tempMax = sentence.charAt(i); // Set temp character

            if (Character.isLetter(tempMax)){
                // Loop through the rest of the String
                for(int j = i+1; j < sentence.length(); j++){
                    // Add to the counter each time the temp character appears
                    if(sentence.charAt(j) == tempMax){
                        tempMaxCounter++;
                    }
                }

                if(tempMaxCounter > trueMaxCounter){ // If the current character appears more often than the last one, set trueMax to it
                    trueMax = tempMax;
                    trueMaxCounter = tempMaxCounter;
                } else if (tempMaxCounter == trueMaxCounter){ // If they are appear the same amount of times, then set the one that comes first alphabetically is set
                    if((String.valueOf(trueMax)).compareTo((String.valueOf(tempMax))) > 0){
                        trueMax = tempMax;
                    }
                }

            }
        }
        return trueMax;
    }

    /**
     * This method checks whether the given string is a palindrome or not. 
     *      A palindrome is a word, number, phrase, or other sequences of 
     *      characters that read the same backward as forward, such as madam 
     *      or racecar. An empty string is considered to be a palindrome.
     *
     * @param input The string to check
     * @return true if the given string is a palindrome and false otherwise
     */
    public static boolean isPalindrome(String input) {
        if(input.equals("")){
            return true;
        }

        input = input.toLowerCase();
        String newInput = "";
        String reversedInput = "";

        for(int i = 0; i < input.length(); i++){
            if(Character.isLetter(input.charAt(i))){
                newInput += input.charAt(i);
            }
        }

        for(int i = 0; i < newInput.length(); i++){
            reversedInput = newInput.substring(i, i+1) + reversedInput;
        }

        System.out.println("\n" + reversedInput);

        if(newInput.equals(reversedInput)){
            return true;
        } else {
            return false;
        }

    }
}
