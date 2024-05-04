import data.*;
import clustering.*;
import distance.*;

/**
 * classe MainTest
 * Classe di test per il clustering
 *
 * @author Team MAP Que Nada
 */
public class MainTest {
	public static void main(String[] args) {
		char continua;
		System.out.println("Data:");
		Data data = new Data();
		System.out.println(data);

		double[][] distancematrix = null;
		try {
			distancematrix = data.distance();
			System.out.println("Distance matrix:");
			for (double[] doubles : distancematrix) {
				for (int j = 0; j < distancematrix.length; j++)
					System.out.printf("%.2f\t", doubles[j]);
				System.out.println();
			}
			System.out.println();
		} catch (InvalidSizeException e) {
			System.out.println(e.getMessage());
		}

		int k;
		System.out.print("Inserire la profondità desiderata del dendrogramma (<=" + data.getNumberOfExample() + ")\n> ");
		k = Keyboard.readInt();
		HierachicalClusterMiner clustering = new HierachicalClusterMiner(k);
		System.out.println();

		int distance_type;
		do {
			System.out.print("Scegli un tipo  di  misura  di distanza tra cluster calcolare:\n1) Single link distance\n2) Average link distance\n> ");
			distance_type = Keyboard.readInt();
			if (distance_type != 1 && distance_type != 2)
				System.out.println("tipo_distanza non valida\n");
		} while (distance_type != 1 && distance_type != 2);
		System.out.println();

		ClusterDistance distance;
		if (distance_type == 1) {
			System.out.println("Single link distance");
			distance = new SingleLinkDistance();
		} else {
			System.out.println("Average link distance");
			distance = new AverageLinkDistance();
		}

		try {
			clustering.mine(data, distance);
			System.out.println(clustering);
			System.out.println(clustering.toString(data));
		} catch (InvalidDepthException | InvalidSizeException | InvalidClustersNumberException e) {
			System.out.println(e.getMessage());
		}
    }
}