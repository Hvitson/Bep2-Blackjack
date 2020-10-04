package nl.hu.bep2.casino.blackjack.domain;

public class InvalidStateException extends RuntimeException{

    public InvalidStateException(String s) {
        super(s);
    }
}
