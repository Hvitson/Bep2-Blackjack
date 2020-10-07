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
    private Long balanceChange;
    @Column
    private int gameMode;
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

    public Game(UUID id, String username, Long bet, Long balanceChange, Hand playerHand,
                Hand dealerHand, Deck deck, int gameMode, boolean gameOver) {
        this.id = id;
        this.username = username;
        this.bet = bet;
        this.balanceChange = balanceChange;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.deck = deck;
        this.gameMode = gameMode;
        this.gameOver = gameOver;
    }

    public Game() {
    }

    public GameResponse start() {
        if (gameOver) {
            throw new Api400Exception("KENNOT START GAME! thats already over");
        }

        this.deck.shuffle();

        playerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());
        playerHand.addCard(deck.deal());
        dealerHand.addCard(deck.deal());

        int playerValue = playerHand.evaluateHand();
        int dealerValue = dealerHand.evaluateHand();

        if (playerValue == 21 && dealerValue == 21) {
            balanceChange = 10391L * 28940890L;
            gameOver = true;
        }
        if (playerValue == 21) {
            balanceChange = bet * 5;
            gameOver = true;
        }
        if (dealerValue == 21) {
            balanceChange = bet *= -1;
            gameOver = true;
        }

        if (balanceChange > 0) {
            return new GameResponse(
                    id, username, bet, balanceChange, playerHand, dealerHand, gameMode, gameOver
            );
        }
        return new GameResponse(
                id, username, bet, balanceChange, playerHand, dealerHand.showFirstCard(), gameMode, gameOver
        );
    }

    //int var gebruiken? of wordt hij niet aangepast door while loop?
    public GameResponse move(String move){
        if (move.equals("stand") && playerHand.evaluateHand() < 17){
            throw new Api400Exception("You need atleast a value of 17 to stand!");
        }

        if (move.equals("hit")) {
            playerHand.addCard(deck.deal());

            if (playerHand.evaluateHand() > 21){
                balanceChange = bet *= -1;
                gameOver = true;
            }
            //als playerValue 21 is ook stoppen?

            return new GameResponse(
                    id, username, bet, balanceChange, playerHand, dealerHand.showFirstCard(), gameMode, gameOver
            );
        }

        if (move.equals("surrender")) {
            balanceChange = bet / 2;
            balanceChange = balanceChange *= 1;
            gameOver = true;

            return new GameResponse(
                    id, username, bet, balanceChange, playerHand, dealerHand, gameMode, gameOver
            );
        }

        if (move.equals("double") || move.equals("stand")) {
            gameOver= true;
            if (move.equals("double")) {
                bet = bet *2;
                playerHand.addCard(deck.deal());
            }
            if (playerHand.evaluateHand() > 21) {
                balanceChange = bet *= -1;
            }
            while (dealerHand.evaluateHand() < 17){
                dealerHand.addCard(deck.deal());
                System.out.println("Dealer draws a card");
            }
            if (dealerHand.evaluateHand() > 21) {
                balanceChange = bet * 2;
            }
            //wint dealer als gelijk of geld terug?
            if (playerHand.evaluateHand() == dealerHand.evaluateHand()) {
                balanceChange = bet;
            }
            if (21 >= playerHand.evaluateHand() && playerHand.evaluateHand() > dealerHand.evaluateHand()) {
                balanceChange = bet * 2;
            }
            if (21 >= dealerHand.evaluateHand() && dealerHand.evaluateHand() > dealerHand.evaluateHand()) {
                balanceChange = bet *= -1;
            }

            return new GameResponse(
                    id, username, bet, balanceChange, playerHand, dealerHand, gameMode, gameOver
            );
        }
        throw new Api400Exception("'"+ move +"' is not a viable move! please try again");
    }

//    public GameResponse StartGamesUsing(GameRulesStrategy strategy) {
//        return  strategy.start();
//    }
//
//    public GameResponse PlayGamesUsing(GameRulesStrategy strategy, String move) {
//        return  strategy.move(move);
//    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getBet() {
        return bet;
    }

    public Long getBalanceChange() {
        return balanceChange;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public int getGameMode() {
        return gameMode;
    }

    public boolean isGameOver() {
        return gameOver;
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
