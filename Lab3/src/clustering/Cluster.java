package clustering;
import data.Data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * classe Cluster
 * modella un cluster come la collezione delle posizioni occupate
 * dagli esempi raggruppati nel Cluster nel vettore data dell’oggetto
 * che modella il dataset su cui il clustering è calcolato(istanza di Data)
 *
 * @author Team MAP Que Nada
 */
public class Cluster implements Iterable<Integer>, Cloneable {
	private Set<Integer> clusteredData = new TreeSet<>();

	/**
	 * metodo iterator
	 * Restituisce un iteratore per gli elementi di Cluster
	 *
	 * @return un iteratore per la lista di valori interi
	 */
	@Override
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}

	/**
	 * metodo addData
	 * aggiunge l'indice di posizione id al cluster
	 * controlla che l'indice non sia già presente nel cluster
	 *
	 * @param id indice da aggiungere al cluster
	 */
	void addData(int id) {
		clusteredData.add(id); // TreeSet evita duplicati
	}

	/**
	 * metodo getSize
	 * restituisce la dimensione del cluster
	 *
	 * @return dimensione del cluster
	 */
	public int getSize() {
		return clusteredData.size();
	}

	/**
	 * metodo clone
	 * crea una copia del cluster
	 *
	 * @return copia del cluster
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		try {
			Cluster copy = (Cluster) super.clone();
			copy.clusteredData = new TreeSet<>(this.clusteredData);
			return copy;
		} catch (CloneNotSupportedException e) {
			throw new CloneNotSupportedException("Clonazione non supportata!");
		}
	}



	/**
	 * metodo mergeCluster
	 * crea un nuovo cluster che è la fusione del cluster corrente e del cluster c
	 *
	 * @param c cluster da unire al cluster corrente
	 * @return newC cluster che è la fusione del cluster corrente e del cluster c
	 */
	Cluster mergeCluster(Cluster c) throws CloneNotSupportedException {
		Cluster newC = (Cluster) this.clone();
		newC.clusteredData.addAll(c.clusteredData);
		return newC;
	}


	/**
	 * metodo toString
	 * restituisce una stringa contenente gli indici degli esempi raggruppati nel cluster
	 *
	 * @return str stringa contenente gli indici degli esempi raggruppati nel cluster
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		Iterator<Integer> iterator = clusteredData.iterator();
		while (iterator.hasNext()) {
			str.append(iterator.next());
			if (iterator.hasNext()) {
				str.append(",");
			}
		}
		return str.toString();
	}

	/**
	 * metodo toString
	 * restituisce una stringa contenente gli esempi raggruppati nel cluster
	 *
	 * @param data oggetto di classe Data che modella il dataset su cui il clustering è calcolato
	 * @return str stringa contenente gli esempi raggruppati nel cluster
	 */
	public String toString(Data data) {
		StringBuilder str = new StringBuilder();
		for (Integer id : clusteredData) {
			str.append("<[").append(data.getExample(id)).append("]>");
		}
		return str.toString();
	}



}
