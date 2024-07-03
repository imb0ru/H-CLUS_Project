package clustering;

/** Eccezione lanciata quando il numero di cluster è minore di 1 */
public class InvalidClustersNumberException extends Exception{
    /** Costruttore */
    InvalidClustersNumberException(String msg) {
        super(msg);
    }
}
