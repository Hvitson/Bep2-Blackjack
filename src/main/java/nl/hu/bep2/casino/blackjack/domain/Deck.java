package nl.hu.bep2.casino.blackjack.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    //misschien d.m.v. builder
    public Deck(){
        this.cards = new ArrayList<Card>();
    }

    public void createDeck() {
        System.out.println("Creating an deck!");
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.cards.add(new Card(suit,rank));
            }
            //optioneel: Nu uit zodat testen makkelijker
            //this.shuffle();
        }
    }

    //schudden van de kaarten nu automaties bij het aanmaken van een deck
    public void shuffle(){
        Collections.shuffle(this.cards);
    }

    //take upper card from deck
    public Card deal(){
        Card card = this.cards.get(0);
        this.cards.remove(0);
        return card;
    }

    //adds card to deck"hand"
    public void addCard(Card card){
        this.cards.add(card);
    }

    public int evaluateHand(){
        int handValue = 0;
        int acesValue = 0;

        for (Card card : this.cards) {
            switch(card.getRank()){
                case Ace:
                    acesValue += 1; break;
                case Two:
                    handValue += 2; break;
                case Three:
                    handValue += 3; break;
                case Four:
                    handValue += 4; break;
                case Five:
                    handValue += 5; break;
                case Six:
                    handValue += 6; break;
                case Seven:
                    handValue += 7; break;
                case Eight:
                    handValue += 8; break;
                case Nine:
                    handValue += 9; break;
                case Ten:
                    handValue += 10; break;
                case Jack:
                    handValue += 10; break;
                case Queen:
                    handValue += 10; break;
                case King:
                    handValue += 10; break;
            }
        }
        for (int i=0; i < acesValue; i++){
            if (handValue > 10){
                handValue += 1;
            }
            else{
                handValue += 11;
            }
        }

        return handValue;
    }

    //"i" weg halen als af
    //laat deck inhoud zien
    @Override
    public String toString() {
        String cardsInDeck = "";
        int i = 0;
        for (Card card : this.cards){
            cardsInDeck += "\n" + i + " : " + card.toString();
            i++;
        }
        return cardsInDeck;
    }
}