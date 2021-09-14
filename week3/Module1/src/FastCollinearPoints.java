import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> LS = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {  // finds all line segments containing 4 or more points
        validateInput(points);
        // Create a copy of points to avoid mutating original input.
        Point[] pointsCopy = points.clone();

        Arrays.sort(pointsCopy);

        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicates detected in BruteCollinearPoints' input");
            }
        }

        int N = pointsCopy.length;
        if (N <= 3) {
            return;
        }

        double preSlope = pointsCopy[0].slopeTo(pointsCopy[1]);

        for (int i = 0; i < N - 3; i++) {
            Point[] pointsSubset = Arrays.copyOfRange(pointsCopy, i, N);
            Point anchorP = pointsSubset[0];
            Arrays.sort(pointsSubset, anchorP.slopeOrder());

            int pointerSlow = 1;
            int pointerFast = 1;
            double baseSlope = anchorP.slopeTo(pointsSubset[pointerSlow]);

            for (int j = 2; j < N - i; j++) {
                double currentSlope = anchorP.slopeTo(pointsSubset[j]);
                if (currentSlope == baseSlope) {
                    pointerFast++;
                } else {
                    if (pointerFast - pointerSlow >= 2) {
                        LineSegment ls = new LineSegment(anchorP, pointsSubset[pointerFast]);
                        LS.add(ls);
                    }
                    baseSlope = currentSlope;
                    pointerSlow = j;
                    pointerFast = j;
                }
            }
            if (pointerFast - pointerSlow >= 2) {
                LineSegment ls = new LineSegment(anchorP, pointsSubset[pointerFast]);
                LS.add(ls);

            }
        }

    }

    private void validateInput(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Input to BruteCollinearPoints constructor is invalid.");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Input to BruteCollinearPoints constructor is invalid.");
            }
        }

    }

    public int numberOfSegments() {   // the number of line segments
        return LS.size();
    }

    public LineSegment[] segments() {  // the line segments
        return LS.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {

        /*
        Point p0 = new Point(1, 1);
        Point p1 = new Point(-1, -1);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(2, 1);
        Point p4 = new Point(2, 2);
        Point p5 = new Point(-2, -1);
        Point p6 = new Point(0, 0);
        Point p7 = new Point(4, 2);
        Point p8 = new Point(2, 0);
        Point p9 = new Point(2, 3);
        Point p10 = new Point(3, 1);
        Point p11 = new Point(-2, 1);

        Point[] points = new Point[]{p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11};
        */
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-10, 32768);
        StdDraw.setYscale(-10, 32768);
        StdDraw.setPenRadius(0.005);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        Stopwatch watch = new Stopwatch();
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("time lapse: " + watch.elapsedTime());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("Number of segments: " + collinear.numberOfSegments());
        StdDraw.show();
    }

}
