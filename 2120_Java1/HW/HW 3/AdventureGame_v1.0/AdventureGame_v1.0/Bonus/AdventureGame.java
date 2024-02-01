import java.util.*;

/*****************************************************************
 * AdventureGame main program:
 * 
 * Description:
 * Reads in the dungeon configuration from standard input.
 * 
 * Usage:
 * cat d1.txt - | java AdventureGame
 ***************************************************************/


public class AdventureGame
{
    public static void main(String[] args)
    {
        //Game's Configuration Data (depending on OS/BROWSER ENV)
        String systemSettings = "";
        for (String specification : args)
        {
            systemSettings+= specification;
        }
        //Call to factory class to construct the correct config object for us.
        Config config = ConfigFactory.createConfig(systemSettings);
        
        //Read in the size of the map
        int width = StdIn.readInt();
        int height = StdIn.readInt();
        
        //Construct the dungeon layout
        Dungeon dungeon = new Dungeon(config, width, height);
        
        //Read in the initial location of the hero
        //#TODO add which room the hero starts in
        int heroX = StdIn.readInt();
        int heroY = StdIn.readInt();
        Hero hero = new Hero(config, heroX, heroY);
        dungeon.addHero(hero);
        
        //Setup all the cells in the dungeon
        for (int y=0; y<height; y++)
        {
            for (int x=0; x<width; x++)
            {
                dungeon.set(x, y, StdIn.readString());
            }//end inner-for
        }//end outer-for
        
        //#TODO read item string, change param for STRING, parse from map 
        dungeon.addItem(Item.KEY,1,1);
        dungeon.addMonster(new Monster(config,1,2));
        dungeon.addMonster(new Monster(config,1,3));
        dungeon.addMonster(new Monster(config,10,8));

        GameTUI tui = new GameTUI(config,dungeon);
        tui.run();
        
    }//end main
    
}//end Game
