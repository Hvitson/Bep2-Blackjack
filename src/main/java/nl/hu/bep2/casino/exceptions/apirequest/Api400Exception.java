package nl.hu.bep2.casino.exceptions.apirequest;

public class Api400Exception extends RuntimeException {

        public Api400Exception(String message) {
            super(message);
        }
}
