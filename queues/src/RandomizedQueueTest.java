import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class RandomizedQueueTest {
	RandomizedQueue<Integer> queue = new RandomizedQueue<>();

	@Test
	public void enqueueDequeue() {
		assertTrue(queue.isEmpty());
		queue.enqueue(1);
		assertEquals(1, queue.size());
		assertFalse(queue.isEmpty());
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(5);
		assertEquals(4, queue.size());

		int sum = (int) queue.dequeue();
		sum += (int) queue.dequeue();
		sum += (int) queue.dequeue();
		assertFalse(queue.isEmpty());
		sum += (int) queue.dequeue();
		assertEquals(0, queue.size());
		assertTrue(queue.isEmpty());

		assertEquals(11, sum);
	}

	@Test
	public void doubleIterate() {
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(5);
		Iterator<Integer> it1 = queue.iterator();
		Iterator<Integer> it2 = queue.iterator();
		assertTrue(it1.hasNext());
		assertTrue(it2.hasNext());
		int sum1 = (int) it1.next();
		int sum2 = (int) it2.next();
		sum1 += (int) it1.next();
		sum2 += (int) it2.next();
		sum1 += (int) it1.next();
		sum2 += (int) it2.next();
		sum1 += (int) it1.next();
		sum2 += (int) it2.next();
		assertFalse(it1.hasNext());
		assertFalse(it2.hasNext());
		assertEquals(11, sum1);
		assertEquals(11, sum2);
	}

	@Test(expected = NoSuchElementException.class)
	public void dequeueEmpty() {
		queue.enqueue(10);
		queue.dequeue();
		queue.dequeue();
	}

	@Test(expected = NoSuchElementException.class)
	public void iterateAfterEnd() {
		queue.iterator().next();
	}

	@Test(expected = IllegalArgumentException.class)
	public void enqueueNull() {
		queue.enqueue(null);
	}

	@Test(expected = NoSuchElementException.class)
	public void sampleEmpty() {
		queue.sample();
	}
}
