import java.io.Serializable;

public class Cell implements Serializable
{
    // instance variable(s)!
    protected Tile tile;
    
    // Create a new cell in the dungeon with the specified tile.
    // The Cell base class represents an impassable part of the dungeon.
    public Cell(Tile tile)
    {
        this.tile = tile;
    }

    
    // Attempts to add the specified item to this cell.
    // Since the Cell class is impassable, it can never store items.
    // Returns true if item was added, false otherwise.
    public boolean attemptToAdd(Item item)
    {
        return false;          
    }

    // Attempt to move the specified character to this location.
    // Since the Cell class is impassable, this always fails.
    public boolean attemptToMove(GameCharacter character)
    {        
        return false;
    }
    
    // Draw at the specified position in the dungeon.
    // NOTE: x and y are indices in the dungeon, NOT neccsarily the StdDraw drawing location. 
    public void draw(Config config, int x, int y)
    {
        StdDraw.loadTileImage(x, y, config.mapTileToImage(tile));
    }
    
    //Returns the Tile for this cell
    public Tile getTile()
    {
        return tile;
    }
    

}
