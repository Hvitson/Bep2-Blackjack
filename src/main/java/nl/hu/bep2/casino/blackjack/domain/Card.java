package nl.hu.bep2.casino.blackjack.domain;

public class Card {
    private final Suit suit;
    private final Rank rank;


    //aanmaken card Card card = new Card(Suit.Hearts,Rank.Ten)
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public String toString(){
        return this.suit.toString() + " : " + this.rank.toString();
    }
}
