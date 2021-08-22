import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] percolationState;
    private final int gridSize;
    private int openSitesNum;
    private final WeightedQuickUnionUF set;

    // creates n-by-n grid, with all sites initially blocked
    // if percolationState[i][j] == 0, then grid[i][j] is blocked.
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(Integer.toString(n));
        }

        // percolationState[i][j] indicates is site[i][j] open or not.
        percolationState = new int[n][n];
        gridSize = n;
        openSitesNum = 0;
        isPercolate = false;
        set = new WeightedQuickUnionUF(n * n + 2);  // 2 extra sites at the end, one for virtual top site and one for bottom site.
        // Need to connect top row to top site and bottom row to bottom site.
        for (int i = 0; i < n; i++) {
            set.union(i, n * n);
            set.union(n * n + 1, (n - 1) * n + i);
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // row and col should be integers fall in [1,n].
        if (row < 1 || row > gridSize) {
            throw new IllegalArgumentException(Integer.toString(row));
        }
        if (col < 1 || col > gridSize) {
            throw new IllegalArgumentException(Integer.toString(col));
        }

        if (percolationState[row - 1][col - 1] == 0) {
            percolationState[row - 1][col - 1] = 1;
            openSitesNum++;
        }

        // updating set
        if (row == 1) {  // first row, only need to connect bottom sites if they are open
            if (percolationState[row][col - 1] == 1) {  // bottom site
                set.union(col - 1, (row) * gridSize + (col - 1));
            }
        } else if (row == gridSize) {  // bottom row
            if (col == 1) {  // bottom left site, test above and right sites.
                if (percolationState[row - 2][col - 1] == 1) {  // above
                    set.union((row - 1) * gridSize + col - 1, (row - 2) * gridSize + col - 1);
                }
                if (percolationState[row - 1][col] == 1) {  // right
                    set.union((row - 1) * gridSize + col - 1, (row - 1) * gridSize + col);
                }
            } else if (col == gridSize) {  // bottom right site, test above and left sites.
                if (percolationState[row - 2][col - 1] == 1) {  // above
                    set.union((row - 1) * gridSize + col - 1, (row - 2) * gridSize + col - 1);
                }
                if (percolationState[row - 1][col - 2] == 1) {  // left
                    set.union((row - 1) * gridSize + col - 1, (row - 1) * gridSize + col - 2);
                }
            } else {  // all other sites on the bottom row need to test above, left and right
                if (percolationState[row - 2][col - 1] == 1) {  // above
                    set.union((row - 1) * gridSize + col - 1, (row - 2) * gridSize + col - 1);
                }
                if (percolationState[row - 1][col - 2] == 1) {  // left
                    set.union((row - 1) * gridSize + col - 1, (row - 1) * gridSize + col - 2);
                }
                if (percolationState[row - 1][col] == 1) {  // right
                    set.union((row - 1) * gridSize + col - 1, (row - 1) * gridSize + col);
                }
            }
        } else {  // rows in the middle
            if (col == 1) {  // left edge; test above, right and below
                if (percolationState[row - 2][col - 1] == 1) {  // above
                    set.union((row - 1) * gridSize + col - 1, (row - 2) * gridSize + col - 1);
                }
                if (percolationState[row - 1][col] == 1) {  // right
                    set.union((row - 1) * gridSize + col - 1, (row - 1) * gridSize + col);
                }
                if (percolationState[row][col - 1] == 1) {  // below
                    set.union((row - 1) * gridSize + col - 1, (row) * gridSize + col - 1);
                }
            } else if (col == gridSize) {  // right edge; test above, left and bottom
                if (percolationState[row - 2][col - 1] == 1) {  // above
                    set.union((row - 1) * gridSize + col - 1, (row - 2) * gridSize + col - 1);
                }
                if (percolationState[row - 1][col - 2] == 1) {  // left
                    set.union((row - 1) * gridSize + col - 1, (row - 1) * gridSize + col - 2);
                }
                if (percolationState[row][col - 1] == 1) {  // below
                    set.union((row - 1) * gridSize + col - 1, (row) * gridSize + col - 1);
                }
            } else {  // sites in the middle; test all 4
                if (percolationState[row - 2][col - 1] == 1) {  // above
                    set.union((row - 1) * gridSize + col - 1, (row - 2) * gridSize + col - 1);
                }
                if (percolationState[row - 1][col - 2] == 1) {  // left
                    set.union((row - 1) * gridSize + col - 1, (row - 1) * gridSize + col - 2);
                }
                if (percolationState[row - 1][col] == 1) {  // right
                    set.union((row - 1) * gridSize + col - 1, (row - 1) * gridSize + col);
                }
                if (percolationState[row][col - 1] == 1) {  // below
                    set.union((row - 1) * gridSize + col - 1, (row) * gridSize + col - 1);
                }
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > gridSize) {
            throw new IllegalArgumentException(Integer.toString(row));
        }
        if (col < 1 || col > gridSize) {
            throw new IllegalArgumentException(Integer.toString(col));
        }

        return (percolationState[row - 1][col - 1] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // row: [1, gridSize]; col: [1, gridSize]
        int topVirtualNode = gridSize * gridSize;

        if (row < 1 || row > gridSize) {
            throw new IllegalArgumentException(Integer.toString(row));
        }
        if (col < 1 || col > gridSize) {
            throw new IllegalArgumentException(Integer.toString(col));
        }
        if (!isOpen(row, col)) {
            return false;
        }

        return set.connected((row - 1) * gridSize + col - 1, topVirtualNode);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return set.connected(gridSize * gridSize, gridSize * gridSize + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 2);
        System.out.println("open site(1,2)");
        p.open(3, 2);
        System.out.println("open site(3,2)");
        p.open(3, 3);
        System.out.println("open site(3,3)");
        System.out.println("number of open sites: " + p.numberOfOpenSites());
        System.out.println("System percolates? " + p.percolates());

//        System.out.println("site[1][2] is full? " + p.isFull(1, 2));
//        System.out.println("site[2][2] is full? " + p.isFull(2, 2));
//        System.out.println("site[3][2] is full? " + p.isFull(3, 2));

        p.open(2, 2);
        System.out.println("open site(2,2)");
        System.out.println("number of open sites: " + p.numberOfOpenSites());
        System.out.println("System percolates? " + p.percolates());

//        System.out.println("site[2][2] is full? " + p.isFull(2, 2));
//        System.out.println("site[3][2] is full? " + p.isFull(3, 2));
//        System.out.println("site[3][3] is full? " + p.isFull(3, 3));
//        System.out.println("site[3][1] is full? " + p.isFull(3, 1));
//        System.out.println("site[2][3] is full? " + p.isFull(2, 3));


    }
}
