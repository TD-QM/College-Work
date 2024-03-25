public class Card {
    Suit suit;
    Rank rank;
    int value;

    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
        switch(rank){
            case THREE:
                value = 10;
                break;
            case FOUR:
                value = 20;
                break;
            case FIVE:
                value = 30;
                break;
            case SIX:
                value = 40;
                break;
            case SEVEN:
                value = 50;
                break;
            case EIGHT:
                value = 60;
                break;
            case NINE:
                value = 70;
                break;
            case TEN:
                value = 80;
                break;
            case JACK:
                value = 90;
                break;
            case QUEEN:
                value = 100;
                break;
            case KING:
                value = 110;
                break;
            case ACE:
                value = 120;
                break;
            case TWO:
                value = 130;
                break;
            default:
                break;
        }
        switch(suit){
            case SPADES:
                value += 1;
                break;
            case CLUBS:
                value += 2;
                break;
            case DIAMONDS:
                value += 3;
                break;
            case HEARTS:
                value += 4;
                break;
            default:
        }
    }

    public boolean sameSuit(Card card2){
        return suit == card2.getSuit();
    }

    public boolean sameRank(Card card2){
        return rank == card2.getRank();
    }

    public Suit getSuit(){
        return suit;
    }

    public Rank getRank(){
        return rank;
    }

    public int getValue(){
        return value;
    }

    @Override
    public String toString(){
        return rank + " of " + suit;
    }
}
