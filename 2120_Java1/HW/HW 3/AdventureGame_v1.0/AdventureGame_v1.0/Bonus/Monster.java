public class Monster extends GameCharacter
{
    //instance variable(s)!
    private Item loot;
    
    
    // Create a new Monster object at the specified location.
    // NOTE: x and y are indexes in the dungeon, NOT the StdDraw drawing location.  
    public Monster(Config config, int x, int y)
    {
        // TODO: code!
        super(config,x,y);
        super.setType("Imp");
        super.setHealth(2);
        super.setAttack(2);
        this.setLoot();
    }
    
    //Set random loot based on monster
    public void setLoot()
    {
        int randomDrop = StdRandom.uniform(20);
        if      (randomDrop < 10) this.loot = Item.NONE;
        else if (randomDrop < 15) this.loot = Item.HEART;
        else if (randomDrop < 18) this.loot = Item.GEM;
        else if (randomDrop < 19) this.loot = Item.BOMB;
        else if (randomDrop < 20) this.loot = Item.SWORD;
    }
    
    public Item getLoot() {return this.loot;}
    
    //Some monster get special attacks or movements
    
    //Check if player is in range for combat
    
    //move monster
    
    //attack (Creature)
}