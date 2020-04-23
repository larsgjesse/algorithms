import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] queue = (Item[]) new Object[1];
	private int size;

	private class RandomizedIterator implements Iterator<Item> {

		final private int[] randomizedIndices = new int[size];
		private int position = 0;

		public RandomizedIterator() {
			// Create randomly permuted sequence of indices between 0 and size-1
			for (int i = 0; i < size; i++) {
				randomizedIndices[i] = i;
			}
			for (int i = 1; i < size; i++) {
				int r = StdRandom.uniform(i + 1);
				int swap = randomizedIndices[r];
				randomizedIndices[r] = randomizedIndices[i];
				randomizedIndices[i] = swap;
			}
		}

		@Override
		public boolean hasNext() {
			return position < randomizedIndices.length;
		}

		@Override
		public Item next() {
			if (position >= randomizedIndices.length) {
				throw new NoSuchElementException();
			}
			return queue[randomizedIndices[position++]];
		}

	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// return the number of items on the randomized queue
	public int size() {
		return size;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		if (size == queue.length) {
			resize(queue.length * 2);
		}
		queue[size++] = item;
	}

	// remove and return a random item
	public Item dequeue() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		int i = StdRandom.uniform(size);
		Item result = queue[i];
		queue[i] = queue[size - 1];
		queue[--size] = null;
		if (size > 0 && size <= queue.length / 4) {
			resize(queue.length / 2);
		}
		return result;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return queue[StdRandom.uniform(size)];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedIterator();
	}

	// unit testing (required)
	public static void main(String[] args) {
		// Intentionally empty - tests are JUnit tests in a separate class
	}

	private void resize(int newSize) {
		Item[] newQueue = (Item[]) new Object[newSize];
		System.arraycopy(queue, 0, newQueue, 0, size);
		queue = newQueue;
	}
}