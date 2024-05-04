package data;
/**
 * classe Data
 * avvalora un oggetto data predefinito (fornito dal docente)
 *
 * @author Team MAP Que Nada
 */
public class Data {
    private Example[] data; //rappresenta il dataset
    private int numberOfExamples; //numero di esempi nel dataset

    /**
     * Costruttore
     * crea un'istanza di classe Data con un dataset predefinito
     */
    public Data(){
        //data
        data = new Example [5];
        Example e=new Example(3);
        e.set(0, 1.0);
        e.set(1, 2.0);
        e.set(2, 0.0);
        data[0]=e;

        e=new Example(3);
        e.set(0, 0.0);
        e.set(1, 1.0);
        e.set(2, -1.0);
        data[1]=e;

        e=new Example(3);
        e.set(0, 1.0);
        e.set(1, 3.0);
        e.set(2, 5.0);
        data[2]=e;

        e=new Example(3);
        e.set(0, 1.0);
        e.set(1, 3.0);
        e.set(2, 4.0);
        data[3]=e;

        e=new Example(3);
        e.set(0, 2.0);
        e.set(1, 2.0);
        e.set(2, 0.0);
        data[4]=e;

        // numberOfExamples
        numberOfExamples=5;
    }

    /**
     * metodo getNumberOfExample
     * restituisce il numero degli esempi memorizzati in data
     *
     * @return numberOfExamples rappresenta il numero di esempi nel dataset
     */
    public int getNumberOfExample () {
        return numberOfExamples;
    }

    /**
     * metodo getExample
     * restituisce l'elemento dell'istanza data in posizione exampleIndex
     *
     * @param exampleIndex indice dell'elemento da restituire
     * @return data[exampleIndex] elemento in posizione exampleIndex
     */
    public Example getExample (int exampleIndex) {
        return data[exampleIndex];
    }

    /**
     * metodo distance
     * restituisce la matrice triangolare superiore delle distanze Euclidee
     * calcolate tra gli esempi memorizzati in data.
     * Tale matrice va avvalorata usando il metodo distance di Example
     *
     * @return dist matrice delle distanze tra gli esempi del dataset
     */
    public double[][] distance() {
        double[][] dist = new double[numberOfExamples][numberOfExamples];
        for (int i = 0; i < numberOfExamples; i++) {
            for (int j = i + 1; j < numberOfExamples; j++) {
                double d = data[i].distance(data[j]);
                dist[i][j] = d;
                dist[j][i] = 0; // Riflessione nella metà inferiore
            }
        }
        for (int i=0; i < numberOfExamples; i++) {
            dist[i][i] = 0;
        }
        return dist;
    }

    /**
     * metodo toString
     * crea una stringa in cui memorizza gli esempi memorizzati
     * nell’attributo data, opportunamente enumerati.
     *
     * @return la stringa con gli esempi in data
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < numberOfExamples; i++) {
            s += i + ":[" + data[i] + "]\n";
        }
        return s;
    }
    /**
     * metodo main
     * Crea l'oggetto Data e lo stampa a video,
     * poi calcola e stampa la matrice delle distanze Euclidee
     * calcolate tra gli esempi memorizzati in data.
     *
     */
    public static void main(String[] args) {
        Data trainingSet = new Data();
        System.out.println(trainingSet);
        double[][] distanceMatrix = trainingSet.distance();
        System.out.println("Distance matrix:\n");
        for (double[] doubles : distanceMatrix) {
            for (int j = 0; j < distanceMatrix.length; j++)
                System.out.printf("%.2f\t", doubles[j]); // Utilizzo String.format() per formattare il numero con due decimali e aggiungere il tab
            System.out.println();
        }
    }


}
