package nl.hu.bep2.casino.blackjack.application;

import nl.hu.bep2.casino.blackjack.data.SpringGameRepository;
import nl.hu.bep2.casino.blackjack.domain.*;
import nl.hu.bep2.casino.blackjack.domain.deckfillfactory.AmountOfDecksFactory;
import nl.hu.bep2.casino.chips.application.ChipsService;
import nl.hu.bep2.casino.chips.data.SpringChipsRepository;
import nl.hu.bep2.casino.security.data.SpringUserRepository;
import org.springframework.http.HttpStatus;
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

    public GameResponse start(String username, Long bet, int amountOfDecks, int fakeReal) {

        this.chipsService.withdrawChips(username, bet);

        AmountOfDecksFactory factory = new AmountOfDecksFactory(amountOfDecks);
        Deck playingDeck = factory.createDeck();

        UUID id = UUID.randomUUID();
        Long balanceChange = 0L;
        Game game = new Game(id, username, bet, balanceChange, new Hand(), new Hand(), playingDeck, fakeReal, false);

        var response = game.start();

        if (game.isGameOver() && game.getBalanceChange() > 0) {
            this.chipsService.depositChips(username, game.getBalanceChange());
        }

        this.gameRepository.save(game);

        return response;
    }
        //Strategy 1 is normal blackjack 2 is fake blackjack
        //kan dit beterr?
//        if (fakeReal == 1) {
//            var response1 = game.StartGamesUsing(new BlackJackRules());
//            if (game.isGameOver() && game.getBet() > 0) {
//                this.chipsService.depositChips(username, game.getBet());
//            }
//            this.gameRepository.save(game);
//            return response1;
//        }
//        if (fakeReal == 2) {
//            var response2 = game.StartGamesUsing(new FakeBlackJackRules());
//            if (game.isGameOver() && game.getBet() > 0) {
//                this.chipsService.depositChips(username, game.getBet());
//            }
//            this.gameRepository.save(game);
//            return response2;
//        }
//        throw new Api400Exception("Game couldn't start! wrong gameMode int");
//    }

    public GameResponse gameMove(UUID id, String move) {
        Game game = this.gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        String username = game.getUsername();

        if (move.equals("double")) {
            this.chipsService.withdrawChips(username, game.getBet());
        }
        var response = game.move(move);

        if (game.isGameOver() && game.getBet() > 0) {
            this.chipsService.depositChips(username, game.getBet());
        }
        this.gameRepository.save(game);

        return response;
    }

}
