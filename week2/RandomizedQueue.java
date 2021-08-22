import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // In order to return a random item at constant time, need to implement this using resizing-array.

    private int N;
    private Item[] items;
    private Item last;

    // construct an empty randomized queue
    public RandomizedQueue() {
        N = 0;
        items = (Item[]) (new Object[1]);
        // last = null;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        // rq.dequeue();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        StdOut.println("Size of the queue: " + rq.size());
        StdOut.println("Dequed item: " + rq.dequeue());
        StdOut.println("Dequed item: " + rq.dequeue());
        StdOut.println("Size of the queue: " + rq.size());
        StdOut.println("Sampled item: " + rq.sample());
        StdOut.println("Size of the queue: " + rq.size());

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (N == items.length) {  // need to grow the array
            resize(items.length * 2);
        }
        items[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) {
            throw new java.util.NoSuchElementException();
        }
        int randomNum = StdRandom.uniform(N);
        Item itemDequeued = items[randomNum];
//        No need to do this, as you don't need to maintain the order in array. It's all random.
//        for (randomNum; randomNum < N - 1; randomNum++) {
//            items[randomNum] = items[randomNum + 1];
//        }
        if (randomNum == N - 1) {
            items[randomNum] = null;
        } else {
            items[randomNum] = items[N - 1];
            items[N - 1] = null;
        }
        N--;
        if (N < items.length / 4 && items.length > 0) {
            resize(items.length / 2);
        }
        return itemDequeued;
    }

    // Helper function to resize array.
    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            newArray[i] = items[i];
        }
        items = newArray;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (N == 0) {
            throw new java.util.NoSuchElementException();
        }
        int randomNum = StdRandom.uniform(N);
        Item sampled = items[randomNum];
        return sampled;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int[] a = new int[N];
        int index = 0;

        public RandomizedQueueIterator() {
            for (int i = 0; i < N; i++) {
                a[i] = i;
            }
            StdRandom.shuffle(a);
        }

        public boolean hasNext() {
            return index < N;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            } else {
                Item i = items[a[index]];
                index++;
                return i;
            }
        }
    }

}
