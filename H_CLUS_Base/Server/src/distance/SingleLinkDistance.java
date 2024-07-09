package distance;

import clustering.Cluster;
import data.Data;
import data.Example;
import data.InvalidSizeException;

/**
 * classe SingleLinkDistance
 * Implementa il metodo distance dell'interfaccia
 * ClusterDistance per calcolare la distanza tra due cluster
 * @author Team MAP Que Nada
 */
public class SingleLinkDistance implements ClusterDistance {
	/**
	 * metodo distance
	 * restituisce la minima distanza tra due cluster
	 * con la distanza singlelink
	 *
	 * @param c1 primo cluster
	 * @param c2 secondo cluster
	 * @param d dataset
	 * @return min (un double)
	 */
	public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {
		double min=Double.MAX_VALUE;

        for (Integer integer : c1) {
            Example e1 = d.getExample(integer);
            for (Integer value : c2) {
                double distance = e1.distance(d.getExample(value));
                if (distance < min)
                    min = distance;
            }
        }
		return min;
	}
}

