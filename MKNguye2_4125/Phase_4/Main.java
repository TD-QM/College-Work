import java.io.*;
import java.util.*;

public class Main{
    public static void main (String[] args){
        try {

            File infile = new File(String.format("%s.txt", args[0])); // Object that acts as the input file
            Scanner input = new Scanner(infile); // Object that reads the input file
            FileWriter outfile = new FileWriter(String.format("%s.sql", args[0])); // Object to write to the output file

            // Loop until there's nothing left in the input file
            while(input.hasNextLine()){
                String line = input.nextLine(); // Set the next line of text as a String
                String[] values = line.split(","); // Split the line into an array at the commas

                // If there's nothing in the line, just print a new line character
                if(line.length() == 0){ 
                    outfile.write("\n");

                // If there's something in the line, do all of this garbage
                } else { 
                    String[] output = new String[values.length]; // Create a new array the length of the number of values in the line
                    int index = 0; // Set an int to represent the index to iterate through the array

                    // Go through the values array
                    for(String value: values){
                        value = value.trim();
                        
                        try {   // I don't like the fact that the program only works if exceptions are thrown. Something just feels wrong about it
                            Integer.parseInt(value); // Attempt to parse the value as an int
                            output[index] = value; // If it succeeds, set the output's current index as Integer

                        } catch (NumberFormatException e){
                            try {
                                Float.parseFloat(value); // Attempt to parse the value as a float
                                output[index] = value; // If it succeeds, set the output's current index as Float

                            } catch(NumberFormatException e2){
                                if (value.compareTo("NULL") == 0){
                                    output[index] = value;   // This is only called if both checks for the integer and float fail
                                                                                // Since we can assume that if it isn't an int or a float, it's a string, we don't need any checks
                                } else {
                                    output[index] = "'" + value + "'";
                                }
                            }
                        }
                        

                        index++; // Increment the index
                    }

                    String sqlString = String.format("INSERT INTO %s VALUES(%s);\n", args[0], String.join(", ", output));
                    outfile.write(sqlString); // Concatenate the arrya and write it into the output file with a new line
                }
            }

            // Close both streams
            outfile.close();
            input.close();

        } catch (FileNotFoundException e){ // If the file isn't found (Assumed to be the output file which is bad code but if I had a check for both it would be too long)
            File file = new File("./" + "phase3_results.txt");

            try{
                file.createNewFile(); // If the file isn't found, just create it

            } catch (IOException IOException){ // Exception handling
                System.err.println(e.getMessage());

            }

        } catch (IOException e){ // Any general IO Exception catching
            System.err.println(e.getMessage());
        }
    }
}
