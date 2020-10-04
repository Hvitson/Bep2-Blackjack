package nl.hu.bep2.casino.blackjack.presentation.controller;

import nl.hu.bep2.casino.blackjack.application.GameService;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.presentation.dto.Bet;
import nl.hu.bep2.casino.blackjack.presentation.dto.GameDto;
import nl.hu.bep2.casino.blackjack.presentation.dto.UserDto;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.chips.data.Chips;
import nl.hu.bep2.casino.exceptions.apirequest.Api400Exception;
import nl.hu.bep2.casino.exceptions.apirequest.Api404Exception;
import nl.hu.bep2.casino.security.data.UserProfile;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;
    private final ChipsService chipsService;

    public GameController(GameService gameService, ChipsService chipsService) {
        this.gameService = gameService;
        this.chipsService = chipsService;
    }

    @PostMapping("/start")
    public GameDto startGame(Authentication authentication, @RequestBody Bet bet) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        Chips chips = this.chipsService
                .findBalance(profile.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UUID id = UUID.randomUUID();
        Long playerBet = bet.betAmount;
        Long playerChips = chips.getAmount();

        if (playerBet <= playerChips) {
            this.gameService.start(id, profile.getUsername(), bet.betAmount);

            Game game = this.gameService.findGame(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            if (profile.getUsername().equals(game.getUsername())){
                GameDto gameDto = new GameDto(game.getUsername(), id, bet.betAmount, game.isGameOver());
                return applyHateoasLinkToUser(applyHateoasLinkToGame(gameDto));
            }else{
                throw new Api400Exception("Could not create game");
            }
        }else {
            throw new Api400Exception("Bet must be lower than: " + playerChips);
        }
    }

    @GetMapping("/{username}/{id}")
    public GameDto showGame(@PathVariable("username") @NotNull String username, @PathVariable("id") @NotNull UUID id) {
        Game game = this.gameService.findGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if (username.equals(game.getUsername())){
                GameDto gameDto = new GameDto(game.getUsername(), game.getId(), game.getBet(), game.isGameOver());
                return applyHateoasLinkToUser(gameDto);
            }else {
                throw new Api404Exception("no Game belonging to " + username + " has been found!");
            }
        }


    //todo: lijst alle games met eventueel of hij afgesloten is of lijst met alle openstaande spellen?
    // + informatie over user? zoals gespeelde spellen? aantal gewoonn
    @GetMapping("/{username}")
    public UserDto get(@PathVariable("username") @NotNull final String username) {
       UserDto userDto = new UserDto(username);
        return userDto;
    }



    private GameDto applyHateoasLinkToUser(GameDto gameDto) {
        final Link UserLink = linkTo(methodOn(GameController.class).get(gameDto.getUsername())).withSelfRel();
        gameDto.add(UserLink);
        return gameDto;
    }

    private GameDto applyHateoasLinkToGame(GameDto gameDto) {
        final Link selfLink = linkTo(methodOn(GameController.class).showGame(gameDto.getUsername(), gameDto.getId())).withSelfRel();
        gameDto.add(selfLink);
        return gameDto;
    }

}
