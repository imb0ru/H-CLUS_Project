package data;

/** Eccezione lanciata quando non ci sono dati */
public class NoDataException extends Exception {
    /** Costruttore */
    public NoDataException(String message) {
        super(message);
    }
}
