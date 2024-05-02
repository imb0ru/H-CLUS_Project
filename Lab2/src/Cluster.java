package clustering;
/**
 * classe Cluster
 * modella un cluster come la collezione delle posizioni occupate
 * dagli esempi raggruppati nel Cluster nel vettore data dell’oggetto
 * che modella il dataset su cui il clustering è calcolato(istanza di Data)
 *
 * @author Team MAP Que Nada
 */
class Cluster {
	private Integer[] clusteredData =new Integer[0];

	/**
	 * metodo addData
	 * aggiunge l'indice di posizione id al cluster
	 * controlla che l'indice non sia già presente nel cluster
	 *
	 * @param id indice da aggiungere al cluster
	 */
	void addData(int id){
        for (Integer clusteredData : clusteredData)
            if (id == clusteredData)
                return;
		Integer[] clusteredDataTemp =new Integer[clusteredData.length+1];
		System.arraycopy(clusteredData, 0, clusteredDataTemp, 0, clusteredData.length);
		clusteredData=clusteredDataTemp;
		clusteredData[clusteredData.length-1]=id;			
	}

	/**
	 * metodo getSize
	 * restituisce la dimensione del cluster
	 *
	 * @return dimensione del cluster
	 */
	int getSize() {
		return clusteredData.length;
	}

	/**
	 * metodo getElement
	 * restituisce l'elemento in posizione i del cluster
	 *
	 * @param i indice dell'elemento da restituire
	 * @return elemento in posizione i del cluster
	 */
	int getElement(int i) {
		return clusteredData[i];
	}

	/**
	 * metodo createACopy
	 * crea una copia del cluster
	 *
	 * @return copia del cluster
	 */
	Cluster createACopy() {
			Cluster copyC=new Cluster();
			for (int i=0;i<getSize();i++)
				copyC.addData(clusteredData[i]);
			return copyC;
	}

	/**
	 * metodo mergeCluster
	 * crea un nuovo cluster che è la fusione del cluster corrente e del cluster c
	 *
	 * @param c cluster da unire al cluster corrente
	 * @return newC cluster che è la fusione del cluster corrente e del cluster c
	 */
	Cluster mergeCluster (Cluster c)
	{
		Cluster newC=new Cluster();
		for (int i=0;i<getSize();i++)
			newC.addData(clusteredData[i]);
		for (int i=0;i<c.getSize();i++)
			newC.addData(c.clusteredData[i]);
		return newC;

	}

	/**
	 * metodo toString
	 * restituisce una stringa contenente gli indici degli esempi raggruppati nel cluster
	 *
	 * @return str stringa contenente gli indici degli esempi raggruppati nel cluster
	 */
	public String toString() {
		String str="";
		for (int i=0;i<clusteredData.length-1;i++)
			str+=clusteredData[i]+",";
		str+=clusteredData[clusteredData.length-1];
		return str;
	}

	/**
	 * metodo toString
	 * restituisce una stringa contenente gli esempi raggruppati nel cluster
	 *
	 * @param data oggetto di classe Data che modella il dataset su cui il clustering è calcolato
	 * @return str stringa contenente gli esempi raggruppati nel cluster
	 */
	public String toString(Data data){
		String str="";

        for (Integer clusteredDatum : clusteredData)
			str += "<[" + data.getExample(clusteredDatum) + "]>";

		return str;

	}



}
