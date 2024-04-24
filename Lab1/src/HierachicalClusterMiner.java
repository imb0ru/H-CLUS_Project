// AGGIUNGERE I COMMENTI PER DOCUMENTAZIONE, VISIBILITA CONTROLLATA
class HierachicalClusterMiner {
	private Dendrogram dendrogram;

	public HierachicalClusterMiner(int depth) {
		dendrogram= new Dendrogram(depth);
	}

	void mine(Data data, ClusterDistance distance) {
		ClusterSet level0 = new ClusterSet(data.getNumberOfExample());
		for (int i = 0; i < data.getNumberOfExample(); i++) {
			Cluster c = new Cluster();
			c.addData(i);
			level0.add(c);
		}
		this.dendrogram.setClusterSet(level0, 0);
		for (int i = 1; i < this.dendrogram.getDepth(); i++) {
			ClusterSet nextlevel = this.dendrogram.getClusterSet(i - 1).mergeClosestClusters(distance, data);
			this.dendrogram.setClusterSet(nextlevel, i);
		}
	}

	public String toString() {
		return dendrogram.toString();
	}
	
	public String toString(Data data) {
		return dendrogram.toString(data);
	}

}
