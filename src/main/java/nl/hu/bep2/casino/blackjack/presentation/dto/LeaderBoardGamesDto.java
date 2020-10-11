package nl.hu.bep2.casino.blackjack.presentation.dto;

import nl.hu.bep2.casino.security.data.User;

import java.util.Objects;

public class LeaderBoardGamesDto {
    private final String username;
    private final int gamesPlayed;

    public LeaderBoardGamesDto(String username, int gamesPlayed) {
        this.username = username;
        this.gamesPlayed = gamesPlayed;
    }

    public String getUsername() {
        return username;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeaderBoardGamesDto that = (LeaderBoardGamesDto) o;
        return username.equals(that.username);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username);
    }
}
