package nl.hu.bep2.casino.chips.presentation;

import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.chips.data.Chips;
import nl.hu.bep2.casino.chips.presentation.dto.Balance;
import nl.hu.bep2.casino.chips.presentation.dto.Deposit;
import nl.hu.bep2.casino.chips.presentation.dto.LeaderBoardChipsDto;
import nl.hu.bep2.casino.security.data.User;
import nl.hu.bep2.casino.security.data.UserProfile;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chips")
public class ChipsController {
    private final ChipsService service;

    public ChipsController(ChipsService service) {
        this.service = service;
    }

    @GetMapping("/balance")
    public Balance showBalance(Authentication authentication) {
        // The Authentication is automatically injected by Spring
        // It is based on the Bearer token in the Authorisation header
        // of the incoming request
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
    public List<LeaderBoardChipsDto> showLeaderBoard() {
        List<LeaderBoardChipsDto> totalChipsUser = new ArrayList<>();
        Long chipsAmount;

        for (Chips chips: this.service.findAllChipsDesc()) {
            chipsAmount = chips.getAmount();
            User user = chips.getUser();
            LeaderBoardChipsDto leaderBoardGamesDto = new LeaderBoardChipsDto(chipsAmount, user.getUsername());
            totalChipsUser.add(leaderBoardGamesDto);
        }
        return totalChipsUser;
    }
}
