import java.util.*;

// Provides the defaults of sizes, sounds, and images for a game.
// Also controls how keyboard input is mapped to an action in the game.
// By subclassing this class, you could create a new "skin" for the game.
//
// Do NOT modify this file.

public class ConfigOSXChrome extends Config
{
    //Named Constants to hold the Color formatting instruction for terminal
    private final String ANSI_BLUE  = "\u001b[44m"; //Blue Background
    private final String ANSI_WHITE = "\u001b[47m"; //White Background
    private final String ANSI_GREEN = "\u001b[42m"; //Green Background
    private final String ANSI_RESET = "\u001B[0m";  //Reset All Formatting
    private final String ANSI_UNDERLINE_ON   = "\033[4m";   //Underline 
    private final String ANSI_UNDERLINE_OFF  = "\033[24m";  //Underline off
    private final String LEFT_VERTICAL_LINE  = "\u23B8";    //Left Outline 
    private final String RIGHT_VERTICAL_LINE = "\u23B9";    //Right Outline
    
    // Maps a specified cell tile type to the image that is used to display it
    public String mapTileToImage(Tile tile)
    {
        String tileImage = mapTileToColor(tile);
        switch (tile)
        {
            case GRASS:  return String.format(tileImage, "\u001b[42;1m\u2B1C");  
            case TREE:   return String.format(tileImage, "\uD83C\uDF33"); 
            case FLOOR:  return String.format(tileImage, "");
            case WALL:   return String.format(tileImage, "");
            case LAVA:   return String.format(tileImage, "");
            case SNOW:   return String.format(tileImage, "");
            case PINE:   return String.format(tileImage, "");
            case WATER:  return String.format(tileImage, "");
            //case DESERT: return String.format(tileImage, "");
            //case FIRE:   return String.format(tileImage, "\uD83D\uDD25");
            default:     return "";
        }
    }
    
    public String mapItemToImage(Item item, Tile tile)
    {
        String tileImage = mapTileToColor(tile);
        switch(item)
        {
            case KEY:       return String.format(tileImage, "\uD83D\uDD11");
            case GEM:       return String.format(tileImage, "\uD83D\uDC8E");
            case SWORD:     return String.format(tileImage, "\uD83D\uDD2A");
            case SHIELD:    return String.format(tileImage, "\uD83D\uDD30");
            case BOMB:      return String.format(tileImage, "\uD83D\uDCA3");
            case HEART:     return String.format(tileImage, "\uD83D\uDC9C");
            default:        return "";
        }
    }
    
    public String mapCharacterToImage(GameCharacter creature, Tile tile)
    {
        String tileImage = mapTileToColor(tile);
        switch(creature.getType())
        { 
            case "Hero":     return String.format(tileImage, "\uD83D\uDC66");
            case "Heroine":  return String.format(tileImage, "\uD83D\uDC67");
            case "Rat":      return String.format(tileImage, "\uD83D\uDC01");
            case "Bee":      return String.format(tileImage, "\uD83D\uDC1D");
            case "Snail":    return String.format(tileImage, "\uD83D\uDC0C");
            case "Ogre":     return String.format(tileImage, "\uD83D\uDC79");
            case "Troll":    return String.format(tileImage, "\uD83D\uDC7A");
            case "Ghost":    return String.format(tileImage, "\uD83D\uDC7B");
            case "Imp":      return String.format(tileImage, "\uD83D\uDC7F");
            case "Skeleton": return String.format(tileImage, "\uD83D\uDC80");
            case "Slime":    return String.format(tileImage, "\uD83D\uDCA9");
            case "Demon":    return String.format(tileImage, "\uD83D\uDC21");
            default: return "";
        }
    }
    
    //The ground color is based upon the tile's type
    //returns the formatted string for printf command 
    public String mapTileToColor(Tile tile)
    {
        switch (tile)
        {
            case GRASS: 
            case TREE:   return ANSI_GREEN + "%s" + ANSI_RESET;
            case WALL:   return ANSI_WHITE + "%s" + ANSI_RESET;
            case FLOOR:  return "%s";
            case LAVA:   return "%s";
            case SNOW:   return "%s";
            case PINE:   return "%s";
            case WATER:  return "%s";
            //case DESERT: return "%s";
            case NONE:   return "%s"; 
            default:     return "";
        }
    }
    
    
    public String setupHeroText(Hero hero)
    {
        //Get icons for text menu
        final String heroIcon   = mapCharacterToImage(hero, Tile.NONE);
        final String lifeIcon   = mapItemToImage(Item.HEART, Tile.NONE);
        final String moneyIcon  = mapItemToImage(Item.GEM, Tile.NONE);
        final String attackIcon = mapItemToImage(Item.SWORD, Tile.NONE);
        final String defendIcon = mapItemToImage(Item.SHIELD, Tile.NONE);
        final String bombIcon   = mapItemToImage(Item.BOMB, Tile.NONE);
        final String keyIcon    = mapItemToImage(Item.KEY, Tile.NONE);
        
        //Get Hero data for text menu
        String type = hero.getType();
        int health  = hero.getHealth();
        int attack  = hero.getAttack();
        int defense = hero.getDefense();
        int bombQty = hero.getNumItems(Item.BOMB);
        int keyQty  = hero.getNumItems(Item.KEY);
        int gemQty  = hero.getNumItems(Item.GEM);
        
        String menu = "\n"; 
        //SET BACKGROUND COLOR
        menu += ANSI_BLUE;
        //CREATE TITLE LINE: Avatar & Name
        menu += ANSI_UNDERLINE_ON;
        menu += LEFT_VERTICAL_LINE;
        menu += String.format(" %s  %s  %s  ",heroIcon, type, heroIcon);
        menu += RIGHT_VERTICAL_LINE;
        menu += ANSI_UNDERLINE_OFF;
        menu += "\n";
        //CREATE SECOND MENU LINE: Life & Money
        menu += LEFT_VERTICAL_LINE;
        menu += String.format(" %s %-4s", lifeIcon, health);
        menu += String.format("%s %-4s", moneyIcon, gemQty);
        menu += RIGHT_VERTICAL_LINE;
        menu += "\n";
        //CREATE THIRD MENU LINE: Attack & Defense
        menu += LEFT_VERTICAL_LINE;
        menu += String.format(" %s %-4s", attackIcon, attack);
        menu += String.format("%s %-4s", defendIcon, defense);
        menu += RIGHT_VERTICAL_LINE;
        menu += "\n";
        //CREATE FOURTH MENU LINE: Bombs & Keys
        menu += ANSI_UNDERLINE_ON;
        menu += LEFT_VERTICAL_LINE;
        menu += String.format(" %s %-4s", bombIcon, bombQty);
        menu += String.format("%s %-4s", keyIcon, keyQty);
        menu += RIGHT_VERTICAL_LINE;
        menu +="\n";
        //RESET ALL FORMATIING
        menu += ANSI_RESET;
        
        return menu;
    }
    
    public String setupMonsterText(ArrayList<Monster> attackers)
    {
        String menu = "\n";
        for (Monster monster : attackers)
        {
        
            //Get icons for text menu
            String monsterIcon = mapCharacterToImage(monster, Tile.NONE); 
            String lifeIcon   = mapItemToImage(Item.HEART, Tile.NONE);
            
            //Get monster data for text menu
            String type = monster.getType();
            int health  = monster.getHealth(); 
            
            //SET BACKGROUND COLOR
            menu += ANSI_BLUE;
            //MONSTER MENU LINE
            menu += ANSI_UNDERLINE_ON;
            menu += LEFT_VERTICAL_LINE; 
            menu += String.format(" %s %-6s %s%s ", monsterIcon, type, lifeIcon, health);
            menu += RIGHT_VERTICAL_LINE;
            menu +="\n";
            //RESET ALL FORMATIING
            menu += ANSI_RESET;
        }
        return menu;
    }
    
    public String getAttackSound()
    {
        final String beep = "\007"; // bell ASCII char
        return beep;
    }
    
    
}
