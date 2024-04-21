public class AverageLinkDistance implements ClusterDistance {
    public double distance(Cluster c1, Cluster c2, Data d) {
        double sum = 0.0;
        for (int i=0;i< c1.getSize();i++)
        {
            Example e1=d.getExample(c1.getElement(i));
            for(int j=0; j<c2.getSize();j++) {
                sum+=e1.distance(d.getExample(c2.getElement(j)));
            }
        }
        return sum / (c1.getSize() * c2.getSize());
    }
}
