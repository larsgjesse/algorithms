import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final WeightedQuickUnionUF wquuf;
	private int top;
	private int bottom;
	private boolean siteOpen[];
	private int openSites;
	private int n;

	// creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if (n < 1) {
    		throw new IllegalArgumentException();
    	}
    	top = n*n;
    	bottom=top+1;
    	this.n = n;
    	wquuf = new WeightedQuickUnionUF(n*n+2);
    	siteOpen = new boolean[n*n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	validate(row, col);
    	int index = toIndex(row, col);
    	if (siteOpen[index]) return;
    	openSites += 1;
    	siteOpen[index] = true;
    	// Connect to neigbor above
    	if (row > 1) {
    		if (siteOpen[index-n]) {
    			wquuf.union(index, index-n);
    		}
    	} else {
    		wquuf.union(index, top);
    	}
    	// Connect to neighbor below
    	if (row < n) {
    		if (siteOpen[index+n]) {
    			wquuf.union(index, index+n);
    		}
    	} else {
    		wquuf.union(index, bottom);
    	}
    	// Connect to neighbor on the left
    	if (col > 1 && siteOpen[index-1]) {
    		wquuf.union(index, index-1);
    	}
    	// Connect to neighbor on the right
    	if (col < n && siteOpen[index+1]) {
    		wquuf.union(index, index+1);
    	}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	validate(row, col);
    	return siteOpen[toIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	validate(row, col);
    	return wquuf.find(toIndex(row, col)) == wquuf.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
    	return wquuf.find(top) == wquuf.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    	
    }

	private void validate(int row, int col) {
		if (row < 1 || row > n || col < 1 || col > n) {
			throw new IllegalArgumentException();
		}
	}
	private int toIndex(int row, int col) {
		return (row-1)*n + col-1;
	}
}
