package assignment.mvc.model;

public class ModelConfiguration {
    public final int n;
    public final int nl;
    public final int maxl;

    public ModelConfiguration(int n, int nOfIntervals, int maxl) {
        this.n = n;
        this.nl = nOfIntervals;
        this.maxl = maxl;
    }
}
