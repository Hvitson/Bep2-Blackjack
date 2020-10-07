package nl.hu.bep2.casino.blackjack.domain.deckfillfactory;

import nl.hu.bep2.casino.blackjack.domain.Card;
import nl.hu.bep2.casino.blackjack.domain.Deck;
import nl.hu.bep2.casino.blackjack.domain.Rank;
import nl.hu.bep2.casino.blackjack.domain.Suit;

import java.util.List;

public class AmountOfDecksFactory {
    private int amountOfDecks;

    public AmountOfDecksFactory(int amountOfDecks) {
        this.amountOfDecks = amountOfDecks;
    }

    public Deck createDeck() {
        Deck deck = new Deck();
        for (int i = 0; i < amountOfDecks; i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    deck.addCard(new Card(suit,rank));
                }
            }
        }
        return deck;
    }
}
