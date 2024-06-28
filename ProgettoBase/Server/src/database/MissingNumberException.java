package database;

/** Eccezione lanciata quando manca un numero */
public class MissingNumberException extends Exception {
    /** Costruttore */
    public MissingNumberException(String message) {
        super(message);
    }
}
