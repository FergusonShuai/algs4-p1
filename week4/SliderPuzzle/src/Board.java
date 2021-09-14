import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Stack;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] boardTile;
    private final int dimension;
    private int hammingNum = -1;  // Caching Hamming val and Manhattan val when constructing the board.
    private int manhattanNum = -1;
/*  // Spent a good amount of time to debug, only then realized that it is not a good idea to store row0 & col0
    // as global variables, as we will need to update them whenever boardTile changed.
    private int row0;
    private int col0;

 */

    public Board(int[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles.length != tiles[i].length) {
                throw new IllegalArgumentException("Input tile is not a n x n grid.");
            }
        }

        dimension = tiles.length;
        boardTile = new int[dimension][dimension];
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                boardTile[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    // Reference: https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/faq.php
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", boardTile[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingNum == -1) {
            hammingNum = 0;
            for (int row = 0; row < dimension; row++) {
                for (int col = 0; col < dimension; col++) {
                    int number = boardTile[row][col];
                    int correctNumber = (row) * dimension + col + 1;
                    if (number != correctNumber && number != 0) {  // it is to test (boardTile[row][col] == 0)
                        hammingNum++;
                    }
                }
            }
        }
        return hammingNum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanNum == -1) {
            manhattanNum = 0;
            for (int row = 0; row < dimension; row++) {
                for (int col = 0; col < dimension; col++) {
                    int number = boardTile[row][col];
                    int correctNumber = (row) * dimension + col + 1;
                    if (correctNumber != number && number != 0) {
                        int correctRow = (number - 1) / dimension;  // A smarter way to find correct rows and cols given the number.
                        int correctCol = (number - 1) % dimension;
                        manhattanNum = manhattanNum + Math.abs(row - correctRow) + Math.abs(col - correctCol);
                    }
                }
            }
        }
        return manhattanNum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {  // reference: https://algs4.cs.princeton.edu/12oop/Date.java.html
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.boardTile, that.boardTile);
    }

    // all neighboring boards
    // Using Stack to store all neighbors so that we can return Iterable<Board>
    public Iterable<Board> neighbors() {

        Stack<Board> s = new Stack<Board>();
        int[] blank = blankTile();
        int row0, col0;

        row0 = blank[0];
        col0 = blank[1];

        // A quite clever way to find out all the neighbors
        Board b;
        if (row0 != 0) {
            b = new Board(boardTile);
            b.exch(row0, col0, row0 - 1, col0);
            s.add(b);
        }
        if (row0 != dimension - 1) {
            b = new Board(boardTile);
            b.exch(row0, col0, row0 + 1, col0);
            s.add(b);
        }
        if (col0 != 0) {
            b = new Board(boardTile);
            b.exch(row0, col0, row0, col0 - 1);
            s.add(b);
        }
        if (col0 != dimension - 1) {
            b = new Board(boardTile);
            b.exch(row0, col0, row0, col0 + 1);
            s.add(b);
        }

        return s;
    }

    private int[] blankTile() {
        int[] result = new int[2];

        outer:
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (boardTile[row][col] == 0) {
                    result[0] = row;
                    result[1] = col;
                    break outer;
                }
            }
        }

        return result;

    }

    private void exch(int row1, int col1, int row2, int col2) {
        assert (row1 >= 0 && row1 < dimension && col1 >= 0 && col1 < dimension
                && row2 >= 0 && row2 < dimension && col2 >= 0 && col2 < dimension);
        int swap = boardTile[row1][col1];
        boardTile[row1][col1] = boardTile[row2][col2];
        boardTile[row2][col2] = swap;
    }

    // a board that is obtained by exchanging any pair of tiles
    // No need to all the twins. Only one of them would be enough.
    public Board twin() {
        Board t = new Board(boardTile);
        int[] blank = blankTile();
        int row0, col0;

        row0 = blank[0];
        col0 = blank[1];

        if (row0 == 0 && col0 == 0 || row0 == 0 && col0 == 1) {
            t.exch(1, 0, 1, 1);
        } else {
            t.exch(0, 0, 0, 1);
        }
        return t;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a1 = {{3, 1, 2}, {0, 5, 6}, {7, 8, 4}};
        Board b1 = new Board(a1);
        int[][] a2 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        Board b2 = new Board(a2);
//        StdOut.print(b1.toString());
        StdOut.println("b2: ");
        StdOut.print(b2.toString());
//        StdOut.print(b2.twin());

        StdOut.println("b2's neighbors: ");
        for (Board b : b2.neighbors()) {
            StdOut.println(b.toString());
        }

//        StdOut.println("b1 is equal to b2? " + b1.equals(b2));
//        StdOut.println("Hamming: " + b1.hamming());
//        StdOut.println("Manhattan distance: " + b1.manhattan());
//        StdOut.println("Is the goal? " + b1.isGoal());

//        int[] coor = new int[2];
//        coor = correctLocation(16, 32);
//        StdOut.println("row: " + coor[0]);
//        StdOut.println("row: " + coor[1]);
    }
}
