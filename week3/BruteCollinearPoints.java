import java.util.Arrays;
import java.util.ArrayList;

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
//        Point p0 = new Point(1, 1);
//        Point p1 = new Point(-1, -1);
//        Point p2 = new Point(1, 2);
//        Point p3 = new Point(2, 1);
//        Point p4 = new Point(2, 2);
//        Point p5 = new Point(-2, -1);
//        Point p6 = new Point(0, 0);
//        Point p7 = new Point(4, 2);
//        Point p8 = new Point(2, 0);
//        Point p9 = new Point(2, 3);
//        Point p10 = new Point(3, 1);
//        Point p11 = new Point(-2, 1);

        Point p0 = new Point(1, 1);
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 3);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(4, 4);
        Point p6 = new Point(5, 5);
        Point p7 = new Point(1, 0);


        Point[] points = new Point[]{p0, p1, p2, p3, p4, p5, p7};
        BruteCollinearPoints BCP = new BruteCollinearPoints(points);
        System.out.println(BCP.numberOfSegments());
        LineSegment[] LSA = BCP.segments();

        for (LineSegment ls : LSA) {
            System.out.println(ls.toString());
        }

    }

}


