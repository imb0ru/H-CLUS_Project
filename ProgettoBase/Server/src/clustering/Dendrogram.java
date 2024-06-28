package clustering;

import data.Data;

import java.io.Serializable;

/**
 * Classe Dendrogram
 * Modella un dendrogramma
 *
 * @author Team MAP Que Nada
 */
class Dendrogram implements Serializable {
    /** array di ClusterSet */
    private final ClusterSet[] tree; //modella il dendrogramma

    /**
     * Costruttore
     * @param depth profondità del dendrogramma
     */
    Dendrogram(int depth) throws InvalidDepthException {
        if (depth <= 0) {
            throw new InvalidDepthException("Profondità non valida!\n");
        }
        tree=new ClusterSet[depth];
    }

    /**
     * Metodo getClusterSet
     * Inserisce il cluster set c al livello level di tree
     * @param level livello del dendrogramma in cui inserire il cluster set
     */
    void setClusterSet(ClusterSet c, int level){
        tree[level]=c;
    }

    /**
     * Metodo getClusterSet
     * Restituisce il cluster set al livello level di tree
     * @param level livello del dendrogramma da restituire
     * @return il cluster set al livello level di tree
     */
    ClusterSet getClusterSet(int level) {
        return tree[level];
    }

    /**
     * Metodo getDepth
     * Restituisce la profondità del dendrogramma
     * @return la profondità del dendrogramma
     */
    int getDepth() {
        return tree.length;
    }

    /**
     * Metodo toString
     * Restituisce una rappresentazione testuale del dendrogramma
     * @return una rappresentazione testuale del dendrogramma
     */
    public String toString(){
        StringBuilder v= new StringBuilder();
        for (int i=0;i<tree.length;i++)
            v.append("level").append(i).append(":\n").append(tree[i]).append("\n");
        return v.toString();
    }

    /**
     * Metodo toString
     * Restituisce una rappresentazione testuale del dendrogramma
     * @param data dataset di esempi
     * @return una rappresentazione testuale del dendrogramma
     */
    public String toString(Data data) {
        StringBuilder v= new StringBuilder();
        for (int i=0;i<tree.length;i++)
            v.append("level").append(i).append(":\n").append(tree[i].toString(data)).append("\n");
        return v.toString();
    }

}
