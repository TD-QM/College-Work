import java.util.*;

// Provides the defaults of sizes, sounds, and images for a game.
// Also controls how keyboard input is mapped to an action in the game.
// By subclassing this class, you could create a new "skin" for the game.


public class ConfigASCII extends Config
{
    // Maps a specified cell tile type to the image that is used to display it
    public String mapTileToImage(Tile tile)
    {
        switch (tile)
        {
            case GRASS:  return "- ";  
            case TREE:   return "# "; 
            case FLOOR:  return "- ";
            case WALL:   return "# ";
            case LAVA:   return "~ ";
            case SNOW:   return "- ";
            case PINE:   return "# ";
            case WATER:  return "~ ";
            //case DESERT: return String.format(tileImage, "");
            //case FIRE:   return String.format(tileImage, "\uD83D\uDD25");
            default:     return "";
        }
    }
    
    public String mapItemToImage(Item item, Tile tile)
    {
        switch(item)
        {
            case KEY:       return "k ";
            case GEM:       return "$ ";
            case SWORD:     return "a ";
            case SHIELD:    return "d ";
            case BOMB:      return "b ";
            case HEART:     return "h ";
            default:        return "  ";
        }
    }
    
    public String mapCharacterToImage(GameCharacter creature, Tile tile)
    {
        switch(creature.getType())
        { 
            case "Hero":     return "@ ";
            case "Heroine":  return "@ ";
            case "Rat":      return "R ";
            case "Bee":      return "B ";
            case "Snail":    return "S ";
            case "Ogre":     return "O ";
            case "Troll":    return "T ";
            case "Ghost":    return "G ";
            case "Imp":      return "I ";
            case "Skeleton": return "X ";
            case "Slime":    return "S ";
            case "Demon":    return "D ";
            default: return "  ";
        }
    }
    
    
    public String setupHeroText(Hero hero)
    {
        //Get icons for text menu
        final String heroIcon   = mapCharacterToImage(hero, Tile.NONE);
        
        //Get Hero data for text menu
        String type = hero.getType();
        int health  = hero.getHealth();
        int attack  = hero.getAttack();
        int defense = hero.getDefense();
        int bombQty = hero.getNumItems(Item.BOMB);
        int keyQty  = hero.getNumItems(Item.KEY);
        int gemQty  = hero.getNumItems(Item.GEM);
        
        String menu = "\n"; 
        //CREATE TITLE LINE: Avatar & Name
        menu += String.format(" [   %s: %s  ]",type, heroIcon);
        menu += "\n";
        //CREATE SECOND MENU LINE: Life & Money
        menu += String.format(" %-4s %-4s", "LIFE", health);
        menu += String.format("%-4s %-4s","GOLD", gemQty);
        menu += "\n";
        //CREATE THIRD MENU LINE: Attack & Defense
        menu += String.format(" %-4s %-4s", "ATK", attack);
        menu += String.format("%-4s %-4s", "DEF", defense);
        menu += "\n";
        //CREATE FOURTH MENU LINE: Bombs & Keys
        menu += String.format(" %-4s %-4s", "BOMB", bombQty);
        menu += String.format("%-4s %-4s", "KEY", keyQty);
        menu +="\n";
        return menu;
    }
    
    public String setupMonsterText(ArrayList<Monster> attackers)
    {
        String menu = "\n";
        for (Monster monster : attackers)
        {
        
            //Get icons for text menu
            String monsterIcon = mapCharacterToImage(monster, Tile.NONE); 
            
            //Get monster data for text menu
            String type = monster.getType();
            int health  = monster.getHealth(); 
            
            //MONSTER MENU LINE
            menu += String.format("[ %s:%s, LIFE:%s ]", monsterIcon,type, health);
            menu +="\n";
        }
        return menu;
    }
    
    public String getAttackSound()
    {
        final String beep = "\007"; // bell ASCII char
        return beep;
    }
    
    
}
