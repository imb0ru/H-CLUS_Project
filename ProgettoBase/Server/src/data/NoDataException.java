package data;

/** Eccezione lanciata quando non ci sono dati */
public class NoDataException extends Exception {
    /** Costruttore
     * @param message messaggio da visualizzare
     * */
    public NoDataException(String message) {
        super(message);
    }
}
