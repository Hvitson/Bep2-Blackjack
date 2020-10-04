package nl.hu.bep2.casino.blackjack.presentation.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class UserDto extends RepresentationModel<UserDto> {
    private final String username;

    public UserDto(String username) {
        this.username = username;
    }

    public String getUsername(){
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

        UserDto that = (UserDto) o;
        return username.equals(that.username);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username);
    }
}
