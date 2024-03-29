package nl.hu.bep2.casino.blackjack.presentation.dto;

import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.GameModes;
import nl.hu.bep2.casino.blackjack.domain.Hand;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

public class GameDto extends RepresentationModel<GameDto> {
    private final UUID id;
    private final String username;
    private final GameModes gameMode;
    private final Long bet;
    private final Long balanceChange;
    private final boolean gameOver;
    private final Hand playerHand;
    private final Hand dealerHand;

    public GameDto(Game game) {
        this.id = game.getId();
        this.username = game.getUsername();
        this.gameMode = game.getGameMode();
        this.bet = game.getBet();
        this.balanceChange = game.getBalanceChange();
        this.gameOver = game.isGameOver();
        this.playerHand = game.getPlayerHand();
        this.dealerHand = game.showDealerHand();
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameDto that = (GameDto) o;
        return username.equals(that.username) && id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, id);
    }
}
