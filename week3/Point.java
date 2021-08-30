/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (x == that.x) {
            if (y == that.y) {
                return Double.NEGATIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        } else {
            if (y == that.y) {
                return +0.0;
            } else {
                return (double) (that.y - y) / (that.x - x);
            }
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (y < that.y) {
            return -1;
        }
        if (y > that.y) {
            return 1;
        }
        if (x < that.x) {
            return -1;
        }
        if (x > that.x) {
            return 1;
        }
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {
        @Override
        public int compare(Point p, Point q) {
            double slopeP = slopeTo(p);
            double slopeQ = slopeTo(q);

            if (slopeP < slopeQ) return -1;
            else if (slopeP == slopeQ) return 0;
            else return 1;
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p0 = new Point(1, 0);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 2);
//        Point p3 = new Point(2, 1);
//        Point p4 = new Point(2, 2);
//        Point p5 = new Point(-2, -1);
//        Point p6 = new Point(0, 0);
//        Point p7 = new Point(4, 2);
//        Point p8 = new Point(0, 0);

//        System.out.println("p0 to p1 slope: " + p0.slopeTo(p1));
//        System.out.println("p0 to p2 slope: " + p0.slopeTo(p2));
//        System.out.println("p0 to p3 slope: " + p0.slopeTo(p3));
//        System.out.println("p0 to p4 slope: " + p0.slopeTo(p4));
//        System.out.println("p0 compareTo p1 " + p0.compareTo(p1));
//        System.out.println("p0 compareTo p2 " + p0.compareTo(p2));
//        System.out.println("p0 compareTo p5 " + p0.compareTo(p5));
//        Comparator<Point> c6 = p6.slopeOrder();
//
//        System.out.println(p0.toString());
//        System.out.println("Comparator c604: " + c6.compare(p0, p4));
//        System.out.println("Comparator c615: " + c6.compare(p1, p5));
        Point[] points = new Point[]{p0, p1, p2};
        for (Point p : points) {
            System.out.print(p + " ");
        }
        double slope01 = p0.slopeTo(p1);
        double slope02 = p0.slopeTo(p2);

        System.out.println("Slope p01 == p02? " + (slope01 == slope02));


    }
}
