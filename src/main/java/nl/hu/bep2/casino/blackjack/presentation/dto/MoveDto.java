package nl.hu.bep2.casino.blackjack.presentation.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class MoveDto {
    @NotNull
    private final String move;

    public MoveDto(@NotNull String move) {
        this.move = move;
    }

    public String getMove() {
        return move;
    }
}
