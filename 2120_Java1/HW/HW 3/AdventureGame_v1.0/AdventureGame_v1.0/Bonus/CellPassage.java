public class CellPassage extends Cell
{
    //instance variable(s)!
    private Item item;
    
    // Create a new cell in the dungeon with the specified tile.
    // The CellPassage class represents a passable part of the dungeon.
    public CellPassage(Tile tile)
    {
        // TODO: code!
        super(tile);
        this.item = Item.NONE;
    }
    
    // Attempts to add the specified item to this cell.
    // A CellPassage can store at most one item.
    // Returns true if item was added, false otherwise.
    @Override
    public boolean attemptToAdd(Item item)
    {
        // TODO: code!
        boolean result = false;
        if (this.item == Item.NONE) 
        {
            this.item = item;
            result = true;
        }
        return result;
    }

    // Attempt to move the specified character to this location.
    // Also collect any item present at this location.
    // Since the CellPassage class is always passable, this always returns true.
    @Override
    public boolean attemptToMove(GameCharacter character)
    {        
        if (character instanceof Hero)
        {
            Hero hero = (Hero) character;
            hero.collectItem(this.item);
            this.item = Item.NONE;
        }
        return true;
    }
    
    
    // Draw at the specified position in the dungeon.
    // Also draws any item contained at this location.
    // NOTE: x and y are indexes in the dungeon, NOT the StdDraw drawing location.
    //@Override
    public void draw(Config config, int x, int y)
    {
        StdDraw.loadTileImage(x,y, config.mapTileToImage(this.tile));
        if (this.item != Item.NONE)
            StdDraw.loadTileImage(x,y, config.mapItemToImage(this.item, this.tile));
    }

    //check if item is in cell passage 
    //@Override
    public boolean containsItem(Item item)
    {
        return (this.item == item);
    }
    
}