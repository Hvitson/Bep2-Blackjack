package nl.hu.bep2.casino.blackjack.domain.actualgamerules;

import nl.hu.bep2.casino.blackjack.domain.*;
import nl.hu.bep2.casino.blackjack.presentation.dto.GameResponse;
import nl.hu.bep2.casino.exceptions.apirequest.Api400Exception;

import java.util.UUID;

public class BlackJack31Rules implements Rules {
    private UUID id;
    private String username;
    private Long bet;
    private Long balanceChange;
    private GameModes gameMode;
    private boolean gameOver;
    private Hand playerHand;
    private Hand dealerHand;
    private Deck deck;

    public BlackJack31Rules(UUID id, String username, Long bet, Long balanceChange, Hand playerHand,
                            Hand dealerHand, Deck deck, GameModes gameMode, boolean gameOver) {
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

    //boven 31 bust
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

        if (playerValue == 31 && dealerValue == 31) {
            balanceChange = bet;
            gameOver = true;
        }
        if (playerValue == 31) {
            balanceChange = bet * 5;
            gameOver = true;
        }
        if (dealerValue == 31) {
            balanceChange = bet * -1;
            gameOver = true;
        }
        if (gameOver = true) {
            return new GameResponse(
                    id, username, bet, balanceChange, playerHand, dealerHand, gameMode, gameOver
            );
        }
        return new GameResponse(
                id, username, bet, balanceChange, playerHand, dealerHand.showFirstCard(), gameMode, gameOver
        );
    }

    //int var gebruiken? of wordt hij niet aangepast door while loop?
    public GameResponse move(Moves move){
        if (move.equals(Moves.Stand) && playerHand.evaluateHand() < 27){
            throw new Api400Exception("You need atleast a value of 27 to stand!");
        }

        if (move.equals(Moves.Hit)) {
            playerHand.addCard(deck.deal());

            if (playerHand.evaluateHand() > 31){
                balanceChange = bet * -1;
                gameOver = true;
            }
            if (playerHand.evaluateHand() == 31){
                balanceChange = bet * 2;
                gameOver = true;
            }
            //als playerValue 31 is ook stoppen?

            if (gameOver = true) {
                return new GameResponse(
                        id, username, bet, balanceChange, playerHand, dealerHand, gameMode, gameOver
                );
            }

            return new GameResponse(
                    id, username, bet, balanceChange, playerHand, dealerHand.showFirstCard(), gameMode, gameOver
            );
        }

        if (move.equals(Moves.Surrender)) {
            balanceChange = bet / 2;
            balanceChange *= 1;
            gameOver = true;

            return new GameResponse(
                    id, username, bet, balanceChange, playerHand, dealerHand, gameMode, gameOver
            );
        }

        if (move.equals(Moves.Double) || move.equals(Moves.Stand)) {
            gameOver= true;
            if (move.equals(Moves.Double)) {
                bet = bet *2;
                playerHand.addCard(deck.deal());
            }
            while (dealerHand.evaluateHand() < 27){
                dealerHand.addCard(deck.deal());
                System.out.println("Dealer draws a card");
            }
            if (dealerHand.evaluateHand() > 31) {
                balanceChange = bet * 2;
            }
            if (playerHand.evaluateHand() == dealerHand.evaluateHand()) {
                balanceChange = bet;
            }
            if (31 >= playerHand.evaluateHand() && playerHand.evaluateHand() > dealerHand.evaluateHand()) {
                balanceChange = bet * 2;
            }
            if (31 >= dealerHand.evaluateHand() && dealerHand.evaluateHand() > dealerHand.evaluateHand()) {
                balanceChange = bet * -1;
            }

            return new GameResponse(
                    id, username, bet, balanceChange, playerHand, dealerHand, gameMode, gameOver
            );
        }
        throw new InvalidStateException("'"+ move +"' is not a viable move! please try again");
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

    public Long getBalanceChange() {
        return balanceChange;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Enum getGameMode() {
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
