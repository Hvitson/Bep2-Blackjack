package nl.hu.bep2.casino.blackjack.presentation.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class StartGameInfo {
    @Positive
    public Long betAmount;
    @NotNull
    public int amountOfDecks;

    public StartGameInfo(@Positive Long betAmount, @NotNull int amountOfDecks) {
        this.betAmount = betAmount;
        this.amountOfDecks = amountOfDecks;
    }

    public Long getBetAmount() {
        return betAmount;
    }

    public int getAmountOfDecks() {
        return amountOfDecks;
    }
}
