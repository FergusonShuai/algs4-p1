import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> LS = new ArrayList<LineSegment>();
    // int segmentsCount = 0;

    public BruteCollinearPoints(Point[] points) {  // finds all line segments containing 4 points

        validateInput(points);

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                double slope_ij = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    double slope_ik = points[i].slopeTo(points[k]);
                    if (slope_ij != slope_ik) {
                        continue;
                    }
                    for (int l = k + 1; l < points.length; l++) {
                        double slope_il = points[i].slopeTo(points[l]);
                        if (slope_ik == slope_il) {
                            Point[] sub = new Point[4];
                            sub[0] = points[i];
                            sub[1] = points[j];
                            sub[2] = points[k];
                            sub[3] = points[l];
                            Arrays.sort(sub);
                            LineSegment lsCurrent = new LineSegment(sub[0], sub[3]);
                            LS.add(lsCurrent);
                        }
                    }
                }
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
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicates detected in BruteCollinearPoints' input");
            }
        }
    }

    public int numberOfSegments() {  // the number of line segments
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
        BruteCollinearPoints BCP = new BruteCollinearPoints(points);
        System.out.println(BCP.numberOfSegments());
        LineSegment[] LSA = BCP.segments();
        */

        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-32768, 32768);
        StdDraw.setYscale(-32768, 32768);
        StdDraw.setPenRadius(0.005);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        Stopwatch watch = new Stopwatch();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println("time lapse: " + watch.elapsedTime());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdOut.println("Number of segments: " + collinear.numberOfSegments());
        StdDraw.show();
    }

}


