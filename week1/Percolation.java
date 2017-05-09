import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int side; // side = n for n-by-n grid
    private boolean[][] openedSites; // track open sites
    private int openSites = 0; // track count of open sites
    private int top = 0; // track top virtual site
    private int bottom; // track bottom virtual site
    private WeightedQuickUnionUF unionFindA; // data structure
    private WeightedQuickUnionUF unionFindB; // data structure
    
    public Percolation(int n) { // create n-by-n grid with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        openedSites = new boolean[n][n];
        unionFindA = new WeightedQuickUnionUF(n * n + 2);
        unionFindB = new WeightedQuickUnionUF(n * n + 2);
        side = n;
        bottom = n * n + 1;
    }
    
    private int getQFIndex(int row, int col) { // converts row, col to QF index
        return side * (row - 1) + col;
    }
    
    private void isValidIndex(int i) { // checks if index is in bounds
        if (i < 1 || i > side) {
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
    }
    
    public void open(int row, int col) { // open site (row, col) if it is not open already
        isValidIndex(row);
        isValidIndex(col);
        if (openedSites[row-1][col-1]) {
            return;
        }
        int p = getQFIndex(row, col);
        openedSites[row-1][col-1] = true;
        openSites++;
        // connect first row sites sites to top
        if (row == 1) {
            unionFindA.union(p, top);
            unionFindB.union(p, top);
        }
        // connect bottom row sites sites to top
        if (row == side) {
            unionFindA.union(p, bottom);
        }
        // check up site valid and open
        if (row > 1 && isOpen(row - 1, col)) {
            unionFindA.union(p, getQFIndex(row - 1, col));
            unionFindB.union(p, getQFIndex(row - 1, col));
        }
        // check down site valid and open
        if (row < side && isOpen(row + 1, col)) {
            unionFindA.union(p, getQFIndex(row + 1, col));
            unionFindB.union(p, getQFIndex(row + 1, col));
        }
        // check right site valid and open
        if (col < side && isOpen(row, col + 1)) {
            unionFindA.union(p, getQFIndex(row, col + 1));
            unionFindB.union(p, getQFIndex(row, col + 1));
        }
        // check left site valid and open
        if (col > 1 && isOpen(row, col - 1)) {
            unionFindA.union(p, getQFIndex(row, col - 1));
            unionFindB.union(p, getQFIndex(row, col - 1));
        }
    }
    public boolean isOpen(int row, int col) { // is site (row, col) open?
        isValidIndex(row);
        isValidIndex(col);
        return openedSites[row-1][col-1];
    }
    public boolean isFull(int row, int col) { // is site (row, col) full?
        isValidIndex(row);
        isValidIndex(col);
        int p = getQFIndex(row, col);
        return unionFindB.connected(top, p);
    }
    public int numberOfOpenSites() { // number of open sites
        return openSites;
    }
    public boolean percolates() { // does the system percolate?
        return unionFindA.connected(top, bottom);
    }
}