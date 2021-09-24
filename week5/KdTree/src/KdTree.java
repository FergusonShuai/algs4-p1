import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node, the rectangle encloses all of the points in its subtree.
        private final boolean isVertical;  // if this node a vertical node or a horizontal one
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, boolean vertical, RectHV rect) {
            this.p = p;
            this.rect = rect;
            isVertical = vertical;
        }

        public int compareTo(Point2D that) {
            int result;
            if (this.p.equals(that)) {
                return 0;
            }
            if (isVertical) {  // compare using x-axis
                return p.x() <= that.x() ? -1 : 1;
            } else {
                return p.y() <= that.y() ? -1 : 1;
            }
        }

    }

    public KdTree() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Add the point to the tree if it is not already in there.
     *
     * @param p Point2D to be added
     * @throws IllegalArgumentException if the input argument is null.
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Trying to insert null into the set.");
        }

        if (size == 0) {
            RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            root = new Node(p, true, rect);
            size++;
        } else {  // recursively call a helper function to find the correct place to insert.
            int cmp = root.compareTo(p);
            if (cmp > 0) {
                root.lb = insert(p, root.lb, root, cmp);
            } else if (cmp < 0) {
                root.rt = insert(p, root.rt, root, cmp);
            }
        }
    }

    /**
     * Define a helper function to recursively look for the right place for the new node.
     *
     * @param direction is a comparator passed down from previous iteration: e.g., if direction > 0 then curr is the left node of its parent.
     */
    private Node insert(Point2D p, Node curr, Node parent, int direction) {

        if (curr == null) {  // stopping criteria: when curr is null (found an empty place).
            RectHV rect;
            if (parent.isVertical) {
                if (direction > 0) {  // new point p will be on the left subtree of parent node.
                    rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(), parent.rect.ymax());
                } else {  // new point p will be on the right subtree of parent node.
                    rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
                }
            } else {  // parent is a horizontal node.
                if (direction > 0) {  // new point p will on the left subtree (bottom) of parent node.
                    rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.p.y());
                } else {  // top
                    rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
                }
            }
            size++;
            return new Node(p, !parent.isVertical, rect);
        } else {
            int cmp = curr.compareTo(p);
            if (cmp > 0) {
                curr.lb = insert(p, curr.lb, curr, cmp);
            } else if (cmp < 0) {
                curr.rt = insert(p, curr.rt, curr, cmp);
            }
            return curr;
        }
    }

    /**
     * Check if the tree contains Point2D p
     *
     * @param p The Point2D to be checked
     * @return a boolean whether p is cantained or not
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point can't be null");
        }
        return contains(p, root);
    }

    private boolean contains(Point2D p, Node node) {
        if (node == null) {
            return false;
        }

        int cmp = node.compareTo(p);
        if (cmp > 0) {
            return contains(p, node.lb);
        }
        if (cmp < 0) {
            return contains(p, node.rt);
        }
        return true;
    }

    /**
     * Draw all of the points to standard draw in black and the subdivisions in red (for vertical splits) and blue (for horizontal splits).
     */
    public void draw() {
        // let's do a preorder traversal
        if (size == 0) return;
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        node.p.draw();

        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.005);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.005);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }

        draw(node.lb);
        draw(node.rt);

    }

    /**
     * return a Queue<Point2D> containing all the points within the rect.
     * Note that can't use TreeSet<E> here
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("input to range() function can't be null.");
        }
        if (size == 0) {
            return null;
        }
        Queue<Point2D> subset = new Queue<Point2D>();

        range(rect, subset, root);

        return subset;

    }

    private void range(RectHV rect, Queue<Point2D> subset, Node node) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.p)) {
            subset.enqueue(node.p);
        }
        if (node.lb != null && rect.intersects(node.lb.rect)) {
            range(rect, subset, node.lb);
        }
        if (node.rt != null && rect.intersects(node.rt.rect)) {
            range(rect, subset, node.rt);
        }
    }

    /**
     * A method returning the nearest point among all the points in the tree, to the input point p.
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (size == 0) {
            return null;
        }
        if (p == null) {
            throw new IllegalArgumentException("Trying to insert null into the set.");
        }

        return nearest(p, root, root).p;

    }

    private Node nearest(Point2D p, Node node, Node nearestNode) {
        if (node == null) {
            return nearestNode;
        }

        // updating nearestNode.
        if (node.p.distanceSquaredTo(p) < nearestNode.p.distanceSquaredTo(p)) {
            nearestNode = node;
        }

        // Need to determine which subtree to go with (left or right).
        // Remember we defined a handy-dandy compareTo() function which will help us here.
        int cmp = node.compareTo(p);
        if (cmp > 0) { // point p is smaller than current node, need to go to left subtree first.
            nearestNode = nearest(p, node.lb, nearestNode);
            if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < nearestNode.p.distanceSquaredTo(p)) {  // The only condition that we need to explore the other half of the tree.
                nearestNode = nearest(p, node.rt, nearestNode);
            }
        } else if (cmp < 0) {  // point p is larger than current node, need to go to right subtree first.
            nearestNode = nearest(p, node.rt, nearestNode);
            if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < nearestNode.p.distanceSquaredTo(p)) {  // The only condition that we need to explore the other half of the tree.
                nearestNode = nearest(p, node.lb, nearestNode);
            }
        }

        return nearestNode;
    }

    public static void main(String[] args) {
        Point2D p0 = new Point2D(0.0, 0.0);
        Point2D p1 = new Point2D(0.1, 0.1);
        Point2D p2 = new Point2D(0.2, 0.2);
        Point2D p28 = new Point2D(0.2, 0.8);
        Point2D p8 = new Point2D(0.8, 0.8);
        Point2D p82 = new Point2D(0.8, 0.2);
        Point2D p55 = new Point2D(0.5, 0.5);

        KdTree tree1 = new KdTree();
        // tree1.insert(p0);
        tree1.insert(p2);
        tree1.insert(p28);
        tree1.insert(p8);
        tree1.insert(p82);
        tree1.insert(p55);
        tree1.insert(p1);

        tree1.draw();

        StdOut.println("Nearest point to (0.81, 0.82) is " + tree1.nearest(new Point2D(0.81, 0.82)).toString());

    }

}
