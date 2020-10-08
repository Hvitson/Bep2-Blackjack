package nl.hu.bep2.casino.blackjack.presentation.dto;

import nl.hu.bep2.casino.blackjack.domain.GameModes;
import nl.hu.bep2.casino.blackjack.domain.Hand;

import java.util.UUID;

public class GameResponse {
    private UUID id;
    private String username;
    private Long bet;
    private Long balanceChange;
    private Hand playerHand;
    private Hand dealerHand;
    private GameModes gameMode;
    private boolean gameOver;

    public GameResponse(UUID id, String username, Long bet, Long balanceChange, Hand playerHand,
                        Hand dealerHand, GameModes gameMode, boolean gameOver) {
        this.id = id;
        this.username = username;
        this.bet = bet;
        this.balanceChange = balanceChange;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.gameMode = gameMode;
        this.gameOver = gameOver;
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

    public GameModes getGameMode() {
        return gameMode;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public String toString() {
        return "GameResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", bet=" + bet +
                ", balanceChange=" + balanceChange +
                ", playerHand=" + playerHand +
                ", dealerHand=" + dealerHand +
                ", gameOver=" + gameOver +
                '}';
    }
}

