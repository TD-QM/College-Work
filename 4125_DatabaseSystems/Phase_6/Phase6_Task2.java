import java.io.*;
import java.util.*;

public class Phase6_Task2{
    public static void main (String[] args) throws FileNotFoundException{

        //Query1();
        Query2();
        
    }

    public static void Query1() throws FileNotFoundException{
        File addressFile = new File(String.format("address.txt")); 
        File customerFile = new File(String.format("customer.txt"));
        Scanner addressInfile = new Scanner(addressFile);
        Scanner customerInfile = new Scanner(customerFile);

        String addressLine;
        String customerLine;

        String[] addressVal; 
        String[] customerVal;

        ArrayList<String[]> addressArr = new ArrayList<>();
        ArrayList<String[]> customerArr = new ArrayList<>();

        while(customerInfile.hasNext()){
            customerLine = customerInfile.nextLine();
            customerVal = customerLine.split(",");
            customerArr.add(customerVal);
        }

        while( addressInfile.hasNext() ){
            addressLine = addressInfile.nextLine();
            addressVal = addressLine.split(",");
            addressArr.add(addressVal);
        }
    
        String output = "";

        for(int i = 0; i < customerArr.size(); i++){
            for(int j = 0; j < addressArr.size(); j++){
                if(customerArr.get(i)[0].equals(addressArr.get(j)[0])){
                    output += customerArr.get(i)[1] + "\n";
                    j = addressArr.size();
                }
            }
        }

        System.out.println(output);
        

        addressInfile.close();
        customerInfile.close();
    }

    public static void Query2() throws FileNotFoundException{
        File productFile = new File(String.format("product.txt")); 
        File lineitemFile = new File(String.format("lineitem.txt"));
        Scanner productInfile = new Scanner(productFile);
        Scanner lineitemInfile = new Scanner(lineitemFile);

        String productLine;
        String lineitemLine;

        String[] productVal; 
        String[] lineitemVal;

        HashMap<String, Integer> productHash = new HashMap<String, Integer>();

        while( productInfile.hasNext() ){
            productLine = productInfile.nextLine();
            productVal = productLine.split(",");
            productHash.put(productVal[0].trim(), 0);
        }

        while(lineitemInfile.hasNext()){
            lineitemLine = lineitemInfile.nextLine();
            lineitemVal = lineitemLine.split(",");
            productHash.replace(lineitemVal[1].trim(), Integer.valueOf(Integer.sum( productHash.get(lineitemVal[1].trim()), ( Integer.decode(lineitemVal[2].trim()) ))));
        }

        // Printing a HashMap taken from https://www.w3docs.com/snippets/java/printing-hashmap-in-java.html
        for (Map.Entry<String, Integer> entry : productHash.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }


        productInfile.close();
        lineitemInfile.close();

        
    }

}