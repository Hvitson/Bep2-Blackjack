package nl.hu.bep2.casino.blackjack.domain;


import nl.hu.bep2.casino.blackjack.presentation.dto.GameResponse;

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
    private GameModes gameMode;
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

    public Game() {
    }

    public GameResponse startGamesUsing(Rules strategy) {
        return  strategy.start();
    }

    public GameResponse playGamesUsing(Rules strategy, Moves move) {
        return  strategy.move(move);
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

    public Deck getDeck() {
        return deck;
    }

    public GameModes getGameMode() {
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
                ", gameMode=" + gameMode +
                ", gameOver=" + gameOver +
                ", playerHand=" + playerHand +
                ", dealerHand=" + dealerHand +
                '}';
    }
}
