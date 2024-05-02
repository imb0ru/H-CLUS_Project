import data.*;
import clustering.*;
import distance.*;
import static java.lang.Character.toLowerCase;

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

		double[][] distancematrix = data.distance();
		System.out.println("Distance matrix:");
		for (double[] doubles : distancematrix) {
			for (int j = 0; j < distancematrix.length; j++)
				System.out.printf("%.2f\t", doubles[j]);
			System.out.println();
		}
		System.out.println();

		int k; //era =5
		do {
			do {
				System.out.print("Inserire la profondità desiderata del dendrogramma (<=" + data.getNumberOfExample() + ")\n> ");
				k = Keyboard.readInt();
				if (k <= 0 || k > data.getNumberOfExample())
					System.out.println("Profondità non valida\n");
			} while (k <= 0 || k > data.getNumberOfExample());
			HierachicalClusterMiner clustering = new HierachicalClusterMiner(k);
			System.out.println();
			
			int tipo_distanza;
			do {
				System.out.print("Scegli un tipo  di  misura  di distanza tra cluster calcolare:\n1) Single link distance\n2) Average link distance\n> ");
				tipo_distanza = Keyboard.readInt();
				if (tipo_distanza != 1 && tipo_distanza != 2)
					System.out.println("tipo_distanza non valida\n");
			} while (tipo_distanza != 1 && tipo_distanza != 2);
			System.out.println();

			ClusterDistance distance;
			if (tipo_distanza == 1) {
				System.out.println("Single link distance");
				distance = new SingleLinkDistance();
				clustering.mine(data, distance);
				System.out.println(clustering);
				System.out.println(clustering.toString(data));
			} else {
				System.out.println("Average link distance");
				distance = new AverageLinkDistance();
				clustering.mine(data, distance);
				System.out.println(clustering);
				System.out.println(clustering.toString(data));
			}

			do{
				System.out.print("Vuoi fare un altro clustering? (s/n)\n> ");
				continua = toLowerCase(Keyboard.readChar());
			} while (continua != 's' && continua != 'n');
			System.out.println();
		} while (continua == 's');
	}
}