package data;

import database.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe Data
 * Avvalora un oggetto data predefinito (fornito dal docente)
 * oppure leggendo i suoi esempi dalla tabella con nome tableName nel database.
 *
 */
public class Data {
    /** Lista di esempi */
    private final List<Example> data = new ArrayList<>(); // rappresenta il dataset

    /**
     * Costruttore
     * Crea un'istanza di classe Data leggendo i suoi esempi dalla tabella con nome tableName nel database.
     *
     * @param tableName Nome della tabella nel database
     * @throws NoDataException se la tabella è vuota.
     */
    public Data(String tableName) throws NoDataException {
        DbAccess dbAccess = new DbAccess();
        try {
            TableData tableData = new TableData(dbAccess);
            List<Example> examples = tableData.getDistinctTransazioni(tableName);
            this.data.addAll(examples);
        } catch (DatabaseConnectionException e) {
            throw new NoDataException("Errore di connessione al database\n");
        } catch (EmptySetException e) {
            throw new NoDataException("La tabella " + tableName + " è vuota\n");
        } catch (MissingNumberException e) {
            throw new NoDataException("Eccezione durante l'elaborazione dei dati\n");
        } catch (SQLException e) {
            throw new NoDataException("Errore SQL durante il recupero dei dati dalla tabella, tabella non esistente\n");
        }
    }

    /**
     * Metodo getNumberOfExample
     * Restituisce il numero degli esempi memorizzati in data.
     *
     * @return numero di esempi nel dataset
     */
    public int getNumberOfExample() {
        return data.size();
    }

    /**
     * Metodo getExample
     * Restituisce l'elemento dell'istanza data in posizione exampleIndex.
     *
     * @param exampleIndex indice dell'elemento da restituire
     * @return elemento in posizione exampleIndex
     */
    public Example getExample(int exampleIndex) {
        return data.get(exampleIndex);
    }

    /**
     * Metodo iterator
     * Restituisce un iterator per scorrere gli elementi di data.
     *
     * @return iterator per scorrere gli elementi di data
     */
    public Iterator<Example> iterator() {
        return data.iterator();
    }

    /**
     * Metodo toString
     * Crea una stringa in cui memorizza gli esempi memorizzati nell’attributo data, opportunamente enumerati.
     *
     * @return stringa con gli esempi in data
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<Example> iterator = iterator();
        int count = 0;

        while (iterator.hasNext()) {
            s.append(count++).append(":[").append(iterator.next().toString()).append("]\n");
        }

        return s.toString();
    }
}

