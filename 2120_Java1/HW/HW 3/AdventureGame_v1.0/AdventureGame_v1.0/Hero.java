public class Hero extends GameCharacter
{
    //instance variable(s)!
    Config config;
    private int keys;
    private int gems; 
    private int bombs;

    
    // Create a new Hero object at the specified location.
    // NOTE: x and y are indexes in the dungeon, NOT the StdDraw drawing location.  
    public Hero(Config config, int x, int y)
    {
        // TODO: code!
        super(config,x,y);
        this.keys = 0;
        this.gems = 0;
        this.bombs = 0;
        super.setType("Hero");
        super.setHealth(10);
        super.setAttack(2);
    }
    
    // Attempts to use the specified item.
    // This consumes one of the specified items (if the hero has one).
    // If successful, also plays sound effect for using the item.
    // If a given item has no sound effect, don't play anything (but don't crash).
    // Returns true if item was available, false otherwise.
    public boolean attemptToUse(Item item)
    {
        boolean haveItem;
        switch(item)
        {            
            case KEY:       if (haveItem = (this.keys  > 0)) keys--; break;
            case GEM:       if (haveItem = (this.gems  > 0)) gems--; break;
            case BOMB:      if (haveItem = (this.bombs > 0)) bombs--; break;
            default:        haveItem = false;
        }
        return haveItem;
    }
           
    // Returns how many of the specified item the hero currently has. 
    public int getNumItems(Item item)
    {
        // TODO: change how this works
        switch(item)
        {
            case KEY:  return this.keys;
            case GEM:  return this.gems;
            case BOMB: return this.bombs;
            default:  return 0;
        }
    }
        
    // Called when the hero takes an item from the dungeon.
    // Also plays sound effect for collecting this item.
    // If a given item has no sound effect, don't play anything (but don't crash).
    public void collectItem(Item item) 
    {
        // TODO: Change item handling code
        switch(item)
        {
            case KEY:   keys++; break;
            case GEM:   gems++; break;
            case HEART: setHealth(getHealth()+1); break;
            case BOMB:  bombs++; break; 
            case SWORD: setAttack(getAttack()+1); break;
        }
    }
    
    
    
}