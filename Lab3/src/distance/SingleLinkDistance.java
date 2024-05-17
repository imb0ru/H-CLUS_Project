package distance;
import data.*;
import clustering.Cluster;

import java.util.Iterator;

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
		Iterator<Integer> iter = c1.iterator();
		Iterator<Integer> iter2 = c2.iterator();


		while(iter.hasNext()){
			Example e1 = d.getExample(iter.next());
			while(iter2.hasNext()) {
				Example e2 = d.getExample(iter2.next());
				double distance = e1.distance(e2);
				if (distance < min)
					min = distance;
			}
		}
		return min;
	}
}

