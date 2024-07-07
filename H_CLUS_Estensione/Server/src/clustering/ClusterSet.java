package clustering;

import data.Data;
import data.InvalidSizeException;
import distance.ClusterDistance;

import java.io.Serializable;

/**
 * classe ClusterSet
 * Implementa un insieme di cluster
 *
 * @author Team MAP Que Nada
 */
class ClusterSet implements Serializable {
	/** Insieme di cluster */
	private final Cluster[] C;
	/** Indice dell'ultimo cluster */
	private int lastClusterIndex=0;

	/**
	 * Costruttore
	 * crea un'istanza di classe ClusterSet di dimensione k
	 *
	 * @param k dimensione dell'insieme di cluster
	 */
	ClusterSet(int k){
		C=new Cluster[k];
	}

	/**
	 * Metodo add
	 * aggiunge il cluster c all'insieme di cluster
	 * controlla che il cluster non sia già presente nell'insieme
	 *
	 * @param c cluster da aggiungere all'insieme
	 */
	void add(Cluster c){
		for(int j=0;j<lastClusterIndex;j++)
			if(c==C[j]) // to avoid duplicates
				return;
		C[lastClusterIndex]=c;
		lastClusterIndex++;
	}

	/**
	 * Metodo get
	 * restituisce il cluster in posizione i
	 *
	 * @param i indice del cluster da restituire
	 * @return cluster in posizione i
	 */
	Cluster get(int i){
		return C[i];
	}

	/**
	 * Metodo mergeClosestClusters
	 * restituisce un nuovo insieme di cluster che è la fusione dei due cluster più vicini
	 *
	 * @param distance interfaccia di calcolo della distanza tra due cluster
	 * @param data dataset
	 * @return insieme di cluster con i due cluster più vicini fusi
	 */
	ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) throws InvalidSizeException, InvalidClustersNumberException {
		if ( lastClusterIndex <= 1)
			throw new InvalidClustersNumberException("Non ci sono abbastanza cluster da fondere");

		double minD = Double.MAX_VALUE;
		Cluster cluster1 = null;
		Cluster cluster2 = null;

		for (int i = 0; i < this.C.length; i++) {
			Cluster c1 = get(i);
			for(int j = i+1; j<this.C.length; j++){
				Cluster c2 = get(j);
				double d;
                try {
					d = distance.distance(c1, c2, data);
					if (d < minD) {
						minD = d;
						cluster1 = c1;
						cluster2 = c2;
					}
				} catch (InvalidSizeException e) {
					j = this.C.length;
					i = this.C.length;
					throw e;
				}
			}
		}
		if (cluster1 == null || cluster2 == null) {
			throw new InvalidClustersNumberException("Non ci sono abbastanza cluster validi da fondere");
		}
		Cluster mergedCluster = cluster1.mergeCluster(cluster2);
		ClusterSet finalClusterSet = new ClusterSet(this.C.length-1);
		for(int i=0; i<this.C.length; i++){
			Cluster c = get(i);
			if(c!=cluster1) {
				if (c != cluster2)
					finalClusterSet.add(c);
			}
			else
				finalClusterSet.add(mergedCluster);
		}

		return finalClusterSet;
	}

	/**
	 * Metodo toString
	 * restituisce una stringa contenente gli indici degli esempi raggruppati nei cluster
	 *
	 * @return str stringa contenente gli indici degli esempi raggruppati nei cluster
	 */
	public String toString(){
		StringBuilder str= new StringBuilder();
		for(int i=0;i<C.length;i++){
			if (C[i]!=null) str.append("cluster").append(i).append(":").append(C[i]).append("\n");
		}
		return str.toString();

	}

	/**
	 * Metodo toString
	 * restituisce una stringa contenente gli esempi raggruppati nei cluster
	 *
	 * @param data oggetto di classe Data che modella il dataset su cui il clustering è calcolato
	 * @return str stringa contenente gli esempi raggruppati nei cluster
	 */
	public String toString(Data data){
		StringBuilder str= new StringBuilder();
		for(int i=0;i<C.length;i++){
			if (C[i]!=null) str.append("cluster").append(i).append(":").append(C[i].toString(data)).append("\n");
		}
		return str.toString();

	}

}
