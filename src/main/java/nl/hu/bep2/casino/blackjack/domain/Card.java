package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;

public class Card implements Serializable {
    private final Suit suit;
    private final Rank rank;

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

    public String convertSuitToIcon(Suit suit){
        String convertedSuit = "";
        switch (suit){
            case Diamonds:
                convertedSuit = "♦"; break;
            case Clubs:
                convertedSuit = "♣"; break;
            case Hearts:
                convertedSuit = "♥"; break;
            case Spades:
                convertedSuit = "♠"; break;
        }
        return convertedSuit;
    }
    public String convertRankToIcon(Rank rank){
        String convertedRank = "";
        switch (rank){
            case Ace:
                convertedRank = "A"; break;
            case Two:
                convertedRank = "2"; break;
            case Three:
                convertedRank = "3"; break;
            case Four:
                convertedRank = "4"; break;
            case Five:
                convertedRank = "5"; break;
            case Six:
                convertedRank = "6"; break;
            case Seven:
                convertedRank = "7"; break;
            case Eight:
                convertedRank = "8"; break;
            case Nine:
                convertedRank = "9"; break;
            case Ten:
                convertedRank = "10"; break;
            case Jack:
                convertedRank = "J"; break;
            case Queen:
                convertedRank = "Q"; break;
            case King:
                convertedRank = "K"; break;
        }
        return convertedRank;
    }

    public String toString(){
        return convertSuitToIcon(this.suit) + convertRankToIcon(this.rank);
    }
}
