import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws FileNotFoundException{

        File infile = new File(String.format("product.txt")); // Object that acts as the input file
        Scanner input = new Scanner(infile); // Object that reads the input file

        String line = input.nextLine(); // Set the next line of text as a String
        String[] values = line.split(","); // Split the line into an array at the commas
        float price = Float.parseFloat( values[2].trim() );
        float highest = price;
        String output = values[1].trim();
        while(input.hasNextLine()){
            line = input.nextLine(); // Set the next line of text as a String
            values = line.split(","); // Split the line into an array at the commas
            price = Float.parseFloat( values[2].trim() );
            if( price > highest){
                highest = price;
                output = values[1].trim();
            }
        }

        System.out.println(output);

        input.close();

    }
}