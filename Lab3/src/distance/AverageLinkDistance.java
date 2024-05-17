package distance;
import data.*;
import clustering.Cluster;

import java.util.Iterator;

/**
 * classe AverageLinkDistance
 * Implementa l'interfaccia ClusterDistance per calcolare
 * la media della distanza tra due cluster
 *
 * @author Team MAP Que Nada
 */

public class AverageLinkDistance implements ClusterDistance {
    /**
     * metodo distance
     * restituisce la media delle distanze minime tra i cluster
     * con la distanza AverageLink
     *
     * @param c1 primo cluster
     * @param c2 secondo cluster
     * @param d dataset
     * @return media selle distanze tra i cluster
     */
    public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {
        double sum = 0.0;
        Iterator<Integer> iter = c1.iterator();
        Iterator<Integer> iter2 = c2.iterator();

        while(iter.hasNext()){
            Example e1 = d.getExample(iter.next());
            while(iter2.hasNext()) {
                Example e2 = d.getExample(iter2.next());
                sum += e1.distance(e2);
            }
        }

        return sum / (c1.getSize() * c2.getSize());
    }
}
