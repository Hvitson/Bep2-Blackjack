package nl.hu.bep2.casino.chips.presentation;

import nl.hu.bep2.casino.blackjack.presentation.controller.GameController;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.chips.data.Chips;
import nl.hu.bep2.casino.chips.presentation.dto.Balance;
import nl.hu.bep2.casino.chips.presentation.dto.Deposit;
import nl.hu.bep2.casino.chips.presentation.dto.LeaderBoardChipsDto;
import nl.hu.bep2.casino.security.data.User;
import nl.hu.bep2.casino.security.data.UserProfile;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/chips")
public class ChipsController {
    private final ChipsService service;

    public ChipsController(ChipsService service) {
        this.service = service;
    }

    @GetMapping("/balance")
    public Balance showBalance(Authentication authentication) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        Chips chips = this.service
                .findBalance(profile.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new Balance(
                chips.getUser().getUsername(),
                chips.getLastUpdate(),
                chips.getAmount()
        );
    }

    @PostMapping("/deposit")
    public void deposit(Authentication authentication, @RequestBody Deposit deposit) {
        UserProfile profile = (UserProfile) authentication.getPrincipal();

        this.service.depositChips(profile.getUsername(), deposit.amount);
    }

    @GetMapping("/leaderboard")
    public List<LeaderBoardChipsDto> showLeaderBoard(String username) {
        List<LeaderBoardChipsDto> totalChipsUser = new ArrayList<>();
        Long chipsAmount;

        for (Chips chips: this.service.findAllChipsDesc()) {
            chipsAmount = chips.getAmount();
            User user = chips.getUser();
            LeaderBoardChipsDto leaderBoardGamesDto = new LeaderBoardChipsDto(chipsAmount, user.getUsername());
            applyHateoasLinkToUser(leaderBoardGamesDto);
            totalChipsUser.add(leaderBoardGamesDto);
        }
        return totalChipsUser;
    }

    private LeaderBoardChipsDto applyHateoasLinkToUser(LeaderBoardChipsDto leaderBoardChipsDto) {
        final Link UserLink = linkTo(methodOn(GameController.class).get(null, leaderBoardChipsDto.getUsername())).withSelfRel();
        leaderBoardChipsDto.add(UserLink);
        return leaderBoardChipsDto;
    }
}
