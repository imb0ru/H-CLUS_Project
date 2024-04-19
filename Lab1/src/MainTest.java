
public class MainTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Data data =new Data();
		System.out.println(data);
		int k=5;
		HierachicalClusterMiner clustering=new HierachicalClusterMiner(k);

		System.out.println("Single link distance");
		ClusterDistance distance=new SingleLinkDistance();

		double [][] distancematrix=data.distance();
		System.out.println("Distance matrix:\n");
		for(int i=0;i<distancematrix.length;i++) {
			for(int j=0;j<distancematrix.length;j++)
				System.out.print(String.format("%.2f\t",distancematrix[i][j]));
			System.out.println();
		}
		clustering.mine(data,distance);
		System.out.println(clustering);
		System.out.println(clustering.toString(data));


		System.out.println("Average link distance");
		distance=new AverageLinkDistance();
		clustering.mine(data,distance);
		System.out.println(clustering);
		System.out.println(clustering.toString(data));
	}

}
