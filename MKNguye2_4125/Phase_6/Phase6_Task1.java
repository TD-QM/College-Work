import java.io.*;
import java.util.*;

public class Phase6_Task1{
    public static void main (String[] args) throws FileNotFoundException{

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
}