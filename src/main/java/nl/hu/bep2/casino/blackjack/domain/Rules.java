package nl.hu.bep2.casino.blackjack.domain;

import nl.hu.bep2.casino.blackjack.presentation.dto.GameResponse;

public interface Rules{
    GameResponse start();
    GameResponse move(Moves move);
}
