import data.*;
import clustering.*;
import distance.*;
import java.io.*;

/**
 * Classe MainTest
 * Classe di test per il clustering
 *
 * @autor Team MAP Que Nada
 */
public class MainTest {
	public static void main(String[] args) {
		Data data = null;
		while (data == null) {
			System.out.print("Inserire il nome della tabella nel database:\n> ");
			String tableName = Keyboard.readString();
			try {
				data = new Data(tableName);
			} catch (NoDataException e) {
				System.out.println("Errore nella creazione dell'oggetto Data: " + e.getMessage());
			}
		}

		System.out.println("Data:");
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

		int retry;
		do {
			retry = 0;
			System.out.print("Vuoi caricare un oggetto HierachicalClusterMiner precedentemente serializzato? (s/n)\n> ");
			String choice = Keyboard.readString();

			HierachicalClusterMiner clustering = null;

			if (choice.equalsIgnoreCase("s")) {
				System.out.print("Inserire il percorso del file serializzato:\n> ");
				String filePath = Keyboard.readString();
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
					clustering = (HierachicalClusterMiner) ois.readObject();
					System.out.println("Oggetto HierachicalClusterMiner caricato correttamente.\n");
				} catch (IOException | ClassNotFoundException e) {
					System.out.println("Errore nel caricamento del file: " + e.getMessage());
					retry = 1;
				}
			} else if (choice.equalsIgnoreCase("n")) {
				int k;
				System.out.print("Inserire la profondità desiderata del dendrogramma (<=" + data.getNumberOfExample() + ")\n> ");
				k = Keyboard.readInt();
				try {
					clustering = new HierachicalClusterMiner(k);
				} catch (InvalidDepthException e) {
					System.out.println(e.getMessage());
					retry = 1;
				}
				System.out.println();

				if (retry == 0) {
					int distance_type;

					System.out.print("Scegli un tipo di misura di distanza tra cluster:\n1) Single link distance\n2) Average link distance\n> ");
					distance_type = Keyboard.readInt();
					if (distance_type != 1 && distance_type != 2) {
						System.out.println("Scelta non valida\n");
						retry = 1;
					}
					System.out.println();

					if (retry == 0) {
						ClusterDistance distance;
						String distance_print = "";
						if (distance_type == 1) {
							distance_print = "Single link distance";
							distance = new SingleLinkDistance();
						} else {
							distance_print = "Average link distance";
							distance = new AverageLinkDistance();
						}

						try {
							clustering.mine(data, distance);
							System.out.println(distance_print);
							System.out.println(clustering);
							System.out.println(clustering.toString(data));

							System.out.print("Inserire il percorso per salvare l'oggetto serializzato:\n> ");
							String filePath = Keyboard.readString();
							try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
								oos.writeObject(clustering);
								System.out.println("Oggetto HierachicalClusterMiner serializzato correttamente.\n");
							} catch (IOException e) {
								System.out.println("Errore nel salvataggio del file: " + e.getMessage());
							}
						} catch (InvalidDepthException | InvalidSizeException | InvalidClustersNumberException e) {
							System.out.println(e.getMessage());
							retry = 1;
						}
					}
				}
			} else {
				System.out.println("Scelta non valida\n");
				retry = 1;
			}
		} while (retry == 1);
	}
}
