import java.io.Serializable;

public class GameCharacter implements Serializable
{
    private Config config;
    private String type;
    private int health;
    private int attack;
    private int defense;
    private int x;          //x position
    private int y;          //y position
    
    public GameCharacter(Config config, int x,int y)
    {
        this.type = "";
        this.config = config;
        this.x = x;
        this.y = y;
    }
    
    //getter: character type
    public String getType()
    {
        return this.type;
    }
    
    //setter: character type
    public void setType(String type)
    {
        this.type = type;
    }
    
    /*GETTERS*/
    public int getHealth () { return this.health;}
    public int getAttack () { return this.attack;}
    public int getDefense() { return this.defense;}
    
    /*SETTERS*/
    public void setHealth (int health ) {this.health=health;}
    public void setAttack (int attack ) {this.attack=attack;}
    public void setDefense(int defense) {this.defense=defense;}
    
    // Returns the current x-index of the hero in the dungeon. 
    public int getX()
    {
        return this.x;
    }
    
    // Returns the current y-index of the hero in the dungeon.
    public int getY()
    {
        return this.y;
    }
    
    // Move the hero to the specified x-index and y-index
    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    // Draw the Hero at its current location.
    public void draw(Tile tile)
    {
        // TODO: code! change into loadTileToBuffer, initBuffer
        StdDraw.loadTileImage(x,y, config.mapCharacterToImage(this, tile));
    
    }
    
    
    public void performAttack(GameCharacter opponent)
    {
        int damage = StdRandom.uniform(1, this.attack+1);
        opponent.takeDamage(damage);
    }
    
    //damage this game character
    //@ensure: health >= 0 after damage
    public void takeDamage(int damage)
    {
        this.health -= (damage < health) ? damage : health;
    }
    
}