import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first = null;
    private Node last = null;

    private class Node {  // use doublly linked list to make removeLast() operation easier.
        Item item;
        Node next;
        Node prev;

        public Node(Item i) {
            item = i;
            next = null;
            prev = null;
        }

        public Node(Item i, Node n) {
            item = i;
            next = n;
            prev = null;
        }
    }

    // construct an empty deque
    public Deque() {
        size = 0;

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't call addFirst() on a null item.");
        }
        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        if (size == 0) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        size++;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't call addFirst() on a null item.");
        }
        Node oldLast = last;
        last = new Node(item);
        last.prev = oldLast;
        if (size == 0) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Node oldSecond = first.next;
        Item removedItem = first.item;
        first = oldSecond;
        size--;
        if (size == 0) {
            last = null;
        } else {
            first.prev = null;
        }

        return removedItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        Node oldSecondToLast = last.prev;
        Item removedItem = last.item;
        last.prev = null;
        last = oldSecondToLast;
        size--;
        if (size == 0) {
            first = null;
        } else {
            last.next = null;
        }

        return removedItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            } else {
                Item i = current.item;
                current = current.next;
                return i;
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(1);
        System.out.println("The size: " + d.size());
        d.addFirst(2);
        d.addFirst(3);
        d.addFirst(4);
        d.addLast(5);
        d.addLast(6);
        for (Integer n : d) {
            System.out.println(n);
        }
        System.out.println("The size: " + d.size());

        System.out.println("Remove last item: " + (d.removeLast()) + " and size: " + d.size());
        for (Integer n : d) {
            System.out.println(n);
        }
        System.out.println("The size: " + d.size());
        d.addLast(7);
        for (Integer n : d) {
            System.out.println(n);
        }
        System.out.println("The size: " + d.size());
        
    }

}
