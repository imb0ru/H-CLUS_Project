package clustering;
import data.Data;
import data.InvalidSizeException;
import distance.ClusterDistance;
/**
 * Classe HierachicalClusterMiner
 * modella il processo di clustering
 *
 * @author Team MAP Que Nada
 */
public class HierachicalClusterMiner {
	private Dendrogram dendrogram;

	/**
	 * Costruttore
	 * crea un'istanza di classe HierachicalClusterMiner con profondità depth
	 * @param depth profondità del dendrogramma
	 */
	public HierachicalClusterMiner(int depth) throws InvalidDepthException {
		dendrogram= new Dendrogram(depth);
	}

	/**
	 * metodo mine
	 * calcola il clustering del dataset data
	 * @param data dataset su cui calcolare il clustering
	 * @param distance interfaccia di calcolo distanza tra cluster
	 */
	public void mine(Data data, ClusterDistance distance) throws InvalidDepthException, InvalidClustersNumberException, InvalidSizeException {
		if (dendrogram.getDepth() > data.getNumberOfExample()) {
			throw new InvalidDepthException("Numero di Esempi maggiore della profondità del dendrogramma!\n");
		}

		ClusterSet level0 = new ClusterSet(data.getNumberOfExample());
		for (int i = 0; i < data.getNumberOfExample(); i++) {
			Cluster c = new Cluster();
			c.addData(i);
			level0.add(c);
		}
		dendrogram.setClusterSet(level0, 0);
		for (int i = 1; i < dendrogram.getDepth(); i++) {
            ClusterSet nextlevel = null;
			nextlevel = dendrogram.getClusterSet(i - 1).mergeClosestClusters(distance, data);
			dendrogram.setClusterSet(nextlevel, i);
        }

	}

	/**
	 * metodo toString
	 * Restituisce una rappresentazione testuale del dendrogramma
	 * @return una rappresentazione testuale del dendrogramma
	 */
	public String toString() {
		return dendrogram.toString();
	}

	/**
	 * metodo toString
	 * Restituisce una rappresentazione testuale del dendrogramma
	 * @param data dataset di esempi
	 * @return una rappresentazione testuale del dendrogramma
	 */
	public String toString(Data data) {
		return dendrogram.toString(data);
	}

}
