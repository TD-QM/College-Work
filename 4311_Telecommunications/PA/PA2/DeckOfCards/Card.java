public class Card {
    Suit suit;
    Rank rank;

    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
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

    @Override
    public String toString(){
        return rank + " of " + suit;
    }
}
