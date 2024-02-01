import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*****************************************************************
 * Game TUI:
 * 
 * Description:
 * 
 ***************************************************************/


public class GameTUI
{

    private Config itsConfig;
    private Dungeon itsDungeon;
    private boolean save = false;
    private boolean load = false;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public GameTUI (Config c, Dungeon d) {

        itsConfig = c;
        itsDungeon = d;
    }

    // this is the main loop - very little logic here in the TUI
    // but all the input and output should be controlled from here

    public void run() {
        while (itsDungeon.getHero().getHealth() > 0)
        {
            //clear previous screen 
            StdDraw.clear();
            
            //draw the game world
            //drawScreen();
            itsDungeon.draw();

            String heroMenu = itsConfig.setupHeroText(itsDungeon.getHero());
            StdDraw.drawText(heroMenu);
            String monsterMenu = itsConfig.setupMonsterText(itsDungeon.getAdjacentMonsters());
            StdDraw.drawText(monsterMenu);

            if(save){
                System.out.println("Successfully saved game!");
            } else if(load){
                System.out.println("Successfully loaded game!");
            }

            save = false;
            load = false;
        
            //Get player's action from input
            String userInput = StdIn.readString();
            Action action = itsConfig.mapKeyToAction(userInput);
            
            if(userInput.equals("save")){

                System.out.print(System.lineSeparator() + "Enter a save file name (must end in .dat): ");
                
                Scanner in = new Scanner(System.in);

                String fileName = in.nextLine();

                while(!fileName.substring(fileName.length()-4).equals(".dat")){
                    System.out.println("Invalid file name");
                    System.out.print("Enter a save file name (must end in .dat): ");
                    fileName = in.nextLine();
                }

                /*
                 * Closing the Scanner here gives me an error message relating to line 61. No idea how to fix it.
                 */

                // Attempt to save the game
                try{
                    // Make a new OutputStream object that looks at the file with the name saveName
                    output = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName))); 

                    // Overwrite the data in saveName with the current status of the dungeon
                    output.writeObject(itsDungeon); 

                    // Close the stream
                    output.close();

                    // Set save boolean to true
                    save = true;

                } catch (NoSuchElementException noSuchElementException){ // If a save file isn't located, then create one
                    File file = new File("./" + fileName); // Make a file object with the name in the current directory
            
                    // Attempt to create a file using the file object in the current directory
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        System.err.println("Failed to create file. (IO Exception for creatingNewFile)");
                    }
            
                    // Attempt to save the game to this new file
                    try {
                        // Do the exact same thing
                        output = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName))); 
                        output.writeObject(itsDungeon); 
                        output.close();
                        save = true; // Set save boolean to true
                    } catch (NoSuchElementException e) {
                        // Error already printed out earlier
                    } catch (IOException e) {
                        System.err.println("Failed to save game. (IO Exception for saving the game)");
                    }

                } catch (IOException ioException){
                    System.err.println("Failed to save game. (IO Exception in execute)");
                }


            } else if(userInput.equals("load")){

                System.out.print(System.lineSeparator() + "Enter a save file name (must end in .dat): ");
                
                Scanner in = new Scanner(System.in);

                String fileName = in.nextLine();

                while(!fileName.substring(fileName.length()-4).equals(".dat")){
                    System.out.println("Invalid file name");
                    System.out.print("Enter a save file name (must end in .dat): ");
                    fileName = in.nextLine();
                }

                // Attempt to load the game. If a save file isn't located, then do not load the game
                try{
                    // Make a new InputStream object that looks at the file with then name fileName
                    input = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
                    
                    // Set the obejct in the file equal to a variable
                    Object serializedObject = input.readObject();

                    // Close the stream
                    input.close();
            
                    // Check to see if the serialized file contained a Dungeon object
                    if (serializedObject instanceof Dungeon){ 
                        itsDungeon = (Dungeon) serializedObject;
                        load = true;
                    } else {
                        // Print out an error message if the object in the file isn't of type Dungeon
                        System.err.println("Failed to load game. (Incorrect object type in file)");
                    }

                } catch (NoSuchElementException noSuchElementException){
                    System.err.println("No save file located.");

                } catch (IOException ioException){
                    System.err.println("Failed to load game. (IO Exception)");

                } catch (ClassNotFoundException classNotFoundException){
                    System.err.println("Failed to load game. (Class Not found Exception)");
                    
                }


            } else {
                //Execute player's action and Update game 
                itsDungeon.execute(action);
            }
        
        }
        StdDraw.drawText("You died!\n");
    }//end run

    public void drawScreen() {
        // Draw game map!
        StdDraw.initGameMap(itsDungeon.getWidth(), itsDungeon.getHeight());
        drawDungeon();
        drawHero();
        drawMonsters();
        //Render display
        StdDraw.draw(); //change to call called render
    }

    public void drawDungeon() {
        Cell[][] map = itsDungeon.getMap();
        for (int i=0;i<itsDungeon.getWidth();i++) 
        {
             for (int j=0;j<itsDungeon.getHeight();j++)
             {
                   map[i][j].draw(itsConfig, i,j);
             }
        }
    }

    //Draw hero
    public void drawHero() {
        Cell[][] map = itsDungeon.getMap();
        Hero stanley = itsDungeon.getHero();
        Tile heroTile = map[stanley.getX()][stanley.getY()].getTile();  //Get background tile
        stanley.draw(heroTile);                                      //Draw hero ontop that background
    }

    //Draw monsters
    public void drawMonsters() {
        Cell[][] map = itsDungeon.getMap();
        ArrayList<Monster> monsters = itsDungeon.getMonsters();
        for (Monster creature: monsters)
        {
            Tile monsterTile = map[creature.getX()][creature.getY()].getTile();  //Get background tile  
            creature.draw(monsterTile);
        }
    }

    
}//end GameTUI
