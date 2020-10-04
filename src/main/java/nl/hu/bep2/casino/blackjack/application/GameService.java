package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.data.SpringGameRepository;
import nl.hu.bep2.casino.blackjack.domain.Game;
import nl.hu.bep2.casino.blackjack.domain.Deck;
import nl.hu.bep2.casino.blackjack.domain.GameResponse;
import nl.hu.bep2.casino.blackjack.domain.Hand;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.exceptions.apirequest.Api404Exception;
import nl.hu.bep2.casino.security.data.SpringUserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class GameService {

    private final SpringGameRepository gameRepository;
    private final ChipsService chipsService;
    private final SpringUserRepository userRepository;

    public GameService(SpringGameRepository gameRepository, ChipsService chipsService, SpringUserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.chipsService = chipsService;
    }

    public Optional<Game> findGame(UUID id) {
        return this.gameRepository.findById(id);
    }

    public GameResponse start(UUID id, String username, Long bet) {

        //FACTORY voor deck? of game algemeen+

        Game game = new Game(id, username, bet, new Hand(), new Hand(), new Deck(), false);
        var response = game.start();

        if (game.isGameOver() && game.getBet() > 0) {
            this.chipsService.depositChips(username, game.getBet());
        }

        this.gameRepository.save(game);

        return response;
    }

//    public GameResponse hit(UUID id, String username) {
//
//    }

}
