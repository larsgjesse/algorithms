import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	// Inner class for each deque element. Space requirement: 16 + 8 bytes JVM overhead
	// for a non-static inner class. 24 bytes for member fields. Total 48 bytes, assuming 64-bit architecture.
	class Node {
		public Node next;
		public Node prev;
		public Item item;
		public Node(Item item) {
			this.item = item;
		}
	}

	class DequeIterator implements Iterator<Item> {
		Node position;

		@Override
		public boolean hasNext() {
			return position != null;
		}

		@Override
		public Item next() {
			if (position == null) {
				throw new NoSuchElementException();
			}
			Item result = position.item;
			position = position.next;
			return result;
		}

		public DequeIterator() {
			position = first;
		}
	}

	private Node first;
	private Node last;
	private int size;

	// construct an empty deque
    public Deque() {
    	
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
    		throw new IllegalArgumentException();
    	}
    	Node oldfirst = first;
    	first = new Node(item);
    	if (oldfirst != null) {
        	first.next = oldfirst;
    		oldfirst.prev = first;
    	} else {
    		// Deque was empty
    		last = first;
    	}
    	size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
    	if (item == null) {
    		throw new IllegalArgumentException();
    	}
    	Node oldlast = last;
    	last = new Node(item);
    	if (oldlast != null) {
        	last.prev = oldlast;
    		oldlast.next = last;
    	} else {
    		// Deque was empty
    		first = last;
    	}
    	size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
    	if (first == null) {
    		throw new NoSuchElementException();
    	}
    	Item result = first.item;
    	first = first.next;
    	if (first != null) {
    		// More elements left
    		first.prev = null;
    	} else {
    		// Last element removed
    		last = null;
    	}
    	size -= 1;
    	return result;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if (last == null) {
    		throw new NoSuchElementException();
    	}
    	Item result = last.item;
    	last = last.prev;
    	if (last != null) {
    		// More elements left
    		last.next = null;
    	} else {
    		// Last element removed
    		first = null;
    	}
    	size -= 1;
    	return result;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
    	return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Intentionally empty - tests are JUnit tests in a separate class
    }

}