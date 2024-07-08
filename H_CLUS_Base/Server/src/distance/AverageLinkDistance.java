package distance;

import clustering.Cluster;
import data.Data;
import data.Example;
import data.InvalidSizeException;

/**
 * Classe AverageLinkDistance
 * Implementa l'interfaccia ClusterDistance per calcolare
 * la media della distanza tra due cluster
 *
 * @author Team MAP Que Nada
 */

public class AverageLinkDistance implements ClusterDistance {
    /**
     * Metodo distance
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

        for (Integer integer : c1) {
            Example e1 = d.getExample(integer);
            for (Integer value : c2) sum += e1.distance(d.getExample(value));
        }

        return sum / (c1.getSize() * c2.getSize());
    }
}
