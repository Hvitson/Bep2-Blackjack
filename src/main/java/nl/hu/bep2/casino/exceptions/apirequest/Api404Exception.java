package nl.hu.bep2.casino.exceptions.apirequest;

public class Api404Exception extends RuntimeException {

    public Api404Exception(String message) {
        super(message);
    }
}
