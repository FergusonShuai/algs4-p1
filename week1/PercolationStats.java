import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private int trials;
    private double[] x;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException(Integer.toString(n));
        }

        if (trials <= 0) {
            throw new IllegalArgumentException(Integer.toString(trials));
        }

        this.trials = trials;
        x = new double[trials];
        // Then randomly open a site, until p percolates.
        for (int i = 0; i < trials; i++) {
            x[i] = singleRun(n);
        }

        mean = StdStats.mean(x);
        stddev = StdStats.stddev(x);

    }

    private double singleRun(int n) {
        int numberOfOpenSites = 0;
        Percolation p = new Percolation(n);
        while (!p.percolates()) {  // if the system does not percolate, randomly open on site.
            p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
        }
        numberOfOpenSites = p.numberOfOpenSites();
        return numberOfOpenSites / (double) (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean - 1.96 * stddev / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean + 1.96 * stddev / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int i = StdIn.readInt();
        Stopwatch watch = new Stopwatch();

        PercolationStats p = new PercolationStats(n, i);
        System.out.println("time lapse: " + watch.elapsedTime());
        System.out.println("mean: " + p.mean());
        System.out.println("stdev: " + p.stddev());
        System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");

    }

}
