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
    private GameModes gameMode;
    @Column
    private Long bet;
    @Column
    private Long balanceChange;
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

    public Game(UUID id, java.lang.String username, Long bet, Long balanceChange, Hand playerHand,
                Hand dealerHand, Deck deck, GameModes gameMode, boolean gameOver) {
        this.id = id;
        this.username = username;
        this.gameMode = gameMode;
        this.bet = bet;
        this.balanceChange = balanceChange;
        this.gameOver = gameOver;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.deck = deck;
    }

    public Game() {
    }

    public GameResponse startGamesUsing(Rules strategy) {
        return  strategy.start();
    }

    public GameResponse playGamesUsing(Rules strategy, Moves move) {
        return  strategy.move(move);
    }

    public Hand showDealerHand() {
        if (gameOver){
            return dealerHand;
        }
        return dealerHand.showFirstCard();
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

    public Deck getDeck() {
        return deck;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setBet(Long bet) {
        this.bet = bet;
    }

    public void setBalanceChange(Long balanceChange) {
        this.balanceChange = balanceChange;
    }

    @Override
    public java.lang.String toString() {
        return "Game{" +
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
