import java.util.regex.*;
import java.util.*;
import java.io.*;

public class part2{
	public static void main(String[] args){
		double start = System.currentTimeMillis();
		doTheThing();
		double end = System.currentTimeMillis();

		System.out.println("********************************");
		System.out.println("Total Runtime: " + ((end-start)/1000) + " seconds");
		System.out.println("********************************");
	}
    

    public static void doTheThing(){
        File inputDir = new File("./files/");
		int size = inputDir.listFiles().length;
		int count;
		Thread[] thrdArr = new Thread[size];
		
		for(int i = 6; i <= 8; i++){
			count = 0;
			System.out.println("--------------------------------------------");
			System.out.println("Key\t\t\t\tValue");
			System.out.println("--------------------------------------------");
			for(File file : inputDir.listFiles()){
				wordCountThread wcThrd = new wordCountThread(file, i);
				thrdArr[count] = new Thread(wcThrd);
				thrdArr[count].start();
				
				count++;
			}
			
			try{
				for(int j = 0; j < size; j++){
					thrdArr[j].join();
				}
			} catch (Exception e){
				
			}
			
			System.out.println("");
		}
    }		
}

class wordCountThread implements Runnable{

    File input;
    int letter;
    

    public wordCountThread(File input, int letter){
        this.input = input;
        this.letter = letter;
    }

    public void run(){
        try{
                
            BufferedReader reader = new BufferedReader( new FileReader(input) );

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

            System.out.print(input.getName());
            for(int i = input.getName().length(); i < 32; i += 8){
                System.out.print("\t");
            }
            System.out.println(maxStr);

        } catch (IOException e){
            System.err.println("IO Error occured");
        } catch (Exception e){
            System.err.println("Error occured");
        }
	
    }

}
