import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
   private int t; // number of trials
   private double[] values; // array of percolation values
   
   public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
       isValidArg(n);
       isValidArg(trials);
       t = trials;
       values = new double[trials];
       for (int round = 0; round < trials; round++) {
           Percolation p = new Percolation(n);
           int openSites = 0;
           while (!p.percolates()) {
               int i = StdRandom.uniform(1, n + 1);
               int j = StdRandom.uniform(1, n + 1);
               if (!p.isOpen(i, j)) {
                   p.open(i, j);
                   openSites++;
               }
           }
           values[round] = (double) openSites / (double) (n * n);
       }
   }
   
   private void isValidArg(int n) {
       if (n <= 0) {
            throw new IllegalArgumentException("Arg must be greater than 0");
       }
   }
   
   public double mean() { // sample mean of percolation threshold
       return StdStats.mean(values);
   }
   
   public double stddev() { // sample standard deviation of percolation threshold
       return StdStats.stddev(values);
   }
   public double confidenceLo() { // low  endpoint of 95% confidence interval
       return mean() - (1.96 * stddev() / Math.sqrt(t));
   }
   
   public double confidenceHi() { // high endpoint of 95% confidence interval
       return mean() + (1.96 * stddev() / Math.sqrt(t));
   }

   public static void main(String[] args) { // test client (described below)
       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);
       PercolationStats ps = new PercolationStats(n, t);
       String conf = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
       StdOut.println("mean                    = " + ps.mean());
       StdOut.println("stdev                   = " + ps.stddev());
       StdOut.println("95% confidence interval = " + conf);
   }
}
