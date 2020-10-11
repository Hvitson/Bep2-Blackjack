package nl.hu.bep2.casino.blackjack.presentation.dto;

import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.GameModes;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

public class AllGamesDto extends RepresentationModel<AllGamesDto> {
    private final UUID id;
    private final GameModes gameMode;
    private final Long bet;
    private final Long balanceChange;
    private final boolean gameOver;

    public AllGamesDto(Game game) {
        this.id = game.getId();
        this.gameMode = game.getGameMode();
        this.bet = game.getBet();
        this.balanceChange = game.getBalanceChange();
        this.gameOver = game.isGameOver();
    }

    public UUID getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AllGamesDto that = (AllGamesDto) o;
        return id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
