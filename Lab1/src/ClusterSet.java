
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
		if ( 2 < lastClusterIndex ) {
			double minD = Double.MAX_VALUE;
			int mini = -1;
			int minj = -1;

			for (int i = 0; i < lastClusterIndex-1; i++) {
				for(int j = i+1; j<lastClusterIndex; j++){
					double d = distance.distance(C[i], C[j], data);
					if (d < minD) {
						minD = d;
						mini = i;
						minj = j;
					}
				}
			}
			Cluster mergedCluster = C[mini].mergeCluster(C[minj]);
			ClusterSet finalClusterSet = new ClusterSet(lastClusterIndex-1);
			for(int i=0; i<lastClusterIndex; i++){
				if(i!=mini && i!=minj){
					finalClusterSet.add(C[i]);
				}
			}
			finalClusterSet.add(mergedCluster);

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
				str+="cluster"+i+":"+C[i]+"\n";
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
