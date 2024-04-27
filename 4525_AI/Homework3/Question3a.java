import java.io.*;
import java.util.*;

public class Question3a{
    public static void main(String[] args) throws Exception{
        File data = new File("IrisDataset.csv");
        Scanner dataReader = new Scanner(data);

        Object[][] dataset = new Object[30][5];
        int fileLine = 0;

        while(dataReader.hasNextLine()){
            String line = dataReader.nextLine();
            String[] lineArr = line.split(",");
            for(int i = 0; i < 4; i++){
                dataset[fileLine][i] = Double.valueOf(lineArr[i]);
            }
            dataset[fileLine][4] = lineArr[4];
            fileLine++;
        }

        double x1 = 5.9;
        double x2 = 3.0;
        double x3 = 5.1;
        double x4 = 1.8;

        double[] closest = new double[30];
        String[] closestIndex = new String[30];

        for(int i = 0; i < dataset.length; i++){
            double distance = Math.sqrt( Math.pow(x1 - (Double)dataset[i][0], 2) + Math.pow(x1 - (Double)dataset[i][1], 2) + Math.pow(x1 - (Double)dataset[i][2], 2) + Math.pow(x1 - (Double)dataset[i][3], 2));
            closest[i] = distance;
            closestIndex[i] = "#" + i + " " + (String)dataset[i][4];
        }

        for(int i = 0; i < 30-1; i++){
            for(int j = i+1; j < 30; j++){
                if(closest[i] > closest[j]){
                    double temp1 = closest[i];
                    closest[i] = closest[j];
                    closest[j] = temp1;
                    String temp2 = closestIndex[i];
                    closestIndex[i] = closestIndex[j];
                    closestIndex[j] = temp2;
                }
            }
        }

        for(int i = 0; i < 30; i++){
            System.out.println(closestIndex[i] + ": " + closest[i]);
        }

        
        
    }
}