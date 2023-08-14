import java.io.Serializable;
import java.util.*;

// Provides the defaults of sizes, sounds, and images for a game.
// Also controls how keyboard input is mapped to an action in the game.
// By subclassing this class, you could create a new "skin" for the game.

public abstract class Config implements Serializable
{
    // Translates a letter hit by the player into the action that that
    // key causes to happen in the game. Currently this is just the
    // cardinal direction that the hero is attempting to move in.
    // If the character isn't a direction, returns INVALID.

    // YOU CAN ADD EXTRA ACTIONS BY EXPANDING THE Action enum, and adding extra
    // switch cases easily here

    public Action mapKeyToAction(String ch) {
        switch (ch)
        {
            case "w":  return Action.NORTH;
            case "s":  return Action.SOUTH;
            case "a":  return Action.WEST;
            case "d":  return Action.EAST;
            default :  return Action.INVALID;
        }
    }

    
    // Maps a specified cell tile type to the image that is used to display it
    public abstract String mapTileToImage(Tile tile);
    
    //Maps a specified item to the image used to display it
    public abstract String mapItemToImage(Item item, Tile tile);
    
    //Maps a specified character to the image used to display it
    public abstract String mapCharacterToImage(GameCharacter creature, Tile tile);
    
    //Setups the icons and data for hero status menu
    public abstract String setupHeroText(Hero hero);
    
    //Sets up the icons and data for monster status menu
    public abstract String setupMonsterText(ArrayList<Monster> attackers);
    
    //maps to the attack sound filename
    public abstract String getAttackSound();
    
    
}
