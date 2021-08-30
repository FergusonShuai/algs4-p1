import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {

    ArrayList<LineSegment> LS = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {  // finds all line segments containing 4 or more points
        validateInput(points);
        int N = points.length;

        Arrays.sort(points);
        // Point[] pointsCopied = points.clone();

        for (int i = 0; i < N - 3; i++) {
            Point[] pointsCopied = Arrays.copyOfRange(points, i, N);
            Point anchorP = pointsCopied[0];
            Arrays.sort(pointsCopied, anchorP.slopeOrder());

//            System.out.println("pointsCopied: ");
//            for (Point p : pointsCopied) {
//                System.out.println(p.toString());
//            }
//            System.out.println();


            double baseSlope = 0.0;
            int pointerSlow = 1;
            int pointerFast = 1;
            for (int j = 1; j < N - i; j++) {
                double currentSlope = anchorP.slopeTo(pointsCopied[j]);
                if (currentSlope == baseSlope) {
                    pointerFast++;
                } else {
                    if (pointerFast - pointerSlow >= 2) {
                        LineSegment ls = new LineSegment(anchorP, pointsCopied[pointerFast]);
                        LS.add(ls);
                    }
                    baseSlope = currentSlope;
                    pointerSlow = j;
                    pointerFast = j;
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

    public int numberOfSegments() {   // the number of line segments
        return LS.size();
    }

    public LineSegment[] segments() {  // the line segments
        return LS.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {
        Point p0 = new Point(1, 1);
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 3);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(4, 4);
        Point p6 = new Point(5, 5);
        Point p7 = new Point(1, 0);
//        Point p7 = new Point(4, 2);

//        Point p8 = new Point(2, 0);
//        Point p9 = new Point(2, 3);
//        Point p10 = new Point(3, 1);
//        Point p11 = new Point(-2, 1);

        Point[] points = new Point[]{p0, p1, p2, p3, p4, p5, p6, p7};
        FastCollinearPoints FCP = new FastCollinearPoints(points);
        System.out.println("Number of line segments: " + FCP.numberOfSegments());

        LineSegment[] LSA = FCP.segments();

//        for (LineSegment ls : LSA) {
//            System.out.println(ls.toString());
//        }
//        System.out.println();

    }

}
