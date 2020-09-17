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

            //Dealing cards after bet
            playerHand.addCard(playingDeck.deal());
            dealerHand.addCard(playingDeck.deal());
            playerHand.addCard(playingDeck.deal());
            dealerHand.addCard(playingDeck.deal());

            //Showing cards of player + total value of cards in hand
            System.out.println("hand: " + playerHand);
            System.out.println("Total value: " + playerHand.evaluateHand());







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
