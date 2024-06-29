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
    /** Vettore di valori reali */
    private final List<Double> example; //vettore di valori reali

    /**
     * Costruttore, crea un'istanza di classe Example
     *
     */
    public Example(){
        example = new LinkedList<>();
    }

    /**
     * Metodo iterator
     * restituisce un iterator per scorrere gli elementi di example
     *
     * @return example.iterator() iterator per scorrere gli elementi di example
     */
    public Iterator<Double> iterator(){
        return example.iterator();
    }

    /**
     * metodo add
     * modifica example inserendo v in coda
     *
     * @param v valore da inserire
     */
    public void add(Double v){
        example.add(v);
    }

    /**
     * Metodo distance
     * calcola la distanza euclidea tra l'istanza this.Example e l'istanza newE.Example
     *
     * @param  newE istanza di classe Example con cui calcolare la distanza
     * @throws InvalidSizeException se le due istanze hanno dimensioni diverse
     * @return sum somma delle distanze tra i valori delle due istanze Example
     */
     public double distance(Example newE) throws InvalidSizeException{
         if(example.size() != newE.example.size())
             throw new InvalidSizeException("Gli esempi hanno dimensioni diverse!");

         double sum = 0.0;
         Iterator<Double> iterator1 = example.iterator();
         Iterator<Double> iterator2 = newE.iterator();

         while (iterator1.hasNext() && iterator2.hasNext()) {
             double diff = iterator1.next() - iterator2.next();
             sum += Math.pow(diff, 2);
         }

         return sum;
    }

/**
     * Metodo toString
     * restituisce una stringa contenente i valori di example
     *
     * @return s stringa contenente i valori di example
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        Iterator<Double> iterator = iterator();

        if (iterator.hasNext())
            s.append(iterator.next());

        while (iterator.hasNext())
            s.append(",").append(iterator.next());

        return s.toString();
    }
}
