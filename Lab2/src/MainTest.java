/**
 * classe MainTest
 * Classe di test per il clustering
 *
 * @author Team MAP Que Nada
 */
public class MainTest {
	public static void main(String[] args) {
		Data data =new Data();
		System.out.println(data);
		int k=5;
		HierachicalClusterMiner clustering=new HierachicalClusterMiner(k);

		System.out.println("Single link distance");
		ClusterDistance distance=new SingleLinkDistance();

		double [][] distancematrix=data.distance();
		System.out.println("Distance matrix:\n");
        for (double[] doubles : distancematrix) {
            for (int j = 0; j < distancematrix.length; j++)
                System.out.printf("%.2f\t", doubles[j]);
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