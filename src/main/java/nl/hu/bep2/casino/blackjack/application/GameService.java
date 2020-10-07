package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.data.SpringGameRepository;
import nl.hu.bep2.casino.blackjack.domain.*;
import nl.hu.bep2.casino.blackjack.domain.deckfillfactory.AmountOfDecksFactory;
import nl.hu.bep2.casino.blackjack.domain.deckfillfactory.DeckFactory;
import nl.hu.bep2.casino.blackjack.presentation.dto.MoveDto;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.chips.data.Chips;
import nl.hu.bep2.casino.chips.data.SpringChipsRepository;
import nl.hu.bep2.casino.exceptions.apirequest.Api400Exception;
import nl.hu.bep2.casino.security.data.SpringUserRepository;
import nl.hu.bep2.casino.security.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class GameService {

    private final SpringGameRepository gameRepository;
    private final SpringUserRepository userRepository;
    private final SpringChipsRepository chipsRepository;
    private final ChipsService chipsService;

    public GameService(SpringGameRepository gameRepository, SpringUserRepository userRepository, SpringChipsRepository chipsRepository, ChipsService chipsService) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.chipsRepository = chipsRepository;
        this.chipsService = chipsService;
    }

    public Optional<Game> findGame(UUID id) {
        return this.gameRepository.findById(id);
    }

//    public UUID findAllGamesFromUser(String username){
//        return this.gameRepository.selectAllGamesFromUser(username);
//    }

    public GameResponse start(String username, Long bet, int amountOfDecks) {

        AmountOfDecksFactory factory = new AmountOfDecksFactory(amountOfDecks);
        Deck playingDeck = factory.createDeck();

        UUID id = UUID.randomUUID();
        Game game = new Game(id, username, bet, new Hand(), new Hand(), playingDeck, false);
        var response = game.start();

        if (game.isGameOver() && game.getBet() > 0) {
            this.chipsService.depositChips(username, game.getBet());
        }

        this.gameRepository.save(game);

        return response;
    }

    public GameResponse gameMove(UUID id, String move) {
        Game game = this.gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String username = game.getUsername();

        var response = game.move(move);

        if (game.isGameOver() && game.getBet() > 0) {
            this.chipsService.depositChips(username, game.getBet());
        }

        this.gameRepository.save(game);

        return response;
    }

}
