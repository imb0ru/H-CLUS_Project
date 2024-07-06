package clustering;
import data.Data;
import data.InvalidSizeException;
import distance.ClusterDistance;

import java.io.*;

/**
 * Classe HierachicalClusterMiner
 * modella il processo di clustering
 *
 * @author Team MAP Que Nada
 */
public class HierachicalClusterMiner implements Serializable {
	/** dendrogramma */
	private final Dendrogram dendrogram;
	/** percorso della directory di salvataggio/caricamento degli oggetti serializzati */
	private static final String DIRECTORY_PATH = "./Server/saved/";


	/**
	 * Costruttore
	 * crea un'istanza di classe HierachicalClusterMiner con profondità depth
	 * @param depth profondità del dendrogramma
	 * @throws InvalidDepthException se la profondità è minore di 1
	 */
	public HierachicalClusterMiner(int depth) throws InvalidDepthException {
		dendrogram= new Dendrogram(depth);
	}

	/**
	 * Metodo getDepth
	 * Restituisce la profondità del dendrogramma
	 * @return la profondità del dendrogramma
	 */
	public int getDepth() {
		return dendrogram.getDepth();
	}

	/**
	 * Metodo mine
	 * calcola il clustering del dataset data
	 * @param data dataset su cui calcolare il clustering
	 * @param distance interfaccia di calcolo distanza tra cluster
	 * @throws InvalidDepthException se la profondità del dendrogramma è minore del numero di esempi
	 * @throws InvalidSizeException se la dimensione del cluster è minore di 2
	 * @throws InvalidClustersNumberException se il numero di cluster è minore di 2
	 */
	public void mine(Data data, ClusterDistance distance) throws InvalidDepthException, InvalidSizeException, InvalidClustersNumberException {
		if (getDepth() > data.getNumberOfExample()) {
			throw new InvalidDepthException("Numero di Esempi maggiore della profondità del dendrogramma!\n");
		}

		ClusterSet level0 = new ClusterSet(data.getNumberOfExample());
		for (int i = 0; i < data.getNumberOfExample(); i++) {
			Cluster c = new Cluster();
			c.addData(i);
			level0.add(c);
		}
		dendrogram.setClusterSet(level0, 0);
		for (int i = 1; i < getDepth(); i++) {
            ClusterSet nextlevel;
            try {
                nextlevel = dendrogram.getClusterSet(i - 1).mergeClosestClusters(distance, data);
				dendrogram.setClusterSet(nextlevel, i);
			} catch (InvalidSizeException | InvalidClustersNumberException e) {
				i = getDepth();
                throw e;
            }
        }

	}

	/**
	 * Metodo toString
	 * Restituisce una rappresentazione testuale del dendrogramma
	 * @return una rappresentazione testuale del dendrogramma
	 */
	public String toString() {
		return dendrogram.toString();
	}

	/**
	 * Metodo toString
	 * Restituisce una rappresentazione testuale del dendrogramma
	 * @param data dataset di esempi
	 * @throws InvalidDepthException se la profondità del dendrogramma è minore del numero di esempi
	 * @return una rappresentazione testuale del dendrogramma
	 */
	public String toString(Data data) throws InvalidDepthException {
		return dendrogram.toString(data);
	}

	/**
	 * Metodo statico per caricare un'istanza di HierachicalClusterMiner da un file
	 * @param fileName nome del file da cui caricare l'istanza
	 * @return l'istanza caricata di HierachicalClusterMiner
	 * @throws FileNotFoundException se il file non viene trovato
	 * @throws IOException se si verifica un errore di input/output
	 * @throws ClassNotFoundException se la classe dell'oggetto serializzato non viene trovata
	 * @throws IllegalArgumentException se il nome del file è nullo o vuoto
	 */
	public static HierachicalClusterMiner loadHierachicalClusterMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException, IllegalArgumentException {
		if (fileName == null || fileName.trim().isEmpty()) {
			throw new IllegalArgumentException("Il nome del file non può essere nullo o vuoto");
		}
		String filePath = DIRECTORY_PATH + fileName;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
			return (HierachicalClusterMiner) ois.readObject();
		}
	}

	/**
	 * Metodo per salvare l'istanza corrente di HierachicalClusterMiner su un file
	 * @param fileName nome del file su cui salvare l'istanza
	 * @throws FileNotFoundException se il file non viene trovato
	 * @throws IOException se si verifica un errore di input/output
	 * @throws IllegalArgumentException se il nome del file è nullo o vuoto
	 */
	public void salva(String fileName) throws FileNotFoundException, IOException, IllegalArgumentException {
		if (fileName == null || fileName.trim().isEmpty()) {
			throw new IllegalArgumentException("Il nome del file non può essere nullo o vuoto");
		}
		fileName = fileName.replace("\\", File.separator).replace("/", File.separator);

		if (fileName.matches(".*[<>:\"|?*].*")) {
			throw new IOException("Errore: Il percorso contiene caratteri non validi. Riprova.\n");
		}

		String filePath = DIRECTORY_PATH + fileName;
		File file = new File(filePath);

		if (file.exists()) {
			throw new IOException("Errore: Il file esiste già. Riprova.\n");
		}

		File parentDir = file.getParentFile();
		if (parentDir != null && !parentDir.exists()) {
			if (parentDir.mkdirs()) {
				System.out.println("Directory creata: " + parentDir.getAbsolutePath());
			} else {
				throw new IOException("Impossibile creare la directory: " + parentDir.getAbsolutePath() + "\n");
			}
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
			oos.writeObject(this);
		}
	}

}

