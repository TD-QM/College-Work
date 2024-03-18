import java.util.Random;

public class Deck{
	Card[] draw;
    Card[] discard;
    int drawPile;
    int discardPile;

    public Deck(){
        draw = new Card[52];
        int index = 0;
        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                draw[index] = new Card(suit, rank);
                index++;
            }
        }

        discard = new Card[52];
        drawPile = 52;
        discardPile = 0;
    }

    public void shuffle(){
        for(int i = drawPile-1; i > 0; i--){
            int rand = (int) (Math.random()*i);
            Card temp = draw[rand];
            draw[rand] = draw[i];
            draw[i] = temp;
        }
    }

    @Override
    public String toString(){
        String returnVal = "";

        returnVal += "Draw: ";

        for(int i = drawPile-1; i > 0; i--){
            returnVal += draw[i].toString() + ", ";
        }
        if(drawPile > 0){
            returnVal += draw[0].toString();
        }

        returnVal += "\nDiscard: ";
        for(int i = discardPile-1; i > 0; i--){
            returnVal += discard[i].toString() + ", ";
        }
        if(discardPile > 0){
            returnVal += discard[0].toString();
        }

        return returnVal;
    }

    public Card draw(){
        Card drawn = draw[drawPile-1];
        draw[drawPile-1] = null;
        drawPile -= 1;

        return drawn;
    }
}
