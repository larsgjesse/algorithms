
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double mean;
	private final double stddev;
	private final double confInterval;

    // perform independent trials on an n-by-n grid
    public PercolationStats(final int n, final int trials) {
    	if (n < 1 || trials < 1) {
    		throw new IllegalArgumentException();
    	}
		int estimates[] = new int[trials];
		final int size = n*n;
		for (int i = 0; i < trials; i++ ) {
	    	final Percolation percolation = new Percolation(n);
	    	do {
	    		final int cell = StdRandom.uniform(size);
	    		percolation.open(cell/n+1, cell%n+1);
	    	} while (!percolation.percolates());
	    	estimates[i] = percolation.numberOfOpenSites();
		}
		mean = StdStats.mean(estimates) / size;
		stddev = StdStats.stddev(estimates) / size;
		confInterval = 1.96*stddev/Math.sqrt(trials);
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
    	return mean - confInterval;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean + confInterval;
    }

    // test client (see below)
    public static void main(String[] args) {
    	if (args.length != 2) {
    		throw new IllegalArgumentException();
    	}
    	final int n = Integer.parseInt(args[0]);
    	final int trials = Integer.parseInt(args[1]);
    	PercolationStats ps = new PercolationStats(n, trials);
    	StdOut.println("mean                    = " + ps.mean());
    	StdOut.println("stddev                  = " + ps.stddev());
    	StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
