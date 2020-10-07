package nl.hu.bep2.casino.blackjack.domain;


import nl.hu.bep2.casino.exceptions.apirequest.Api400Exception;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "games")
public class Game {
   @Id
    private UUID id;
   @Column
    private String username;
    @Column
    private Long bet;
    @Column
    private boolean gameOver;
    @Lob
    @Column
    private Hand playerHand;
    @Lob
    @Column
    private Hand dealerHand;
    @Lob
    @Column
    private Deck deck;

    public Game(UUID id, String username, Long bet, Hand playerHand, Hand dealerHand, Deck deck, boolean gameOver) {
        this.id = id;
        this.username = username;
        this.bet = bet;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.deck = deck;
        this.gameOver = gameOver;
    }

    public Game() {
    }

    public GameResponse start() {
        if (gameOver) {
            throw new InvalidStateException("KENNOT START GAME! thats already over");
        }

        this.deck.shuffle();

        playerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());
        playerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());

        int playerValue = playerHand.evaluateHand();
        int dealerValue = dealerHand.evaluateHand();

        if (playerValue == 21 && dealerValue == 21) {
            gameOver = true;
        } else if (playerValue == 21) {
            bet = this.bet * 5;
            gameOver = true;
        } else if (dealerValue == 21) {
            bet = 0L;
            gameOver = true;
        }

        return new GameResponse(
                id, username, bet, playerHand, dealerHand.showFirstCard(), gameOver
        );
    }

    public GameResponse move(String move){
        if (move.equals("hit")) {
            playerHand.addCard(deck.deal());

            if (playerHand.evaluateHand() > 21){
                bet = 0L;
                gameOver = true;
            }
            //als playerValue 21 is ook stoppen?

            return new GameResponse(
                    id, username, bet, playerHand, dealerHand.showFirstCard(), gameOver
            );
        }

        if (move.equals("surrender")) {
            bet = bet / 2;
            gameOver = true;

            return new GameResponse(
                    id, username, bet, playerHand, dealerHand, gameOver
            );
        }

        if (move.equals("double") || move.equals("stand")) {
            if (move.equals("double")) {
                bet = bet *2;
                playerHand.addCard(deck.deal());
            }

            while (dealerHand.evaluateHand() < 17){
                dealerHand.addCard(deck.deal());
                System.out.println("Dealer draws card");
            }
            if (dealerHand.evaluateHand() > 21) {
                bet = bet * 2;
                gameOver = true;
            }
            //wint dealer als gelijk of geld terug?
            if (playerHand.evaluateHand() == dealerHand.evaluateHand()) {
                gameOver = true;
            }
            if (21 >= playerHand.evaluateHand() && playerHand.evaluateHand() > dealerHand.evaluateHand()) {
                bet = bet * 2;
                gameOver = true;
            }

            return new GameResponse(
                    id, username, bet, playerHand, dealerHand, gameOver
            );
        }
        throw new Api400Exception("'"+ move +"' is not a viable move! please try again");
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getBet() {
        return bet;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", bet=" + bet +
                ", gameOver=" + gameOver +
                ", playerHand=" + playerHand +
                ", dealerHand=" + dealerHand +
                '}';
    }
}
