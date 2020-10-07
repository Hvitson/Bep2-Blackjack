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

        int playerValue = playerHand.evaluateHand();
        int dealerValue = dealerHand.evaluateHand();

        if (move.equals("hit")) {
            playerHand.addCard(deck.deal());

            if (playerValue > 21){
                bet = 0L;
                gameOver = true;
            }
            //als playerValue 21 is ook stoppen?

            return new GameResponse(
                    id, username, bet, playerHand, dealerHand.showFirstCard(), gameOver
            );
        }
        if (move.equals("stand")) {
            if (dealerValue > )

            gameOver = true;
        }
        if (move.equals("surrender")) {
            gameOver = true;
                return new GameResponse(
                        id, username, bet, playerHand, dealerHand.showFirstCard(), gameOver
                );
        }
        if (move.equals("double")) {
            bet = bet *2;
                playerHand.addCard(deck.deal());

                return new GameResponse(
                        id, username, bet, playerHand, dealerHand.showFirstCard(), gameOver
                );
        }
//        switch (move){
//            case HIT:
//                playerHand.addCard(deck.deal());
//
//                int playerValue = playerHand.evaluateHand();
//
//                if (playerValue > 21){
//                    bet = 0L;
//                    gameOver = true;
//                }
//                //als playerValue 21 is ook stoppen?
//
//                return new GameResponse(
//                        id, username, bet, playerHand, dealerHand.showFirstCard(), gameOver
//                );
//                break;
//            case STAND:
//                //todo: dealer laten spelen
//                System.out.println(dealerHand);break;
//            case SURRENDER:
//                gameOver = true;
//                return new GameResponse(
//                        id, username, bet, playerHand, dealerHand.showFirstCard(), gameOver
//                );break;
//            case DOUBLE:
//                bet = bet *2;
//                playerHand.addCard(deck.deal());
//
//                return new GameResponse(
//                        id, username, bet, playerHand, dealerHand.showFirstCard(), gameOver
//                );break;
//        }
        throw new Api400Exception("This is not a move!");
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
