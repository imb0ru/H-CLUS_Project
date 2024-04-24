
//// AGGIUNGERE I COMMENTI PER DOCUMENTAZIONE + CONTROLLA VISIBILITA

class ClusterSet {

	private Cluster C[];
	private int lastClusterIndex=0;
	
	ClusterSet(int k){
		C=new Cluster[k];
	}
	
	void add(Cluster c){
		for(int j=0;j<lastClusterIndex;j++)
			if(c==C[j]) // to avoid duplicates
				return;
		C[lastClusterIndex]=c;
		lastClusterIndex++;
	}
	
	Cluster get(int i){
		return C[i];
	}

	ClusterSet mergeClosestClusters(ClusterDistance distance, Data data){
		if ( 2 <= lastClusterIndex ) {
			double minD = Double.MAX_VALUE;
			Cluster cluster1 = null;
			Cluster cluster2 = null;

			for (int i = 0; i < this.C.length; i++) {
				Cluster c1 = get(i);
				for(int j = i+1; j<this.C.length; j++){
					Cluster c2 = get(j);
					double d = distance.distance(c1, c2, data);
					if (d < minD) {
						minD = d;
						cluster1 = c1;
						cluster2 = c2;
					}
				}
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
		else {
			return this;
		}
	}

	public String toString(){
		String str="";
		for(int i=0;i<C.length;i++){
			if (C[i]!=null){
				str+="cluster"+i+":["+C[i]+"]\n";
			}
		}
		return str;
		
	}

	
	String toString(Data data){
		String str="";
		for(int i=0;i<C.length;i++){
			if (C[i]!=null){
				str+="cluster"+i+":"+C[i].toString(data)+"\n";
			}
		}
		return str;
		
	}

}
