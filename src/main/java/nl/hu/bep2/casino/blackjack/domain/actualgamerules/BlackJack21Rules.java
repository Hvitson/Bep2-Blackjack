package nl.hu.bep2.casino.blackjack.domain.actualgamerules;

import nl.hu.bep2.casino.blackjack.domain.*;
import nl.hu.bep2.casino.blackjack.presentation.dto.GameResponse;
import nl.hu.bep2.casino.exceptions.apirequest.Api400Exception;

public class BlackJack21Rules implements Rules {
    private final Game game;

    public BlackJack21Rules(Game game) {
        this.game = game;
    }

    //boven 21 bust
    public GameResponse start() {
        if (game.isGameOver()) {
            throw new Api400Exception("KENNOT START GAME! thats already over");
        }

        this.game.getDeck().shuffle();

        game.getPlayerHand().addCard(game.getDeck().deal());
        game.getDealerHand().addCard(game.getDeck().deal());
        game.getPlayerHand().addCard(game.getDeck().deal());
        game.getDealerHand().addCard(game.getDeck().deal());

        int playerValue = game.getPlayerHand().evaluateHand();
        int dealerValue = game.getDealerHand().evaluateHand();

        if (playerValue == 21 && dealerValue == 21) {
            game.setBalanceChange(game.getBet());
            game.setGameOver(true);
        }
        if (playerValue == 21) {
            game.setBalanceChange(game.getBet() * 5);
            game.setGameOver(true);
        }
        if (dealerValue == 21) {
            game.setBalanceChange(game.getBet() * -1);
            game.setGameOver(true);
        }
        if (game.isGameOver()) {
            return new GameResponse(
                    game.getId(), game.getUsername(), game.getBet(), game.getBalanceChange(), game.getPlayerHand(), game.getDealerHand(), game.getGameMode(), game.isGameOver()
            );
        }
        return new GameResponse(
                game.getId(), game.getUsername(), game.getBet(), game.getBalanceChange(), game.getPlayerHand(), game.getDealerHand().showFirstCard(), game.getGameMode(), game.isGameOver()
        );
    }

    //int var gebruiken? of wordt hij niet aangepast door while loop?
    public GameResponse move(Moves move){
        if (move.equals(Moves.STAND) && game.getPlayerHand().evaluateHand() < 17){
            throw new Api400Exception("You need atleast a value of 17 to stand!");
        }

        if (move.equals(Moves.HIT)) {
            game.getPlayerHand().addCard(game.getDeck().deal());

            if (game.getPlayerHand().evaluateHand() > 21){
                game.setBalanceChange(game.getBet() * -1);
                game.setGameOver(true);
            }
            if (game.getPlayerHand().evaluateHand() == 21){
                game.setBalanceChange(game.getBet() * 2);
                game.setGameOver(true);
            }
            //als playerValue 21 is ook stoppen?

            if (game.isGameOver()) {
                return new GameResponse(
                        game.getId(), game.getUsername(), game.getBet(), game.getBalanceChange(), game.getPlayerHand(), game.getDealerHand(), game.getGameMode(), game.isGameOver()
                );
            }

            return new GameResponse(
                    game.getId(), game.getUsername(), game.getBet(), game.getBalanceChange(), game.getPlayerHand(), game.getDealerHand().showFirstCard(), game.getGameMode(), game.isGameOver()
            );
        }

        if (move.equals(Moves.SURRENDER)) {
            game.setBalanceChange(game.getBet() / 2 *-1);
            game.setGameOver(true);

            return new GameResponse(
                    game.getId(), game.getUsername(), game.getBet(), game.getBalanceChange(), game.getPlayerHand(), game.showDealerHand(), game.getGameMode(), game.isGameOver()
            );
        }

        if (move.equals(Moves.DOUBLE) || move.equals(Moves.STAND)) {
            game.setGameOver(true);
            if (move.equals(Moves.DOUBLE)) {
                game.setBet(game.getBet() * 2);
                game.getPlayerHand().addCard(game.getDeck().deal());
            }
            while (game.getDealerHand().evaluateHand() < 17){
                game.getDealerHand().addCard(game.getDeck().deal());
                System.out.println("Dealer draws a card");
            }
            if (game.getDealerHand().evaluateHand() > 21) {
                game.setBalanceChange(game.getBet() * 2);
            }
            if (game.getPlayerHand().evaluateHand() == game.getDealerHand().evaluateHand()) {
                game.setBalanceChange(game.getBet());
            }
            if (21 >= game.getPlayerHand().evaluateHand() && game.getPlayerHand().evaluateHand() > game.getDealerHand().evaluateHand()) {
                game.setBalanceChange(game.getBet() * 2);
            }
            if (21 >= game.getDealerHand().evaluateHand() && game.getDealerHand().evaluateHand() > game.getDealerHand().evaluateHand()) {
                game.setBalanceChange(game.getBet() * -1);
            }

            return new GameResponse(
                    game.getId(), game.getUsername(), game.getBet(), game.getBalanceChange(), game.getPlayerHand(), game.showDealerHand(), game.getGameMode(), game.isGameOver()
            );
        }
        throw new InvalidStateException("'"+ move +"' is not a viable move! please try again");
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + game.getId() +
                ", username='" + game.getUsername()+ '\'' +
                ", bet=" + game.getBet()+
                ", gameOver=" + game.isGameOver()+
                ", playerHand=" + game.getPlayerHand()+
                ", dealerHand=" + game.getDealerHand()+
                '}';
    }
}
