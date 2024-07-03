package database;

/** Eccezione lanciata quando non è possibile stabilire una connessione al database */
public class DatabaseConnectionException extends Exception {
    /** Costruttore */
    DatabaseConnectionException(String msg) {
        super(msg);
    }
}
