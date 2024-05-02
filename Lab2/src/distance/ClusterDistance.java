package distance;
import data.Data;
import clustering.Cluster;
/**
 * Interfaccia ClusterDistance
 * contiene metodo per
 * calcolare la distanza tra due cluster
 *
 * @author Team MAP Que Nada
 */
public interface ClusterDistance {
	/**
	 * metodo distance
	 *
	 * @param c1 primo cluster
	 * @param c2 secondo cluster
	 * @param d dataset
	 * @return double
	 */
	double distance(Cluster c1, Cluster c2, Data d);
}
