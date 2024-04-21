class Dendrogram {
    private ClusterSet tree[]; //modella il dendrogramma

    /*
     * Costruttore
     * @param depth profondità del dendrogramma
     */
    Dendrogram(int depth){
        tree=new ClusterSet[depth];
    }

    /*
     * Inserisce il cluster set c al livello level di tree
     * @param level livello del dendrogramma in cui inserire il cluster set
     */
    void setClusterSet(ClusterSet c, int level){
        tree[level]=c;
    }

    /*
     * Restituisce il cluster set al livello level di tree
     * @param level livello del dendrogramma da restituire
     * @return il cluster set al livello level di tree
     */
    ClusterSet getClusterSet(int level) {
        return tree[level];
    }

    /*
     * Restituisce la profondità del dendrogramma
     * @return la profondità del dendrogramma
     */
    int getDepth() {
        return tree.length;
    }

    /*
     * Restituisce una rappresentazione testuale del dendrogramma
     * @return una rappresentazione testuale del dendrogramma
     */
    public String toString(){
        String v="";
        for (int i=0;i<tree.length;i++)
            v+=("level"+i+":\n"+tree[i]+ "\n");
        return v;
    }

    /*
     * Restituisce una rappresentazione testuale del dendrogramma
     * @param data dataset di esempi
     * @return una rappresentazione testuale del dendrogramma
     */
    String toString(Data data){
        String v="";
        for (int i=0;i<tree.length;i++)
            v+=("level"+i+":\n"+tree[i].toString(data)+"\n");
        return v;
    }

}
