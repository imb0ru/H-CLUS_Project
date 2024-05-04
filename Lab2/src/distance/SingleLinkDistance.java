package distance;
import data.*;
import clustering.Cluster;
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
		
		for (int i=0;i< c1.getSize();i++)
		{
			Example e1=d.getExample(c1.getElement(i));
			for(int j=0; j<c2.getSize();j++) {
                double distance= 0;
                try {
                    distance = e1.distance(d.getExample(c2.getElement(j)));
                } catch (InvalidSizeException e) {
					j = c2.getSize();
					i = c1.getSize();
					throw e;
				}
                if (distance<min)
					min=distance;
			}
		}
		return min;
	}
}

