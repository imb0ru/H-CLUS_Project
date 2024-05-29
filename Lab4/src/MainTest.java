import data.*;
import clustering.*;
import distance.*;

import java.io.*;

/**
 * Classe MainTest
 * Classe di test per il clustering
 *
 * @author Team MAP Que Nada
 */
public class MainTest {
	public static void main(String[] args) {
		Data data = null;
		boolean validData = false;
		while (!validData) {
			try {
				System.out.print("Inserisci il nome della tabella nel database:\n> ");
				String tableName = Keyboard.readString();
				data = new Data(tableName);
				validData = true;
			} catch (NoDataException e) {
				System.out.println(e.getMessage());
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

		HierachicalClusterMiner clustering = null;
		int choice;
		do {
			System.out.println("Scegli un'opzione:");
			System.out.print("1) Carica un oggetto serializzato\n2) Crea un nuovo oggetto serializzato\n> ");
			choice = Keyboard.readInt();

			if (choice != 1 && choice != 2)
				System.out.println("Scelta non valida.\n");
		} while (choice != 1 && choice != 2);
		if (choice == 1) {
			boolean validChoice = false;
			while (!validChoice) {
				System.out.print("\nInserisci il percorso completo del file da caricare (es: /home/utente/test_clustering/cluster_miner.ser):\n> ");
				String filePath = Keyboard.readString();
				try {
					clustering = HierachicalClusterMiner.loadHierachicalClusterMiner(filePath);
					validChoice = true;
					System.out.println("\nOggetto caricato con successo.\n");
					System.out.println(clustering);
					System.out.print(clustering.toString(data));
				} catch (FileNotFoundException e) {
					System.out.println("File non trovato: " + e.getMessage());
				} catch (IOException e) {
					System.out.println("Errore di input/output: " + e.getMessage());
				} catch (ClassNotFoundException e) {
					System.out.println("Classe non trovata: " + e.getMessage());
				} catch (InvalidDepthException | IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			}
		} else {
			int retry;
			do {
				retry = 0;
				int k;
				System.out.print("\nInserisci la profondità desiderata del dendrogramma (<=" + data.getNumberOfExample() + ")\n> ");
				k = Keyboard.readInt();
				try {
					clustering = new HierachicalClusterMiner(k);
				} catch (InvalidDepthException e) {
					System.out.print(e.getMessage());
					retry = 1;
				}
			} while (retry == 1);
			System.out.println();

			int distance_type;
			do{
				System.out.print("Scegli un tipo di misura di distanza tra cluster calcolare:\n1) Single link distance\n2) Average link distance\n> ");
				distance_type = Keyboard.readInt();
				if (distance_type != 1 && distance_type != 2) {
					System.out.println("Scelta non valida\n");
				}
			} while (distance_type != 1 && distance_type != 2);
			System.out.println();

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
				System.out.print(clustering.toString(data));

				String saveChoice;
				do {
					System.out.print("Vuoi salvare l'oggetto creato? (s/n)\n> ");
					saveChoice = Keyboard.readString().trim().toLowerCase();
					if (!saveChoice.equals("s") && !saveChoice.equals("n"))
						System.out.println("Scelta non valida.\n");
				}while (!saveChoice.equals("s") && !saveChoice.equals("n"));

				if (saveChoice.equals("s")) {
					String filePath;
					System.out.print("\nInserisci il percorso e il nome del file (senza estensione) dove salvare l'oggetto:\n> ");
					filePath = Keyboard.readString().trim();
					clustering.salva(filePath);
					System.out.println("\nOggetto salvato con successo.");
				}
			} catch (InvalidDepthException | InvalidSizeException | InvalidClustersNumberException | IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}

