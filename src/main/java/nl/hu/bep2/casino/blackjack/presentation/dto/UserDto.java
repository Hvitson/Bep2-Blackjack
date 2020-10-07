package nl.hu.bep2.casino.blackjack.presentation.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

public class UserDto extends RepresentationModel<UserDto> {
    private final String username;
    private final UUID id;


    public UserDto(String username, UUID id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public UUID getId() {
        return id;
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
        return username.equals(that.username) && id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, id);
    }
}
