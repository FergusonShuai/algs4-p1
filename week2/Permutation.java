import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        // Use StdIn.readString() to read a sequence of strings from standard input,
        // also readin a k, and print k of them.
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }

    }
}
