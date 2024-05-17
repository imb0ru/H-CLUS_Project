package clustering;
import data.Data;
/**
 * classe Dendrogram
 * Modella un dendrogramma
 *
 * @author Team MAP Que Nada
 */
import java.util.ArrayList;
import java.util.List;

class Dendrogram {
    private List<ClusterSet> tree; // Modella il dendrogramma

    /**
     * Costruttore
     *
     * @param depth profondità del dendrogramma
     */
    Dendrogram(int depth) throws InvalidDepthException {
        if (depth <= 0) {
            throw new InvalidDepthException("Profondità non valida!\n");
        }
        tree = new ArrayList<>(depth);
    }

    /**
     * Metodo setClusterSet
     * Inserisce il cluster set c al livello level di tree
     *
     * @param level livello del dendrogramma in cui inserire il cluster set
     */
    void setClusterSet(ClusterSet c, int level) {
        tree.set(level, c);
    }

    /**
     * Metodo getClusterSet
     * Restituisce il cluster set al livello level di tree
     *
     * @param level livello del dendrogramma da restituire
     * @return il cluster set al livello level di tree
     */
    ClusterSet getClusterSet(int level) {
        return tree.get(level);
    }

    /**
     * Metodo getDepth
     * Restituisce la profondità del dendrogramma
     *
     * @return la profondità del dendrogramma
     */
    int getDepth() {
        return tree.size();
    }

    /**
     * Metodo toString
     * Restituisce una rappresentazione testuale del dendrogramma
     *
     * @return una rappresentazione testuale del dendrogramma
     */
    public String toString() {
        StringBuilder v = new StringBuilder();
        for (int i = 0; i < tree.size(); i++)
            v.append("level").append(i).append(":\n").append(tree.get(i)).append("\n");
        return v.toString();
    }

    /**
     * Metodo toString
     * Restituisce una rappresentazione testuale del dendrogramma
     *
     * @param data dataset di esempi
     * @return una rappresentazione testuale del dendrogramma
     */
    public String toString(Data data) {
        StringBuilder v = new StringBuilder();
        for (int i = 0; i < tree.size(); i++)
            v.append("level").append(i).append(":\n").append(tree.get(i).toString(data)).append("\n");
        return v.toString();
    }
}
