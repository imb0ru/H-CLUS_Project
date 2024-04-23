/**
 * Interfaccia ClusterDistance
 * contiene metodo per
 * calcolare la distanza tra due cluster
 *
 * @author Team MAP Que Nada
 */
interface ClusterDistance {
	/**
	 * metodo distance
	 *
	 * @param c1 primo cluster
	 * @param c2 secondo cluster
	 * @param d dati utilizzati per calcolare la distanza tra i cluster
	 * @return double
	 */
		double distance(Cluster c1, Cluster c2, Data d);
}
