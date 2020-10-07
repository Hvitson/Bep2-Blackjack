package nl.hu.bep2.casino.blackjack.presentation.controller;

import nl.hu.bep2.casino.blackjack.application.GameService;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.GameResponse;
import nl.hu.bep2.casino.blackjack.presentation.dto.GameDto;
import nl.hu.bep2.casino.blackjack.presentation.dto.StartGameInfo;
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
    public GameDto startGame(Authentication authentication, @RequestBody StartGameInfo gameInfo) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        Chips chips = this.chipsService
                .findBalance(profile.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Long playerBet = gameInfo.betAmount;
        Long playerChips = chips.getAmount();

        if (gameInfo.fakeReal != 1 && gameInfo.fakeReal != 2) {
            throw new Api400Exception("GameMode must be '1' for normal blackjack or '2' for fake blackjack");
        }

        if (playerBet > playerChips){
            throw new Api400Exception("Bet must be lower than: " + playerChips);
        }
        if (playerBet < 0) {
            throw new Api400Exception("Bet must higher than 0");
        }


        GameResponse gameResponse = this.gameService.start(profile.getUsername(),
                gameInfo.betAmount, gameInfo.amountOfDecks, gameInfo.fakeReal);

        GameDto gameDto = new GameDto(gameResponse.getUsername(), gameResponse.getId(),
                gameInfo.betAmount, gameResponse.getBalanceChange(), gameResponse.getPlayerHand(),
                gameResponse.getDealerHand(), gameResponse.isGameOver());
        return applyHateoasLinkToUser(applyHateoasLinkToGame(gameDto));
    }


    @GetMapping("/{username}/{id}")
    public GameDto showGame(@PathVariable("username") @NotNull String username, @PathVariable("id") @NotNull UUID id) {
        Game game = this.gameService.findGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if (username.equals(game.getUsername())){
                GameDto gameDto = new GameDto(game.getUsername(), game.getId(), game.getBet(), game.getBalanceChange(), game.getPlayerHand(), game.getDealerHand().showFirstCard(), game.isGameOver());
                return applyHateoasLinkToUser(gameDto);
            }else {
                throw new Api404Exception("no Game belonging to " + username + " has been found!");
            }
        }


    //todo: lijst alle games met eventueel of hij afgesloten is of lijst met alle openstaande spellen?
    // + informatie over user? zoals gespeelde spellen? aantal gewoonn
    @GetMapping("/{username}")
    public UserDto get(Authentication authentication, @PathVariable("username") final String username) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        if (profile.getUsername() != username) {
            throw new Api400Exception("You dont belong HIERRRRR");
        }
        UserDto userDto = new UserDto(username, null);
           return applyHateoasLinkToUsersGames(userDto);

    }

    @PostMapping("/{username}/{id}/{move}")
    public GameDto move(@PathVariable("username") final String username,
                        @PathVariable("id") final UUID id,
                        @PathVariable("move") final String move) {
        Game game = this.gameService.findGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (game.isGameOver()) {
            throw new Api400Exception("Game with id: " + id + " is already over!");
        }

        if (!username.equals(game.getUsername())) {
            throw new Api400Exception("User " + username + " does not belong to game with id: " + id);
        }
        GameResponse gameResponse = this.gameService.gameMove(id, move);
        GameDto gameDto = new GameDto(gameResponse.getUsername(), gameResponse.getId(),
                gameResponse.getBet(), gameResponse.getBalanceChange(), gameResponse.getPlayerHand(),
                gameResponse.getDealerHand(), gameResponse.isGameOver());
        return gameDto;
    }

//    public UserDto get(@PathVariable("username") final String username) {
//        List<UUID> gamesList = new ArrayList();
//        gamesList.add(gameService.findAllGamesFromUser(username));
//        System.out.println(gamesList);
//        UserDto useruser = new UserDto(username, null);
//        for (int i = 0; i <  gamesList.size(); i++) {
//
//            UserDto userDto = new UserDto(username, gamesList.get(i));
//            System.out.println(userDto);
//            return applyHateoasLinkToUsersGames(userDto);
//        }
//        return useruser;
//    }

    private UserDto applyHateoasLinkToUsersGames(UserDto userDto) {
        final Link GameLink = linkTo(methodOn(GameController.class).showGame(userDto.getUsername(), userDto.getId())).withSelfRel();
        userDto.add(GameLink);
        return userDto;
    }

    private GameDto applyHateoasLinkToUser(GameDto gameDto) {
        final Link UserLink = linkTo(methodOn(GameController.class).get(null, gameDto.getUsername())).withSelfRel();
        gameDto.add(UserLink);
        return gameDto;
    }

    private GameDto applyHateoasLinkToGame(GameDto gameDto) {
        final Link selfLink = linkTo(methodOn(GameController.class).showGame(gameDto.getUsername(), gameDto.getId())).withSelfRel();
        gameDto.add(selfLink);
        return gameDto;
    }

}
