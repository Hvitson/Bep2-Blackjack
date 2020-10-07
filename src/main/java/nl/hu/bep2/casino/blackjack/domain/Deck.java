package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable {
    private List<Card> cards;

    //misschien d.m.v. builder
    public Deck(){
        this.cards = new ArrayList<Card>();
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

//    public void fill() {
//        for (Suit suit : Suit.values()) {
//            for (Rank rank : Rank.values()) {
//                this.cards.add(new Card(suit,rank));
//            }
//        }
//    }

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

    public Card getCard(int i){
        return this.cards.get(i);
    }

    public List<Card> getCards() {
        return cards;
    }

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