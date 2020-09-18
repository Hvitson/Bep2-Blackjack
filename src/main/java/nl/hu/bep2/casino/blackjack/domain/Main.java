package nl.hu.bep2.casino.blackjack.domain;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Willkommen to Hu-land BlackJack!@!");
        Deck playingDeck = new Deck();
        playingDeck.createDeck();
        playingDeck.shuffle();

        //handen van het spel(Dealer en Speler)
        Deck dealerHand = new Deck();
        Deck playerHand = new Deck();

        //geld speler later aanroepen via chips
        double playerChips = 100.00;

        Scanner playerInput = new Scanner(System.in);

        while (playerChips > 0){
            System.out.println("Chips: "+ playerChips);
            System.out.println("How much would you like to bet?: ");
            double playerBet = playerInput.nextDouble();
            if (playerBet > playerChips){
                System.out.println("You don't have enough chips!");
                //optie om chips toe te voegen?
                //Y or N toevoegen?
                break;
            }
            while (true){
                if (playerBet < 0){
                    System.out.println("Bet has to be 0 to play for free or above 0 to bet money");
                    System.out.println("How much would you like to bet?: ");
                    playerBet = playerInput.nextDouble();
                }
                else{
                    break;
                }
            }

            //START SPEL
            boolean GameOver = false;

            //Dealing cards after bet
            playerHand.addCard(playingDeck.deal());
            dealerHand.addCard(playingDeck.deal());
            playerHand.addCard(playingDeck.deal());
            dealerHand.addCard(playingDeck.deal());


            //Playing the game "interaction with dealer"
            while (true) {
                //Showing cards of player + total value of cards in hand
                System.out.println("Players hand: " + playerHand);
                System.out.println("Total value: " + playerHand.evaluateHand());
                System.out.println("");

                //showing first card the dealer has
                System.out.println("Dealers hand: " + dealerHand.getCard(0));
                System.out.println("Dealers card value: " + dealerHand.evaluateCard(dealerHand.getCard(0)));
                System.out.println("");

                //Options after evaluating cards
                System.out.println("What would you like to do?\n1. Hit\n2. Stand\n3. Surrender\n4. Double\n5. Split (later misschien ooit maybe ooit eventueel ooit wellicht ooit mogelijkheid op ooit)");
                int playerMove = playerInput.nextInt();
                //Hit
                if (playerMove == 1) {
                    //player gets extra card
                    playerHand.addCard(playingDeck.deal());
                    //check if hand is bigger then 21 if true BUST
                    if (playerHand.evaluateHand() > 21) {
                        System.out.println("BUST your total card value is too high! total card value: " + playerHand.evaluateHand());
                        playerChips -= playerBet;
                        GameOver = true;
                        break;
                    }
                }
                //Stand
                else if (playerMove == 2) {
                    break;
                }
                //Surrender
                else if (playerMove == 3) {
                    playerChips -= (playerBet/2);
                    GameOver = true;
                    break;
                }
                //Double
                else if (playerMove == 4) {
                    //later
                }
                //Split
                else if (playerMove == 5) {
                    //later misschien?
                }
            }

            //Dealers turn
            System.out.println("");
            System.out.println("Dealers turn!");
            System.out.println("");

            System.out.println("Dealers hand: " + dealerHand);
            System.out.println("Total value: " + dealerHand.evaluateHand());

            //checks if dealer has higher total card value then player
            if (dealerHand.evaluateHand() > playerHand.evaluateHand() && GameOver == false){
                System.out.println("Dealer wins with total card value of " + dealerHand.evaluateHand());
                playerChips -= playerBet;
                GameOver = true;
            }

            //Dealer needs to have total value of atleast 16.
            while (dealerHand.evaluateHand() < 17 && GameOver == false){
                dealerHand.addCard(playingDeck.deal());
                System.out.println("Dealer draws card");
                //System.out.println("Dealers draws: " + dealerHand.getCard(-1));
            }

            //show dealers final hand
            System.out.println("Dealers hand: " + dealerHand);
            System.out.println("Total value: " + dealerHand.evaluateHand());

            //check if dealer is busted
            if (dealerHand.evaluateHand() > 21 && GameOver == false){
                System.out.println("Player has won with hand: ");
                System.out.println("Players hand: " + playerHand);
                System.out.println("Total value: " + playerHand.evaluateHand());
                System.out.println("");
                System.out.println("Player wins: "+ (playerBet*2) +" Chips!");
                playerChips += (playerBet*2);
                GameOver = true;
            }

            //if player and dealer have the same hand: dealer wins
            if (playerHand.evaluateHand() == dealerHand.evaluateHand() && GameOver == false){
                System.out.println("Dealer has won with hand: ");
                System.out.println("Dealer hand: " + dealerHand);
                System.out.println("Total value: " + dealerHand.evaluateHand());
                playerChips -= playerBet;
                GameOver = true;
            }

            //if player wins
            if (playerHand.evaluateHand() > dealerHand.evaluateHand() && GameOver == false){
                System.out.println("Player has won with hand: ");
                System.out.println("Players hand: " + playerHand);
                System.out.println("Total value: " + playerHand.evaluateHand());
                playerChips += (playerBet*2);
                GameOver = true;
            }

            //Game is over return cards to deck and shuffle
            playerHand.moveBackToDeck(playingDeck);
            dealerHand.moveBackToDeck(playingDeck);
            playingDeck.shuffle();



        }
        System.out.println("You're out of chips!");
        //optie om chips toe te voegen?
        //Y or N toevoegen?
        System.out.println("How many would you like to add?: ");
        //werkt niet
        double playerAddChips = playerInput.nextDouble();
        if (playerAddChips > 0){
            playerChips +=playerAddChips;
        }
    }
}
