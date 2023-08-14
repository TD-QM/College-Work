import java.io.Serializable;
import java.util.ArrayList;

public class Dungeon implements Serializable
{
    //instance variable(s)!
    Config config;
    int width;
    int height;
    Hero hero;
    Cell[][] map;
    ArrayList<Monster> monsters = new ArrayList<Monster>();



    // Construct a new dungeon of the specified size.
    //  1) set() to initialize each Cell in the dungeons 2D array
    //  2) addHero() to put a hero in the dungeon
    //  3) addItem() to add keys and gems to the dungeon   
    public Dungeon(Config config, int width, int height)
    {
        this.config = config;
        this.width = width;
        this.height = height;
        this.map = new Cell[width][height];            
    }
     
    // Adds the specified hero to the dungeon
    public void addHero(Hero hero)
    {
        this.hero = hero;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Cell[][] getMap() {
        return this.map;
    }

    public Hero getHero() {
        return this.hero;
    }
    
    public void addMonster(Monster monster)
    {
        this.monsters.add(monster);
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }


    // Setup a given grid location based on a string:
    //  "W" = wall
    //  "S" = secret passage
    //  "D" = closed door
    //  "O" = open door
    //  "-" = dirt floor
    public void set(int cellX, int cellY, String str)
    {
        switch(str)
        {
            case "T": this.map[cellX][cellY] = new Cell(Tile.TREE); break;
            //case "S": this.map[cellX][cellY] = new CellPassage(this.config.wallImage()); break;
            //case "D": this.map[cellX][cellY] = new CellDoor(this.config.openDoorImage(), this.config.closedDoorImage(), true); break;
            //case "O": this.map[cellX][cellY] = new CellDoor(this.config.openDoorImage(), this.config.closedDoorImage(), false); break;
            case ".": this.map[cellX][cellY] = new CellPassage(Tile.GRASS); break;
        }
    }
    
    
    // Add the specified item to a random location in the dungeon.
    // The item can only be added to a cell that is passable (i.e.
    // not a wall or a locked door). The location must not already
    // have an item and items can't be added on top of the hero.     
    public void addItem(Item item, int x, int y)
    {
        map[x][y].attemptToAdd(item);
        /*
        boolean hasAdded = false;
        do
        {
            int randX = StdRandom.uniform(this.width);
            int randY = StdRandom.uniform(this.height);
            if (randX != hero.getX() || randY != hero.getY() )
            {
                hasAdded = map[randX][randY].attemptToAdd(item);
            }
        }while(!hasAdded);
        */
    }
    
    
    // Make the Hero execute the specified action.
    // Currently this is attempting to move north, south, east, or west.
    // If the action would take the hero into combat then play the battle sound.
    public void execute(Action action)
    {     
        int x = this.hero.getX();
        int y = this.hero.getY();
        switch(action)
        {
            case SOUTH: y = (y+1 < this.height) ? y+1 : y;
                        break;

            case NORTH: y = (y-1 >= 0) ? y-1 : y; 
                        break;

            case EAST:  x = (x+1 < this.width) ? x+1 : x; 
                        break;

            case WEST:  x = (x-1 >= 0) ? x-1 : x; 
                        break;
                
            case INVALID:
        }
        if (map[x][y].attemptToMove(this.hero) ) 
        { 
            if (isMonsterOnCell(x,y))
            {
                Monster enemy = getMonsterOnCell(x,y);
                hero.performAttack(enemy);
                removeDeadMonsters();
                StdAudio.playSound(config.getAttackSound());
            }
            else
            {
                hero.setLocation(x,y);
            }
            
            moveMonsters();
        }
    }
    
    //helper method checks if monster is on tile
    private boolean isMonsterOnCell(int x, int y)
    {
        return (getMonsterOnCell(x,y) != null);
    }
    
    //helper method returns monster at specified position
    private Monster getMonsterOnCell(int x, int y)
    {
        for (Monster monster : monsters)
        {
            if (x==monster.getX() && y == monster.getY())
            {
                return monster;
            }
        }
        return null;
    }
    
    
    
    // Draw all the cell tiles that make up the dungeon.
    // Also draws the hero.
    public void draw()
    {       
        // Draw game map!
        StdDraw.initGameMap(this.width, this.height);
        for (int i=0;i<this.width;i++)
        {
            for (int j=0;j<this.height;j++)
            {
                map[i][j].draw(this.config, i,j);
            }
        }
        //Draw hero
        Tile heroTile = map[hero.getX()][hero.getY()].getTile();  //Get background tile
        hero.draw(heroTile);                                      //Draw hero ontop that background
        
        //Draw monsters
        for (Monster creature: monsters)
        {
            Tile monsterTile = map[creature.getX()][creature.getY()].getTile();  //Get background tile  
            creature.draw(monsterTile);
        }
        //Render display
        StdDraw.draw(); //change to call called render
    }
    
    private boolean isHeroAdjacent(Monster monster)
    {
        int x = monster.getX();
        int y = monster.getY();
        int dx = hero.getX();
        int dy = hero.getY();
        return (Math.abs(dx-x) + Math.abs(dy-y) == 1); 
        
    }
    
    public ArrayList<Monster> getAdjacentMonsters()
    {
        ArrayList<Monster> adjacentMonsters = new ArrayList<Monster>();
        for (Monster monster : monsters)
        {
            if (isHeroAdjacent(monster))
            {
                adjacentMonsters.add(monster);
            }
        }
        return adjacentMonsters;
    }
    
    public void removeDeadMonsters()
    {
        int index = 0;
        while (index < monsters.size())
        {
            Monster monster = monsters.get(index);
            if (monster.getHealth() <= 0)
            {
                addItem(monster.getLoot(), monster.getX(), monster.getY());
                monsters.remove(index);
            }
            index++;
        }
    }
    
    public void moveMonsters()
    {
        for (Monster monster : monsters)
        {
            if (isHeroAdjacent(monster))
            {
                monster.performAttack(hero);
            }
            else
            {
                int x = monster.getX();
                int y = monster.getY();
                int direction = StdRandom.uniform(4);
                switch(direction)
                {
                    case 0: y = (y+1 < this.height) ? y+1 : y; break; //SOUTH
                    case 1: y = (y-1 >= 0) ? y-1 : y; break;          //NORTH
                    case 2: x = (x+1 < this.width) ? x+1 : x; break;  //EAST
                    case 3: x = (x-1 >= 0) ? x-1 : x; break;          //WEST
                }
                if (map[x][y].attemptToMove(monster) && !(isMonsterOnCell(x,y))) 
                {
                    monster.setLocation(x,y);
                }//end if
            }//end else
        }//end for
    }//end method
    
}

