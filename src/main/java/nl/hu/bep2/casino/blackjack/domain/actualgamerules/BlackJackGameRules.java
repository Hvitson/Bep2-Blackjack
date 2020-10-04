//package nl.hu.bep2.casino.blackjack.domain.actualgamerules;
//
//import nl.hu.bep2.casino.blackjack.domain.Card;
//import nl.hu.bep2.casino.blackjack.domain.Deck;
//import nl.hu.bep2.casino.blackjack.domain.GameRules;
//import nl.hu.bep2.casino.blackjack.domain.Hand;
//
//import java.util.List;
//
//public class BlackJackGameRules implements GameRules {
//    private Hand hand;
//    private Deck deck;
//    private List<Card> cards;
//
////    public int evaluateCard(Card card){
////        int handValue = 0;
////        switch(card.getRank()){
////            case Ace:
////                handValue = 11; break;
////            case Two:
////                handValue = 2; break;
////            case Three:
////                handValue = 3; break;
////            case Four:
////                handValue = 4; break;
////            case Five:
////                handValue = 5; break;
////            case Six:
////                handValue = 6; break;
////            case Seven:
////                handValue = 7; break;
////            case Eight:
////                handValue = 8; break;
////            case Nine:
////                handValue = 9; break;
////            case Ten:
////                handValue = 10; break;
////            case Jack:
////                handValue = 10; break;
////            case Queen:
////                handValue = 10; break;
////            case King:
////                handValue = 10; break;
////        }
////        return handValue;
////    }
////
////    public int evaluateHand(Hand hand){
////        int handValue = 0;
////        int acesValue = 0;
////
////        for (Card card : this.cards) {
////            switch(card.getRank()){
////                case Ace:
////                    acesValue += 1; break;
////                case Two:
////                    handValue += 2; break;
////                case Three:
////                    handValue += 3; break;
////                case Four:
////                    handValue += 4; break;
////                case Five:
////                    handValue += 5; break;
////                case Six:
////                    handValue += 6; break;
////                case Seven:
////                    handValue += 7; break;
////                case Eight:
////                    handValue += 8; break;
////                case Nine:
////                    handValue += 9; break;
////                case Ten:
////                    handValue += 10; break;
////                case Jack:
////                    handValue += 10; break;
////                case Queen:
////                    handValue += 10; break;
////                case King:
////                    handValue += 10; break;
////            }
////        }
////        for (int i=0; i < acesValue; i++){
////            if (handValue > 10){
////                handValue += 1;
////            }
////            else{
////                handValue += 11;
////            }
////        }
////
////        return handValue;
////    }
//
//}
