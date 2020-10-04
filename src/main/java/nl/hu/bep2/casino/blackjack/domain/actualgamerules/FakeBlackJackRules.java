//package nl.hu.bep2.casino.blackjack.domain.actualgamerules;
//
//import nl.hu.bep2.casino.blackjack.domain.Card;
//
//import java.util.List;
//
//public class FakeBlackJackRules implements GameRules{
//        private List<Card> cards;
//
//        @Override
//        public int evaluateCard(Card card){
//            int handValue = 0;
//            switch(card.getRank()){
//                case Ace:
//                    handValue = -11; break;
//                case Two:
//                    handValue = 10; break;
//                case Three:
//                    handValue = 10; break;
//                case Four:
//                    handValue = 10; break;
//                case Five:
//                    handValue = 10; break;
//                case Six:
//                    handValue = 9; break;
//                case Seven:
//                    handValue = 8; break;
//                case Eight:
//                    handValue = 7; break;
//                case Nine:
//                    handValue = 6; break;
//                case Ten:
//                    handValue = 5; break;
//                case Jack:
//                    handValue = 4; break;
//                case Queen:
//                    handValue = 3; break;
//                case King:
//                    handValue = 2; break;
//            }
//            return handValue;
//        }
//
//        @Override
//        public int evaluateHand(){
//            int handValue = 0;
//            int acesValue = 0;
//
//            for (Card card : this.cards) {
//                switch(card.getRank()){
//                    case Ace:
//                        acesValue += 1; break;
//                    case Two:
//                        handValue += 10; break;
//                    case Three:
//                        handValue += 10; break;
//                    case Four:
//                        handValue += 10; break;
//                    case Five:
//                        handValue += 10; break;
//                    case Six:
//                        handValue += 9; break;
//                    case Seven:
//                        handValue += 8; break;
//                    case Eight:
//                        handValue += 7; break;
//                    case Nine:
//                        handValue += 6; break;
//                    case Ten:
//                        handValue += 5; break;
//                    case Jack:
//                        handValue += 4; break;
//                    case Queen:
//                        handValue += 3; break;
//                    case King:
//                        handValue += 2; break;
//                }
//            }
//            for (int i=0; i < acesValue; i++){
//                if (handValue > 10){
//                    handValue += 1;
//                }
//                else{
//                    handValue += 11;
//                }
//            }
//
//            return handValue;
//        }
//
//}