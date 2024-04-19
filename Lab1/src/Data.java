/**
 * classe Data
 * avvalora un oggetto data predefinito (fornito dal docente)
 *
 * @author Team MAP Que Nada
 */
public class Data {
    private Example data [];
    private int numberOfExamples;

    public Data();

    /**
     *metodo getNumberOfExample
     * restituisce il numero degli esempi memorizzati in data
     *
     * @return numberOfExamples rappresenta il numero di esempi nel dataset
     */
    int getNumberOfExample () {
        int numberOfExamples = 0;
        for (int i = 0; i < data.length; i++) {
            numberOfExamples ++;
        }
        return numberOfExamples;
    }
    /**
     *metodo getExample
     * restituisce il numero degli esempi memorizzati in data
     *
     * @return numberOfExamples rappresenta il numero di esempi nel dataset
     */
    Example getExample (int exampleIndex) {
        return data[exampleIndex];
    }

    double [][] distance() {

    }

    public String toString() {
        String s;
        for (int i = 0; i < data.length; i++) {
            s= i" "+data[i]+" ";
        }
        return s;
    }


}
