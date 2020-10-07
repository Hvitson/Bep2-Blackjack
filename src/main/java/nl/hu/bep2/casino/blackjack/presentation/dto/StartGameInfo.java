package nl.hu.bep2.casino.blackjack.presentation.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class StartGameInfo {
    @Positive
    public Long betAmount;
    @NotNull
    public int amountOfDecks;
    @NotNull
    public int fakeReal;

    public StartGameInfo(@Positive Long betAmount, @NotNull int amountOfDecks, @NotNull int fakeReal) {
        this.betAmount = betAmount;
        this.amountOfDecks = amountOfDecks;
        this.fakeReal = fakeReal;
    }

    public Long getBetAmount() {
        return betAmount;
    }

    public int getAmountOfDecks() {
        return amountOfDecks;
    }

    public int getFakeReal() {
        return fakeReal;
    }
}
