import java.util.regex.*;
import java.util.*;
import java.io.*;

public class part2{
	public static void main(String[] args){
		double start = System.currentTimeMillis();

		double end = System.currentTimeMillis();

		System.out.println("********************************");
		System.out.println("Total Runtime: " + ((end-start)/1000) + " seconds");
		System.out.println("********************************");
	}
		
}

class wordCountThread implements Runnable{

    String fileName;
    int letter;
    

    public wordCountThread(String fileName, int letter){
        this.fileName = fileName;
        this.letter = letter;
    }

    public void run(){
        System.out.println("--------------------------------------------");
        System.out.println("Key\t\t\t\tValue");
        System.out.println("--------------------------------------------");
        try{
            File inputDir = new File("./files");
            for (File file : inputDir.listFiles()){
                
                BufferedReader reader = new BufferedReader( new FileReader(file) );

                String line = "";
                String pattern = "(?<![a-zA-Z])[a-zA-Z]{" + letter + "}(?![a-zA-Z])";	
                Pattern r = Pattern.compile(pattern);
                Map<String, String> fileWords = Collections.synchronizedMap(new HashMap<String, String>());
                
                while (true){
                    line = reader.readLine();
                    if(line == null){
                        break;
                    }
                    Matcher m = r.matcher(line);
                    
                    while (m.find()) {
                        String word = m.group().toLowerCase();
                        fileWords.putIfAbsent(word, "0");
                        fileWords.put( word, String.valueOf( Integer.parseInt(fileWords.get( word )) + 1) );
                    }
                }

                reader.close();

                int max = 0;
                String maxStr = "";

                for(Map.Entry<String, String> entry : fileWords.entrySet()){
                    int value = Integer.parseInt(entry.getValue());

                    if(value > max){
                        max = value;
                        maxStr = entry.getKey();
                    }
                }

                System.out.print(file.getName());
                for(int i = file.getName().length(); i < 32; i += 8){
                    System.out.print("\t");
                }
                System.out.println(maxStr);
            }
        } catch (IOException e){
            System.err.println("IO Error occured");
        } catch (Exception e){
            System.err.println("Error occured");
        }
        System.out.println("");	
	
    }

}