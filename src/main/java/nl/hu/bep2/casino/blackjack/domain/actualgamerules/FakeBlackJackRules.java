//package nl.hu.bep2.casino.blackjack.domain.actualgamerules;
//
//import nl.hu.bep2.casino.blackjack.domain.*;
//import nl.hu.bep2.casino.exceptions.apirequest.Api400Exception;
//
//import javax.persistence.Column;
//import javax.persistence.Id;
//import javax.persistence.Lob;
//import java.util.List;
//import java.util.UUID;
//
////boven 31 bust
//public class FakeBlackJackRules implements GameRulesStrategy{
//    private UUID id;
//    private String username;
//    private Long bet;
//    private int gameMode;
//    private boolean gameOver;
//    private Hand playerHand;
//    private Hand dealerHand;
//    private Deck deck;
//
//    public GameResponse start() {
//        if (gameOver) {
//            throw new Api400Exception("KENNOT START GAME! thats already over");
//        }
//
//        this.deck.shuffle();
//
//        playerHand.addCard(deck.deal());
//        dealerHand.addCard(deck.deal());
//        playerHand.addCard(deck.deal());
//        dealerHand.addCard(deck.deal());
//        playerHand.addCard(deck.deal());
//        dealerHand.addCard(deck.deal());
//
//        int playerValue = playerHand.evaluateHand();
//        int dealerValue = dealerHand.evaluateHand();
//
//        if (playerValue == 31 && dealerValue == 31) {
//            gameOver = true;
//        } else if (playerValue == 31) {
//            bet = this.bet * 5;
//            gameOver = true;
//        } else if (dealerValue == 31) {
//            bet = 0L;
//            gameOver = true;
//        }
//
//        return new GameResponse(
//                id, username, bet, playerHand, dealerHand.showFirstCard(), gameMode, gameOver
//        );
//    }
//
//    public GameResponse move(String move){
//
//        if (move.equals("hit")) {
//            playerHand.addCard(deck.deal());
//
//            if (playerHand.evaluateHand() > 31){
//                bet = 0L;
//                gameOver = true;
//            }
//            //als playerValue 21 is ook stoppen?
//
//            return new GameResponse(
//                    id, username, bet, playerHand, dealerHand.showFirstCard(), gameMode, gameOver
//            );
//        }
//
//        if (move.equals("surrender")) {
//            bet = bet / 2;
//            gameOver = true;
//
//            return new GameResponse(
//                    id, username, bet, playerHand, dealerHand, gameMode, gameOver
//            );
//        }
//
//        if (move.equals("double") || move.equals("stand")) {
//            if (move.equals("double")) {
//                bet = bet *2;
//                playerHand.addCard(deck.deal());
//            }
//            while (dealerHand.evaluateHand() < 17){
//                dealerHand.addCard(deck.deal());
//                System.out.println("Dealer draws card");
//            }
//            if (dealerHand.evaluateHand() > 31) {
//                bet = bet * 2;
//                gameOver = true;
//            }
//            //wint dealer als gelijk of geld terug?
//            if (playerHand.evaluateHand() == dealerHand.evaluateHand()) {
//                gameOver = true;
//            }
//            if (31 >= playerHand.evaluateHand() && playerHand.evaluateHand() > dealerHand.evaluateHand()) {
//                bet = bet * 2;
//                gameOver = true;
//            }
//
//            return new GameResponse(
//                    id, username, bet, playerHand, dealerHand, gameMode, gameOver
//            );
//        }
//        throw new Api400Exception("'"+ move +"' is not a viable move! please try again");
//    }
//}
