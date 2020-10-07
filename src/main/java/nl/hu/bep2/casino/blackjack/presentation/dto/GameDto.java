package nl.hu.bep2.casino.blackjack.presentation.dto;

import nl.hu.bep2.casino.blackjack.domain.Hand;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

public class GameDto extends RepresentationModel<GameDto> {
    private final String username;
    private final UUID id;
    private final Long bet;
    private final Hand playerHand;
    private final Hand dealerHand;
    private final boolean gameOver;

    public GameDto(String username, UUID id, Long bet, Hand playerHand, Hand dealerHand, boolean gameOver) {
        this.username = username;
        this.id = id;
        this.bet = bet;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.gameOver = gameOver;
    }

    public String getUsername() {
        return username;
    }

    public UUID getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameDto that = (GameDto) o;
        return username.equals(that.username) && id.equals(that.id) && bet.equals(that.bet);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, id, bet, playerHand, dealerHand, gameOver);
    }
}
