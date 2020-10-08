package nl.hu.bep2.casino.blackjack.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand implements Serializable {
    private List<Card> cards;

    public Hand(){
        this.cards = new ArrayList<Card>();
    }

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card){
        this.cards.add(card);
    }

    public Card getCard(int i){
        return this.cards.get(i);
    }

    public void moveBackToDeck(Deck toFillDeck){
        int deckSize = this.cards.size();

        //puts cards in  to fill deck
        for (int i = 0; i < deckSize; i++){
            toFillDeck.addCard(this.getCard(i));
        }
        for (int i = 0; i < deckSize; i++){
            this.cards.remove(0);
        }
    }

    public Hand showFirstCard() {
        return new Hand(List.of(this.getCard(0)));
    }

    public Hand showLastCard() {
        return new Hand(List.of(this.getCard(this.cards.size() - 1)));
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

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                '}';
    }
}
