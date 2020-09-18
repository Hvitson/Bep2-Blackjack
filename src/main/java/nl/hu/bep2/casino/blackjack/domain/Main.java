package nl.hu.bep2.casino.blackjack.domain;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Willkommen to Hu-land BlackJack!@!");
        Deck playingDeck = new Deck();
        playingDeck.createDeck();

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

            //START SPEL
            boolean GameOver = false;

            //Dealing cards after bet
            playerHand.addCard(playingDeck.deal());
            dealerHand.addCard(playingDeck.deal());
            playerHand.addCard(playingDeck.deal());
            dealerHand.addCard(playingDeck.deal());

            while (true) {
                //Showing cards of player + total value of cards in hand
                System.out.println("hand: " + playerHand);
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
                        System.out.println("BUST your card value is: " + playerHand.evaluateHand());
                        playerChips -= playerBet;
                        GameOver = true;
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
                } else if (playerMove == 4) {
                    //later
                } else if (playerMove == 5) {
                    //later misschien?
                } else {
                    System.out.println();
                }
            }
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
