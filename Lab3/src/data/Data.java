package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * classe Data
 * avvalora un oggetto data predefinito (fornito dal docente)
 *
 * @author Team MAP Que Nada
 */
public class Data {
    private List<Example> data = new ArrayList<>(); //rappresenta il dataset

    /**
     * Costruttore
     * crea un'istanza di classe Data con un dataset predefinito
     */
    public Data(){
        //data
        Example e=new Example();
        e.add(1.0);
        e.add(2.0);
        e.add(0.0);
        data.add(e);

        e=new Example();
        e.add(0.0);
        e.add(1.0);
        e.add(-1.0);
        data.add(e);

        e=new Example();
        e.add(1.0);
        e.add(3.0);
        e.add(5.0);
        data.add(e);

        e=new Example();
        e.add(1.0);
        e.add(3.0);
        e.add(4.0);
        data.add(e);

        e=new Example();
        e.add(2.0);
        e.add(2.0);
        e.add(0.0);
        data.add(e);
    }

    /**
     * metodo getNumberOfExample
     * restituisce il numero degli esempi memorizzati in data
     *
     * @return numberOfExamples rappresenta il numero di esempi nel dataset
     */
    public int getNumberOfExample () {
        return data.size();
    }

    /**
     * metodo getExample
     * restituisce l'elemento dell'istanza data in posizione exampleIndex
     *
     * @param exampleIndex indice dell'elemento da restituire
     * @return data.get(exampleIndex) elemento in posizione exampleIndex
     */
    public Example getExample (int exampleIndex) {
        return data.get(exampleIndex);
    }

    /**
     * metodo iterator
     * restituisce un iteratore per scorrere gli elementi di data
     *
     * @return data.iterator() iteratore per scorrere gli elementi di data
     */
    public Iterator<Example> iterator(){
        return data.iterator();
    }

    /**
     * metodo distance
     * restituisce la matrice triangolare superiore delle distanze Euclidee
     * calcolate tra gli esempi memorizzati in data.
     * Tale matrice va avvalorata usando il metodo distance di Example
     *
     * @return dist matrice delle distanze tra gli esempi del dataset
     */
    public double[][] distance() throws InvalidSizeException {
        double[][] dist = new double[data.size()][data.size()];
        for (int i = 0; i < data.size(); i++) {
            dist[i][i] = 0;
            for (int j = i + 1; j < data.size(); j++) {
                double d = 0;
                d = data.get(i).distance(data.get(j));
                dist[i][j] = d;
                dist[j][i] = 0; // Riflessione nella metà inferiore
            }
        }

        return dist;
    }

    /**
     * metodo toString
     * crea una stringa in cui memorizza gli esempi memorizzati
     * nell’attributo data, opportunamente enumerati.
     *
     * @return la stringa con gli esempi in data
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<Example> iterator = iterator();
        int count = 0;

        while(iterator.hasNext())
            s.append(count++).append(":[").append(iterator.next().toString()).append("]\n");

        return s.toString();
    }
   }
