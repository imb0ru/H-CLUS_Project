package database;

/** Eccezione lanciata quando un insieme è vuoto */
public class EmptySetException extends Exception {
    /** Costruttore */
    public EmptySetException(String message) {
        super(message);
    }
}