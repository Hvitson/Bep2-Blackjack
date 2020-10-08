package nl.hu.bep2.casino.blackjack.presentation.dto;

import nl.hu.bep2.casino.blackjack.domain.GameModes;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class StartGameInfo {
    @Positive
    @NotNull
    public Long betAmount;
    @NotNull
    public int decks;
    @NotNull
    public GameModes gameMode;

    public StartGameInfo(@Positive @NotEmpty(message = "Bet cannot be empty.") Long betAmount, @NotNull int decks, @Validated GameModes gameMode) {
        this.betAmount = betAmount;
        this.decks = decks;
        this.gameMode = gameMode;
    }

    public Long getBetAmount() {
        return betAmount;
    }

    public int getDecks() {
        return decks;
    }

    public GameModes getGameMode() {
        return gameMode;
    }
}
