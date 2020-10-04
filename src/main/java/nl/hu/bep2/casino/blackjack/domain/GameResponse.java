package nl.hu.bep2.casino.blackjack.domain;

import java.util.UUID;

public class GameResponse {
    private UUID id;
    private String username;
    private Long bet;

    private Hand playerHand;
    private Hand dealerHand;
    private boolean gameOver;

    public GameResponse(UUID id, String username, Long bet, Hand playerHand, Hand dealerHand, boolean gameOver) {
        this.id = id;
        this.username = username;
        this.bet = bet;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
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

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
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
                ", playerHand=" + playerHand +
                ", dealerHand=" + dealerHand +
                ", gameOver=" + gameOver +
                '}';
    }
}

