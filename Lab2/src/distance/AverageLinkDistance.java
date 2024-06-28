package distance;
import data.*;
import clustering.Cluster;
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
        for (int i=0;i< c1.getSize();i++) {
            Example e1=d.getExample(c1.getElement(i));
            for(int j=0; j<c2.getSize();j++) {
                sum+=e1.distance(d.getExample(c2.getElement(j)));
            }
        }
        return sum / (c1.getSize() * c2.getSize());
    }
}
