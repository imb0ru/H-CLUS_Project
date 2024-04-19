/**
 * Classe Example
 * modella le entità esempio inteso come vettore di valori reali
 *
 * @author Team MAP Que Nada
 */
class Example {
    private Double [] example; //vettore di valori reali

    /**
     * Costruttore, crea un'istanza di classe Example di dimensione length
     *
     * @param length dimensione del vettore
     */
    public Example(int length){
        example = new Double[length];
    }

    /**
     * metodo set
     * modifica example inserendo value in posizione index
     *
     * @param index indice in cui inserire value
     * @param value valore da inserire
     * @return void
     */
    public void set(int index, Double value){
        example[index] = value;
    }

    /**
     * metodo get
     * restituisce il valore di example in posizione index
     *
     * @param index indice del valore da prendere
     * @return value valore memorizzato in example in posizione index
     */
    public Double get(int index){
        return example[index];
    }

    /**
     * metodo distance
     * calcola la distanza euclidea tra l'istanza this.Example e l'istanza newE.Example
     *
     * @param  newE istanza di classe Example con cui calcolare la distanza
     * @return sum somma delle distanze tra i valori delle due istanze Example
     */
    public Double distance(Example newE){
        Double sum = 0.0;
        for(int i = 0; i < example.length; i++){
            sum += Math.pow(example[i] - newE.get(i), 2);
        }
        return sum;
    }

/**
     * metodo toString
     * restituisce una stringa contenente i valori di example
     *
     * @return s stringa contenente i valori di example
     */
    public String toString() {
        String s = "";
        for (Double aDouble : example) {
            s += aDouble + " ";
        }
        return s;
    }
}

