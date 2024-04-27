import java.io.*;
import java.util.*;

public class Question3b{
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

        double[] centroid1 = new double[4];
        double[] centroid2 = new double[4];
        double[] centroid3 = new double[4];

        ArrayList<Integer> centroid1Group = new ArrayList<Integer>();
        ArrayList<Integer> centroid2Group = new ArrayList<Integer>();
        ArrayList<Integer> centroid3Group = new ArrayList<Integer>();

        for(int i = 0; i < dataset.length; i++){
            if(i % 3 == 1){
                centroid1Group.add(i);
            } else if(i % 3 == 2){
                centroid2Group.add(i);
            } else {
                centroid3Group.add(i);
            }
        }
        
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 4; j++){
                centroid1[j] += (Double) dataset[centroid1Group.get(i)][j];
                centroid2[j] += (Double) dataset[centroid2Group.get(i)][j];
                centroid3[j] += (Double) dataset[centroid3Group.get(i)][j];
            }
        }
        for(int i = 0; i < 4; i++){
            centroid1[i] /= 10;
            centroid2[i] /= 10;
            centroid3[i] /= 10;
        }


        for(int j = 0; j < 1000; j++){
            centroid1Group = new ArrayList<Integer>();
            centroid2Group = new ArrayList<Integer>();
            centroid3Group = new ArrayList<Integer>();
            for(int i = 0; i < dataset.length; i++){
                double[] distance = new double[3];
                distance[0] = Math.sqrt( Math.pow(centroid1[0] - (Double)dataset[i][0], 2) + Math.pow(centroid1[1] - (Double)dataset[i][1], 2) + Math.pow(centroid1[2] - (Double)dataset[i][2], 2) + Math.pow(centroid1[3] - (Double)dataset[i][3], 2));
                distance[1] = Math.sqrt( Math.pow(centroid2[0] - (Double)dataset[i][0], 2) + Math.pow(centroid2[1] - (Double)dataset[i][1], 2) + Math.pow(centroid2[2] - (Double)dataset[i][2], 2) + Math.pow(centroid2[3] - (Double)dataset[i][3], 2));
                distance[2] = Math.sqrt( Math.pow(centroid3[0] - (Double)dataset[i][0], 2) + Math.pow(centroid3[1] - (Double)dataset[i][1], 2) + Math.pow(centroid3[2] - (Double)dataset[i][2], 2) + Math.pow(centroid3[3] - (Double)dataset[i][3], 2));
                
                if(distance[0] < distance[1]){
                    if(distance[0] < distance[2]){
                        centroid1Group.add(j);
                    } else {
                        centroid3Group.add(j);
                    }
                } else {
                    if(distance[1] < distance[2]){
                        centroid2Group.add(j);
                    } else {
                        centroid3Group.add(j);
                    }
                }
            }
        }
        
        System.out.print("Centroid1 Coordinates: [");
        for(int i = 0; i < 4; i++){
            System.out.print(centroid1[i] + ", ");
        }
        System.out.println("]");
        System.out.print("Centroid2 Coordinates: [");
        for(int i = 0; i < 4; i++){
            System.out.print(centroid2[i] + ", ");
        }
        System.out.println("]");
        System.out.print("Centroid3 Coordinates: [");
        for(int i = 0; i < 4; i++){
            System.out.print(centroid3[i] + ", ");
        }
        System.out.println("]");
        
        
    }
}