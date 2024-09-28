/*
 * The "Intent" system that Slay the Spire (and Wildfrost) has
 * This is essentially the enemies' version of cards
 * 
 * TODO:
 *      - Everything
 */

public abstract class Intent {
    
    private String name;
    private int delay;
    private String desc;
    private CardType type;

    public Card(String name, int delay, String desc, CardType type){
        this.name = name;
        this.delay = delay;
        this.desc = desc;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public int getDelay(){
        return delay;
    }

    public CardType getType(){
        return type;
    }

    public void doEffect(Player p, Enemy[] e, int target);


}