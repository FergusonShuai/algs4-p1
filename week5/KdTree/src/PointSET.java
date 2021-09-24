import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<Point2D>();

    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Trying to insert null into the set.");
        }
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Trying to insert null into the set.");
        }
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            return null;
        }
        TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                rangeSet.add(p);
            }
        }
        return rangeSet;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (set == null) {
            return null;
        }
        if (p == null) {
            throw new IllegalArgumentException("Trying to insert null into the set.");
        }
        double minDist = 2.0;
        Point2D nearestP = null;
        for (Point2D point : set) {
            if (point.distanceSquaredTo(p) < minDist) {
                minDist = point.distanceSquaredTo(p);
                nearestP = point;
            }
        }
        return nearestP;
    }

    public static void main(String[] args) {
        Point2D p0 = new Point2D(0.0, 0.0);
        Point2D p1 = new Point2D(0.1, 0.1);
        Point2D p2 = new Point2D(0.2, 0.2);
        Point2D p28 = new Point2D(0.2, 0.8);
        Point2D p8 = new Point2D(0.8, 0.8);
        Point2D p82 = new Point2D(0.8, 0.2);
        Point2D p55 = new Point2D(0.5, 0.5);

        PointSET pSET = new PointSET();
        pSET.insert(p2);
        pSET.insert(p28);
        pSET.insert(p8);
        pSET.insert(p82);
        pSET.insert(p55);
        pSET.insert(p1);

        StdOut.println("Nearest point to (0.07, 0.07) is " + pSET.nearest(new Point2D(0.07, 0.07)).toString());


    }
}
