package data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe Example
 * modella le entità esempio inteso come vettore di valori reali
 *
 * @author Team MAP Que Nada
 */
public class Example implements Iterable<Double>{
    private List<Double> example; //vettore di valori reali

    /**
     * Costruttore, crea un'istanza di classe Example
     *
     */
    Example(){
        example = new LinkedList<>();
    }

    /**
     * metodo set
     * modifica example inserendo value
     *
     * @param value valore da inserire
     */
    void add(Double value){
        example.add(value);
    }

    /**
     * metodo get
     * restituisce il valore di example in posizione index
     *
     * @param index indice del valore da prendere
     * @return value valore memorizzato in example in posizione index
     */
    Double get(int index){
        return example.get(index);
    }

    /**
     * metodo iterator
     * Restituisce un iteratore per gli elementi di example
     *
     * @return un iteratore per la lista di valori reali
     */
    @Override
    public Iterator<Double> iterator() {
        return example.iterator();
    }

    /**
     * metodo distance
     * calcola la distanza euclidea tra l'istanza this.Example e l'istanza newE.Example
     *
     * @param newE istanza di classe Example con cui calcolare la distanza
     * @return sum somma delle distanze tra i valori delle due istanze Example
     */
    public double distance(Example newE) throws InvalidSizeException {
        if (this.example.size() != newE.example.size()) {
            throw new InvalidSizeException("Gli esempi hanno dimensioni diverse!");
        }

        double sum = 0.0;
        Iterator<Double> iter1 = this.iterator();
        Iterator<Double> iter2 = newE.iterator();

        while (iter1.hasNext() && iter2.hasNext()) {
            double val1 = iter1.next();
            double val2 = iter2.next();
            sum += Math.pow(val1 - val2, 2);
        }

        return Math.sqrt(sum);
    }

/**
     * metodo toString
     * restituisce una stringa contenente i valori di example
     *
     * @return s stringa contenente i valori di example
     */
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Double aDouble : this)
            s.append(aDouble).append(",");

        return s.toString();
    }

}

