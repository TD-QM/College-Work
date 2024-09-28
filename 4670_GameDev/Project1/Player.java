import java.util.*;

/*
 * The Player character!
 * Has their own deck, draw pile, discard pile, and hand
 * Manages what the player has in hand, and the options that they can perform
 * 
 * TODO:
 *      - Implement buff/debuff interactions. Self-explanatory
 *      - Implement a card draw system. Would penalize the player by advancing all actions by 1 
 *          before the draw occurs. This encourages the building of bigger decks
 *      - Implement a penalty for reshuffling. 
 *      - Other things.
 * 
 */

public class Player {

    private int hp;
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private ArrayList<Card> draw;
    private ArrayList<Card> discard;
    private ArrayList<Object[]> buffs;
    private ArrayList<Object[]> debuffs;

    public Player(int hp){
        this.hp = hp;
        hand = new ArrayList<Card>();
        draw = new ArrayList<Card>();
        discard = new ArrayList<Card>();
        buffs = new ArrayList<Object[]>();
        debuffs = new ArrayList<Object[]>();
    }

    public String getHand(){
        String output = "";
        for(int i = 0; i < hand.size(); i++){
            output = "[" + i + "] " + hand.get(i).getName() + ": " + hand.get(i).getDesc() + "\n";
        }
        return output;
    }

    public void shuffleDeck(){
        while(discard.size() > 0){
            draw.add(discard.get(0));
            discard.remove(0);
        }
        while(hand.size() > 0){
            draw.add(hand.get(0));
            hand.remove(0);
        }

        for(int i = 0; i < draw.size(); i++){
            
        }

    }

    public Card playCard(int index){
        playedCard = hand.get(i);
        discard.add(playedCard);
        hand.remove(i);
        return playedCard;
    }

    public void takeDamage(int dmg){

        hp -= dmg;
    }

    public void getBuff(Buff buff, int duration, int intensity){
        Object[] addition = {buff, duration, intensity};
        buffs.add(addition);
    }

    public void getDebuff(Debuff debuff, int duration, int intensity){
        Object[] addition = {debuff, duration, intensity};
        buffs.add(addition);
    }


}
