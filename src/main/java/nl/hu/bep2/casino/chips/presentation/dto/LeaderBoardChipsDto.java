package nl.hu.bep2.casino.chips.presentation.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class LeaderBoardChipsDto extends RepresentationModel<LeaderBoardChipsDto> {
    private final Long chipsAmount;
    private final String username;

    public LeaderBoardChipsDto(Long chipsAmount, String username) {
        this.chipsAmount = chipsAmount;
        this.username = username;
    }

    public Long getChipsAmount() {
        return chipsAmount;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeaderBoardChipsDto that = (LeaderBoardChipsDto) o;
        return chipsAmount.equals(that.chipsAmount);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chipsAmount);
    }
}