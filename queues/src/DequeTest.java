
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class DequeTest {
	Deque<Integer> deque = new Deque<>();

	@Test
	public void addFirstRemoveFirst() {
		assertEquals(0, deque.size());
		deque.addFirst(10);
		assertEquals(1, deque.size());
		deque.addFirst(11);
		assertEquals(2, deque.size());
		assertEquals(11, (int)deque.removeFirst());
		assertEquals(1, deque.size());
		assertEquals(10, (int)deque.removeFirst());
		assertEquals(0, deque.size());
	}

	@Test
	public void addFirstRemoveLast() {
		deque.addFirst(10);
		deque.addFirst(11);
		assertEquals(10, (int)deque.removeLast());
		assertEquals(1, deque.size());
		assertEquals(11, (int)deque.removeLast());
		assertEquals(0, deque.size());
	}

	@Test
	public void addLastRemoveLast() {
		deque.addLast(10);
		assertEquals(1, deque.size());
		deque.addLast(11);
		assertEquals(2, deque.size());
		assertEquals(11, (int)deque.removeLast());
		assertEquals(10, (int)deque.removeLast());
	}

	@Test
	public void addLastRemoveFirst() {
		deque.addLast(10);
		deque.addLast(11);
		assertEquals(10, (int)deque.removeFirst());
		assertEquals(11, (int)deque.removeFirst());
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFirstNull() {
		deque.addFirst(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void addLastNull() {
		deque.addLast(null);
	}

	@Test(expected=NoSuchElementException.class)
	public void removeFirstEmpty() {
		deque.removeFirst();
	}

	@Test(expected=NoSuchElementException.class)
	public void removeLastEmpty() {
		deque.removeLast();
	}

	@Test(expected=NoSuchElementException.class)
	public void removeExcess() {
		deque.addFirst(10);
		deque.removeLast();
		deque.removeLast();
	}

	@Test(expected=NoSuchElementException.class)
	public void iterateEmpty() {
		Iterator<Integer> it = deque.iterator();
		assertFalse(it.hasNext());
		it.next();
	}

	@Test
	public void iterate() {
		for (int i = 0; i < 1000000; i++) {
			deque.addLast(i);
		}
		
		Iterator<Integer> it = deque.iterator();
		for (int i = 0; i < 1000000; i++) {
			assertTrue(it.hasNext());
			assertEquals(i, (int)it.next());
		}
		assertFalse(it.hasNext());
	}
}
