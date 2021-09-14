import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private int minMoves = -1;
    private SearchNode goal;
    private Stack<Board> result;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board is invalid.");
        }
        SearchNode initialSN = new SearchNode(initial, null, 0);
        SearchNode twinSN = new SearchNode(initial.twin(), null, 0);
        MinPQ<SearchNode> PQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
        PQ.insert(initialSN);
        twinPQ.insert(twinSN);

        // SearchNode curr, currTwin;
        while (!PQ.min().board.isGoal() && !twinPQ.min().board.isGoal()) {
            SearchNode curr = PQ.delMin();
            // Iterable<Board> neighbors = curr.board.neighbors();
            for (Board neighbor : curr.board.neighbors()) {
                if (curr.prev == null) {
                    PQ.insert(new SearchNode(neighbor, curr, curr.moves + 1));
                } else if (!neighbor.equals(curr.prev.board)) {
                    PQ.insert(new SearchNode(neighbor, curr, curr.moves + 1));
                }
            }

            SearchNode currTwin = twinPQ.delMin();
            for (Board neighbor : currTwin.board.neighbors()) {
                if (currTwin.prev == null) {
                    twinPQ.insert(new SearchNode(neighbor, currTwin, currTwin.moves + 1));
                } else if (!neighbor.equals(currTwin.prev.board)) {
                    twinPQ.insert(new SearchNode(neighbor, currTwin, currTwin.moves + 1));
                }
            }
        }

        if (PQ.min().board.isGoal()) {
            minMoves = PQ.min().moves;
            goal = PQ.delMin();
        }
    }

    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;  // The board representing current SearchNode
        private final int moves;  // The number of moves made to reach this SearchNode
        private final SearchNode prev;
        private int manhattanPriority = -1;

        public SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            manhattanPriority = board.manhattan() + moves;
        }

        public int getManhattanPriority() {
            if (manhattanPriority == -1) {
                manhattanPriority = board.manhattan() + moves;
            }
            return manhattanPriority;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.getManhattanPriority() - that.getManhattanPriority();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return minMoves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return minMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        result = new Stack<Board>();
        SearchNode resultNode = goal;
        while (resultNode != null) {
            result.push(resultNode.board);
            resultNode = resultNode.prev;
        }

        return result;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
