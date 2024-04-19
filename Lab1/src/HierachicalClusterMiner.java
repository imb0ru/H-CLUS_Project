

class HierachicalClusterMiner {
	private Dendrogram dendrogram;

	HierachicalClusterMiner(int depth) {
		dendrogram= new Dendrogram(depth);
	}

	void mine(Data data, ClusterDistance distance) {
		ClusterSet[] dendrogram = new ClusterSet[data.getNumberOfExample()];

		// Creazione del livello base (livello 0) del dendrogramma
		for (int i = 0; i < data.getNumberOfExample(); i++) {
			ClusterSet baseLevelClusterSet = new ClusterSet(data.getNumberOfExample());
			Cluster baseCluster = new Cluster();
			baseCluster.addData(i);
			baseLevelClusterSet.add(baseCluster);
			dendrogram[0] = baseLevelClusterSet;
		}

		// Costruzione dei livelli successivi del dendrogramma
		for (int level = 1; level < dendrogram.length; level++) {
			ClusterSet prevLevelClusterSet = dendrogram[level - 1];
			ClusterSet mergedClusterSet = prevLevelClusterSet.mergeClosestClusters(distance, data);
			dendrogram[level] = mergedClusterSet;
		}

		//da realizzare/aggiustare la memorizzazione del dendrogramma

	}


	public String toString() {
		return dendrogram.toString();
	}
	
	String toString(Data data) {
		return dendrogram.toString(data);
	}

}
