import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrintWriter;


//#TODO
//Use appropriate imports
//hint: there are a lot!

public class CartoonCharacterSerializer {
	
	private static ObjectOutputStream outputCartoon;
	private static ObjectInputStream inputCartoon;
	private static PrintWriter writer;
	
	//Method that writes one CartoonCharacter object out to a file
	public static void serializeCharacter(CartoonCharacter character) {
		//#TODO
		//Initialize outputCartoon to serialize objects to a file called Cartoon.ser
		//Write the character object out to the file
		//Close the stream
		try{
			outputCartoon = new ObjectOutputStream(Files.newOutputStream(Paths.get("Cartoon.ser")));
			outputCartoon.writeObject(character);
			outputCartoon.close();
		} catch (IOException ioException){
			System.err.println("Failed to write to file");
		}
	}
	
	public static CartoonCharacter deserializeCharacter() {
		CartoonCharacter cartoon = null;
		
		//#TODO
		//Initialize inputCartoon to read objects from a file called Cartoon.ser
		//Read one CartoonCharacter object and store it in variable cartoon
		try{
			inputCartoon = new ObjectInputStream(Files.newInputStream(Paths.get("Cartoon.ser"))); 
			cartoon = (CartoonCharacter) inputCartoon.readObject();
			inputCartoon.close();
		} catch (IOException ioException){
			System.err.println("Failed to read file");
		} catch (ClassNotFoundException classNotFoundException){
			System.err.println("File does not exist");
		}

		return cartoon;
	}
	
	// NOTE: This is optional and you don't have to do this if you choose not to
	public static void printToFile(String stringToFile) throws FileNotFoundException {
		
		//#TODO
		//Initialize writer to print characters to a file called C-137.txt
		//Print stringToFile to that file
		//Print the string "PrintWriter makes printing 50 times easier!" followed by a newline character out to the file
		//Close the PrintWriter stream
		try{
			writer = new PrintWriter("C-137.txt");
			writer.print(stringToFile + System.getProperty("line.separator"));
			writer.print("PrintWriter makes printing 50 times easier!" + System.getProperty("line.separator"));
			writer.close();
		} catch (FileNotFoundException fileNotFoundException){
			System.err.println("File does not exist");
		}
	}

}
