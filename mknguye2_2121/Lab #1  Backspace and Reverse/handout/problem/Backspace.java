/**
* Given a string S containing letters and ‘#‘. The ‘#” represents a backspace. The task is to print the new string without ‘#‘.
* Input : S = "abc#de#f#ghi#jklmn#op#"
* Output : abdghjklmo

* Input:  s = "ab##"
* output: ""
*/


public class Backspace {
    public static String erase(String str) {
	// Declaring instance variables
	String newStr = ""; // Used to make the new String

	// For loop to loop through each letter in the original String
	for(int i = 0; i < str.length(); i++){
	    /* 
	    * Check to see if the character is a # or not. If it is not, then add the current character
	    *    character. Otherwise, proceed.
	    */
	    if(!str.substring(i,i+1).equals("#")){
		newStr += str.substring(i,i+1);

	    } else {
		/*
		* thisiswherethefunbegins.jpg
		* Ok, so we found a hash in the string. Very cool. 
		* So, now we have the number of hashes in a row. Now we just need to delete that
		*    many characters.
		* First we check the number of hashes to see if it's less than the current number of 
		*    characters currently in the newString. This is to prevent OutOfBounds Exceptions
		*/
		if(newStr.length() > 0){
		    newStr = newStr.substring(0,newStr.length()-1);
		} else {
		    // If the number of hashes exceeds the number of charaters currently in the String
		    //    simply make the entire thing empty
		    newStr = "";
		}
		
	    }
	}

	return newStr; // Return the String
    }
}
