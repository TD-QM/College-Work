/*
 * How the player talks with the enemy
 * 
 * TODO:
 *      - A lot
 *      - Make basic cards, worry about buff and debuff application later
 */

public abstract class Card {
    
    private String name;
    private int delay;
    private String desc;
    private String shortDesc;
    private CardType type;

    public Card(String name, int delay, String desc, String shortDesc, CardType type){
        this.name = name;
        this.delay = delay;
        this.desc = desc;
        this.shortDesc = shortDesc;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public String getDesc(){
        return desc;
    }

    public String getShortDesc(){
        return shortDesc;
    }

    public int getDelay(){
        return delay;
    }

    public CardType getType(){
        return type;
    }

    public void doEffect(Player p, Enemy[] e, int target);


}
