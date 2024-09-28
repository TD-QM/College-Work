/*
 * A representation of the "queue" system I'm using. It's present in some RPGs where characters have a speed
 *  value which determines how fast they travel down a track. After they reach the end of that track, they
 *  take their turn, and they're moved back to the start.
 * 
 * This system makes the game more interesting in my opinion, but sacrifices immediate reward/satisfaction 
 *  from the player in favor of mokre strategy. May be a bad tradeoff, idk
 * 
 */

public class ActionQueue {
    
    Object[] queue;
    int index;

    public ActionQueue(){
        queue = new Object[10];
        for(int i = 0; i < 10; i++){
            queue[i] = "~";
        }
    }

    public void advance(){
        queue[index] = "~";
        index += 1;
    }

    public void add(Card card){
        for(int i = 0; i < card.getDelay(); i++){
            queue[ (index+1)%10 ] = "+";
        }
        queue[ (index+card.getDelay())%10 ] = card;
    }

    public void add(Intent intent){
        for(int i = 0; i < intent.getDelay(); i++){
            queue[ (index+1)%10 ] = "+";
        }
        queue[ (index+intent.getDelay())%10 ] = intent;
    }

    public String toString(){
        String output = "-------------------------------------------------------------\n";
        for(int i = index; i != index-1; i = (i+1)%10 ){
            output += "|";
            if(queue[i].equals("+")){
                output += "+++++++++++";
            } else if (queue[i].instanceOf(Card)){
                output += queue[i].getShortDesc();
            }
        }

        return output;
    }

    /*
    Example Queue
    -------------------------------------------------------------
    |+++++++++++|  6dmg > 3 |+++++++++++|  6blk > 0 | 4Shrp > 0 |
    -------------------------------------------------------------
    |+++++++++++|+++++++++++|+++++++++++| 10dmg > 0 |           |
    -------------------------------------------------------------
    | 3DAdv > 0 |           |           |           |
    -------------------------------------------------------------
    */
}
