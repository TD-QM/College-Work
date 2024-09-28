import java.util.*;

/*
 * The enemy!
 * An abstract class that houses a bunch of general things that an enemy can do such as attack, 
 *  buff themselves/allies, and debuff the player
 * 
 * TODO:
 *      - Literally everything
 * 
 */


public abstract class Enemy {
    
    int hp;
    Intent[] actions;
    private ArrayList<Object[]> buffs;
    private ArrayList<Object[]> debuffs;


    public Enemy(){

    }

    public void takeDamage(Player p, Card card, int dmg){

    }

    public Intent intention(){
        return actions[ Math.random()*actions.length ];
    }
}
