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
		Data data = loadData();

		System.out.println("Data:");
		System.out.println(data);

		double[][] distancematrix = calculateDistanceMatrix(data);

		if (distancematrix != null) {
			printDistanceMatrix(distancematrix);
		}

		chooseClusteringOption(data);
	}

	private static Data loadData() {
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
		return data;
	}

	private static double[][] calculateDistanceMatrix(Data data) {
		double[][] distancematrix = null;
		try {
			distancematrix = data.distance();
		} catch (InvalidSizeException e) {
			System.out.println(e.getMessage());
		}
		return distancematrix;
	}

	private static void printDistanceMatrix(double[][] distancematrix) {
		System.out.println("Distance matrix:");
		for (double[] row : distancematrix) {
			for (double value : row) {
				System.out.printf("%.2f\t", value);
			}
			System.out.println();
		}
		System.out.println();
	}

	private static HierachicalClusterMiner chooseClusteringOption(Data data) {
		int choice;
		do {
			System.out.println("Scegli un'opzione:");
			System.out.print("1) Carica un oggetto serializzato\n2) Crea un nuovo oggetto serializzato\n> ");
			choice = Keyboard.readInt();

			if (choice != 1 && choice != 2) {
				System.out.println("Scelta non valida.\n");
			}
		} while (choice != 1 && choice != 2);

		if (choice == 1) {
			return loadSerializedObject(data);
		} else {
			return createNewClusteringObject(data);
		}
	}

	private static HierachicalClusterMiner loadSerializedObject(Data data) {
		HierachicalClusterMiner clustering = null;
		boolean validChoice = false;
		while (!validChoice) {
			System.out.print("\nInserisci il percorso completo del file da caricare (es: /home/utente/test_clustering/cluster_miner.ser):\n> ");
			String filePath = Keyboard.readString();
			try {
				clustering = HierachicalClusterMiner.loadHierachicalClusterMiner(filePath);
				if (clustering.getDepth() > data.getNumberOfExample()) {
					System.out.println("Numero di esempi maggiore della profondità del dendrogramma!\n");
				} else {
					validChoice = true;
					System.out.println("\nOggetto caricato con successo.\n");
					System.out.println(clustering);
					System.out.print(clustering.toString(data));
				}
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
		return clustering;
	}

	private static HierachicalClusterMiner createNewClusteringObject(Data data) {
		HierachicalClusterMiner clustering = null;
		int retry;
		do {
			retry = 0;
			int k;
			System.out.print("\nInserisci la profondità desiderata del dendrogramma (<=" + data.getNumberOfExample() + ")\n> ");
			k = Keyboard.readInt();
			try {
				clustering = new HierachicalClusterMiner(k);
                performClustering(data, clustering);
            } catch (InvalidDepthException e) {
				System.out.print(e.getMessage());
				retry = 1;
			}
		} while (retry == 1);

		return clustering;
	}

	private static void performClustering(Data data, HierachicalClusterMiner clustering) {
		int distanceType = chooseDistanceType();
		ClusterDistance distance = createDistanceObject(distanceType);
		String distancePrint = distanceType == 1 ? "Single link distance" : "Average link distance";

		try {
			clustering.mine(data, distance);
			System.out.println(distancePrint);
			System.out.println(clustering);
			System.out.print(clustering.toString(data));

			if (askToSaveObject()) {
				saveClusteringObject(clustering);
			}
		} catch (InvalidDepthException | InvalidSizeException | InvalidClustersNumberException e) {
			System.out.println(e.getMessage());
		}
	}

	private static int chooseDistanceType() {
		int distanceType;
		do {
			System.out.print("\nScegli un tipo di misura di distanza tra cluster calcolare:\n1) Single link distance\n2) Average link distance\n> ");
			distanceType = Keyboard.readInt();
			if (distanceType != 1 && distanceType != 2) {
				System.out.println("Scelta non valida\n");
			}
		} while (distanceType != 1 && distanceType != 2);
		return distanceType;
	}

	private static ClusterDistance createDistanceObject(int distanceType) {
		return distanceType == 1 ? new SingleLinkDistance() : new AverageLinkDistance();
	}

	private static boolean askToSaveObject() {
		String saveChoice;
		do {
			System.out.print("Vuoi salvare l'oggetto creato? (s/n)\n> ");
			saveChoice = Keyboard.readString().trim().toLowerCase();
			if (!saveChoice.equals("s") && !saveChoice.equals("n")) {
				System.out.println("Scelta non valida.\n");
			}
		} while (!saveChoice.equals("s") && !saveChoice.equals("n"));
		return saveChoice.equals("s");
	}

	private static void saveClusteringObject(HierachicalClusterMiner clustering) {
		String filePath;
		System.out.print("\nInserisci il percorso e il nome del file (con estensione) dove salvare l'oggetto:\n> ");
		filePath = Keyboard.readString().trim();
		try {
			clustering.salva(filePath);
			System.out.println("\nOggetto salvato con successo.");
		} catch (IOException e) {
			System.out.println("Errore di salvataggio: " + e.getMessage());
		}
	}
}
