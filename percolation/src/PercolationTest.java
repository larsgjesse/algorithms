

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class PercolationTest {
	private static int n = 5;
	Percolation percolation = new Percolation(n);

	@Test
	public void create() {
		assertEquals(0, percolation.numberOfOpenSites());
		assertFalse(percolation.isOpen(1, 1));
		assertFalse(percolation.isFull(1, 1));
		assertFalse(percolation.percolates());
	}

	@Test
	public void open() {
		percolation.open(1, 3);
		assertTrue(percolation.isFull(1, 3));
		assertFalse(percolation.isFull(1, 2));
		assertEquals(1, percolation.numberOfOpenSites());
		assertFalse(percolation.percolates());
	}

	@Test
	public void openTwice() {
		percolation.open(1, 3);
		percolation.open(1, 3);
		assertEquals(1, percolation.numberOfOpenSites());
	}

	@Test
	public void openPath() {
		percolation.open(1, 1);
		percolation.open(2, 1);
		percolation.open(2, n);
		assertTrue(percolation.isFull(2, 1));
		// No wrap around
		assertFalse(percolation.isFull(2, n));
		// No diagonal connection
		percolation.open(3, 2);
		assertFalse(percolation.isFull(3, 2));
		assertEquals(4, percolation.numberOfOpenSites());
	}

	@Test
	public void percolates() {
		percolation.open(1, 2);
		percolation.open(2, 2);
		percolation.open(2, 3);
		
		percolation.open(5, 5);
		percolation.open(4, 5);
		percolation.open(4, 4);
		percolation.open(3, 4);

		assertTrue(percolation.isFull(2, 3));
		assertFalse(percolation.isFull(3, 4));
		assertFalse(percolation.percolates());

		percolation.open(3, 3);
		assertTrue(percolation.isFull(3, 4));
		assertTrue(percolation.isFull(4, 5));
		assertTrue(percolation.percolates());
	}

	@Test
	public void backWash() {
		percolation.open(1, 2);
		percolation.open(2, 2);
		percolation.open(3, 2);
		percolation.open(4, 2);

		percolation.open(3, 4);
		percolation.open(4, 4);
		percolation.open(5, 4);

		assertFalse(percolation.percolates());
		percolation.open(5, 2);
		assertTrue(percolation.percolates());
		assertFalse(percolation.isFull(3, 4));
		assertFalse(percolation.isFull(4, 4));
		assertFalse(percolation.isFull(5, 4));
	}

	
	@Test
	public void invalidArguments() {
		try {
			percolation.isFull(0, 1);
			fail("");
		} catch (IllegalArgumentException e) {}
		try {
			percolation.isFull(n+1, 1);
			fail("");
		} catch (IllegalArgumentException e) {}
		try {
			percolation.isFull(1, 0);
			fail("");
		} catch (IllegalArgumentException e) {}
		try {
			percolation.isFull(1, n+1);
			fail("");
		} catch (IllegalArgumentException e) {}
	}
}
