package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.data.SpringGameRepository;
import nl.hu.bep2.casino.blackjack.domain.*;
import nl.hu.bep2.casino.blackjack.domain.GameModes;
import nl.hu.bep2.casino.blackjack.domain.actualgamerules.BlackJack21Rules;
import nl.hu.bep2.casino.blackjack.domain.actualgamerules.BlackJack31Rules;
import nl.hu.bep2.casino.blackjack.domain.deckfillfactory.AmountOfDecksFactory;
import nl.hu.bep2.casino.blackjack.presentation.dto.GameResponse;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.chips.data.SpringChipsRepository;
import nl.hu.bep2.casino.security.data.SpringUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class GameService {

    private final SpringGameRepository gameRepository;
    private final ChipsService chipsService;

    public GameService(SpringGameRepository gameRepository, ChipsService chipsService) {
        this.gameRepository = gameRepository;
        this.chipsService = chipsService;
    }

    public Optional<Game> findGame(UUID id) {
        return this.gameRepository.findById(id);
    }

    public List<Game> findAllGames(String username) {
        return this.gameRepository.findAllByUsername(username);
    }

    public GameResponse start(String username, Long bet, int amountOfDecks, GameModes gameMode) {
        this.chipsService.withdrawChips(username, bet);

        AmountOfDecksFactory factory = new AmountOfDecksFactory(amountOfDecks);
        Deck playingDeck = factory.createDeck();

        UUID id = UUID.randomUUID();
        Long balanceChange = 0L;
        Game game = new Game(id, username, bet, balanceChange, new Hand(), new Hand(), playingDeck, gameMode, false);

        Rules rules;
        if (gameMode.equals(GameModes.BLACKJACK21)) {
            rules = new BlackJack21Rules(game);
        }else {
            rules = new BlackJack31Rules(game);
        }

        var response = rules.start();

        if (game.isGameOver() && game.getBet() > 0) {
                this.chipsService.depositChips(username, game.getBalanceChange());
            }
            this.gameRepository.save(game);
            return response;
    }

    public GameResponse gameMove(UUID id, Moves move)  {
        Game game = this.gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        java.lang.String username = game.getUsername();

        if (move.equals("double")) {
            this.chipsService.withdrawChips(username, game.getBet());
        }

        Rules rules;
        if (game.getGameMode().equals(GameModes.BLACKJACK21)) {
            rules = new BlackJack21Rules(game);
        }else {
            rules = new BlackJack31Rules(game);
        }

        var response = rules.move(move);

        if (game.isGameOver() && game.getBet() > 0) {
            this.chipsService.depositChips(username, game.getBalanceChange());
        }
        this.gameRepository.save(game);
        return response;
    }
}
