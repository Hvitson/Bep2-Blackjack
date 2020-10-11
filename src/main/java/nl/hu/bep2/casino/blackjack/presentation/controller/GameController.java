package nl.hu.bep2.casino.blackjack.presentation.controller;

import nl.hu.bep2.casino.blackjack.application.GameService;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.GameModes;
import nl.hu.bep2.casino.blackjack.domain.Moves;
import nl.hu.bep2.casino.blackjack.presentation.dto.*;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.chips.data.Chips;
import nl.hu.bep2.casino.exceptions.apirequest.Api400Exception;
import nl.hu.bep2.casino.exceptions.apirequest.Api404Exception;
import nl.hu.bep2.casino.security.application.UserService;
import nl.hu.bep2.casino.security.data.User;
import nl.hu.bep2.casino.security.data.UserProfile;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;
    private final ChipsService chipsService;
    private final UserService userService;

    public GameController(GameService gameService, ChipsService chipsService, UserService userService) {
        this.gameService = gameService;
        this.chipsService = chipsService;
        this.userService = userService;
    }


    @PostMapping("/start")
    public GameDto startGame(Authentication authentication, @Validated @RequestBody  StartGameInfo gameInfo) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        Chips chips = this.chipsService
                .findBalance(profile.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        GameModes gameModes;
        String gameMode = gameInfo.getGameMode().toUpperCase();
        System.out.println(gameMode);

        try {
            gameModes = GameModes.valueOf(gameMode);
        } catch (Exception e) {
            throw new Api400Exception(gameInfo.gameMode + " isnt a gamemode!");
        }

        Long playerBet = gameInfo.betAmount;
        Long playerChips = chips.getAmount();

        if (playerBet > playerChips){
            throw new Api400Exception("Bet must be lower than: " + playerChips);
        }
        if (playerBet < 0) {
            throw new Api400Exception("Bet must higher than 0");
        }

        GameResponse gameResponse = this.gameService.start(profile.getUsername(),
                gameInfo.betAmount, gameInfo.decks, gameModes);

        Game game = this.gameService.findGame(gameResponse.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        GameDto gameDto = new GameDto(game);
        return applyHateoasLinkToUser(applyHateoasLinkToGame(gameDto));
    }

    @GetMapping("/{username}/{id}")
    public GameDto showGame(@PathVariable("username") @NotNull String username, @PathVariable("id") @NotNull UUID id) {
        Game game = this.gameService.findGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            if (username.equals(game.getUsername())){
                GameDto gameDto = new GameDto(game);
                return applyHateoasLinkToUser(gameDto);
            }else {
                throw new Api404Exception("no Game belonging to " + username + " has been found!");
            }
        }

    @GetMapping("/{username}")
    public List<AllGamesDto> get(Authentication authentication, @PathVariable("username") final String username) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        if (!profile.getUsername().equals(username)) {
            throw new Api400Exception("You dont belong HIERRRRR");
        }

        List<AllGamesDto> allGames = new ArrayList<>();
        for (Game game : this.gameService.findAllGames(username)) {
            AllGamesDto allGamesDto = new AllGamesDto(game);
            applyHateoasLinkToAllGames(authentication, allGamesDto);
            allGames.add(allGamesDto);
        }
        return allGames;
    }

    @GetMapping("/leaderboard")
    public List<LeaderBoardGamesDto> showLeaderBoard() {
        List<LeaderBoardGamesDto> totalGamesUser = new ArrayList<>();
        for (User user : this.userService.findAllUsers()) {
            String username = user.getUsername();
            int gamesPlayed = this.gameService.findAllGames(username).size();
            LeaderBoardGamesDto leaderBoardGamesDto = new LeaderBoardGamesDto(gamesPlayed, username);
            totalGamesUser.add(leaderBoardGamesDto);
        }
//        List.sort(totalGamesUser, Collections.reverseOrder());
        return totalGamesUser;
    }

    @PostMapping("/{username}/{id}/{move}")
    public GameDto move(@PathVariable("username") final String username,
                        @PathVariable("id") final UUID id,
                        @Validated @PathVariable("move") final String gameMoves) {
        Game game = this.gameService.findGame(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (game.isGameOver()) {
            throw new Api400Exception("Game with id: " + id + " is already over!");
        }
        if (!username.equals(game.getUsername())) {
            throw new Api400Exception("User " + username + " does not belong to game with id: " + id);
        }

        Moves move;
        String gameMove = gameMoves.toUpperCase();

        try {
            move = Moves.valueOf(gameMove);
        } catch (Exception e) {
            throw new Api400Exception(gameMoves + " isnt a viable move!");
        }
        GameResponse gameResponse = this.gameService.gameMove(id, move);
        Game updatedGame = this.gameService.findGame(gameResponse.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        GameDto gameDto = new GameDto(updatedGame);
        return gameDto;
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

    private AllGamesDto applyHateoasLinkToAllGames(Authentication authentication, AllGamesDto allGamesDto) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();
        final Link selfLink = linkTo(methodOn(GameController.class).showGame(profile.getUsername(), allGamesDto.getId())).withSelfRel();
        allGamesDto.add(selfLink);
        return allGamesDto;
    }
}
