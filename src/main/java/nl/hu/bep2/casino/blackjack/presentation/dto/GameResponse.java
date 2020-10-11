package nl.hu.bep2.casino.blackjack.presentation.dto;

import nl.hu.bep2.casino.blackjack.domain.GameModes;
import nl.hu.bep2.casino.blackjack.domain.Hand;

import java.util.UUID;

public class GameResponse {
    private UUID id;
    private java.lang.String username;
    private Long bet;
    private Long balanceChange;
    private Hand playerHand;
    private Hand dealerHand;
    private GameModes gameMode;
    private boolean gameOver;

    public GameResponse(UUID id, java.lang.String username, Long bet, Long balanceChange, Hand playerHand,
                        Hand dealerHand, GameModes gameMode, boolean gameOver) {
        this.id = id;
        this.username = username;
        this.gameMode = gameMode;
        this.bet = bet;
        this.balanceChange = balanceChange;
        this.gameOver = gameOver;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public GameModes getGameMode() {
        return gameMode;
    }

    public Long getBet() {
        return bet;
    }

    public Long getBalanceChange() {
        return balanceChange;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    @Override
    public java.lang.String toString() {
        return "GameResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", gameMode=" + gameMode +
                ", bet=" + bet +
                ", balanceChange=" + balanceChange +
                ", gameMode=" + gameMode +
                ", gameOver=" + gameOver +
                ", playerHand=" + playerHand +
                ", dealerHand=" + dealerHand +
                '}';
    }
}