package database;

/** Eccezione lanciata quando un insieme è vuoto */
public class EmptySetException extends Exception {
    /** Costruttore
     * @param message messaggio da visualizzare
     * */
    public EmptySetException(String message) {
        super(message);
    }
}