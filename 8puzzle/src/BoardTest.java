import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Iterator;

import org.junit.Test;

public class BoardTest {
	int[][] mixedTiles = new int[][] { new int[] { 3, 5, 4 }, new int[] { 8, 0, 7 }, new int[] { 2, 1, 6 } };
	int[][] mixedTilesNeighbor1 = new int[][] { new int[] { 3, 0, 4 }, new int[] { 8, 5, 7 }, new int[] { 2, 1, 6 } };
	int[][] mixedTilesNeighbor2 = new int[][] { new int[] { 3, 5, 4 }, new int[] { 8, 1, 7 }, new int[] { 2, 0, 6 } };
	int[][] mixedTilesNeighbor3 = new int[][] { new int[] { 3, 5, 4 }, new int[] { 0, 8, 7 }, new int[] { 2, 1, 6 } };
	int[][] mixedTilesNeighbor4 = new int[][] { new int[] { 3, 5, 4 }, new int[] { 8, 7, 0 }, new int[] { 2, 1, 6 } };
	int[][] solvedTiles = new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, new int[] { 7, 8, 0 } };
	int[][] solvedTilesNeighbor1 = new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, 0 }, new int[] { 7, 8, 6 } };
	int[][] solvedTilesNeighbor2 = new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, new int[] { 7, 0, 8 } };

	@Test(expected = IllegalArgumentException.class)
	public void nullArgument() {
		new Board(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullRow() {
		new Board(new int[][] { new int[] { 1, 2 }, null });
	}

	@Test(expected = IllegalArgumentException.class)
	public void notSquare() {
		new Board(new int[][] { new int[] { 1, 2 }, new int[] { 1 } });
	}

	@Test
	public void equal() {
		Board b1 = new Board(mixedTiles);
		Board b2 = new Board(mixedTiles);
		Board b3 = new Board(solvedTiles);
		assertTrue(b1.equals(b2));
		assertTrue(b2.equals(b1));
		assertFalse(b2.equals(b3));
		assertFalse(b3.equals(b2));
	}

	@Test
	public void neighbors() {
		Board b1 = new Board(mixedTiles);
		Iterator<Board> neighbors = b1.neighbors().iterator();
		assertEquals(new Board(mixedTilesNeighbor1), neighbors.next());
		assertEquals(new Board(mixedTilesNeighbor2), neighbors.next());
		assertEquals(new Board(mixedTilesNeighbor3), neighbors.next());
		assertEquals(new Board(mixedTilesNeighbor4), neighbors.next());
		assertFalse(neighbors.hasNext());

		Board b2 = new Board(solvedTiles);
		neighbors = b2.neighbors().iterator();
		assertEquals(new Board(solvedTilesNeighbor1), neighbors.next());
		assertEquals(new Board(solvedTilesNeighbor2), neighbors.next());
		assertFalse(neighbors.hasNext());
	}

	@Test
	public void distances() {
		Board b1 = new Board(mixedTiles);
		assertEquals(8, b1.hamming());
		assertEquals(18, b1.manhattan());
		assertFalse(b1.isGoal());

		Board b2 = new Board(solvedTiles);
		assertEquals(0, b2.hamming());
		assertEquals(0, b2.manhattan());
		assertTrue(b2.isGoal());

		Board b3 = new Board(solvedTilesNeighbor1);
		assertEquals(1, b3.hamming());
		assertEquals(1, b3.manhattan());
		assertFalse(b3.isGoal());
	}

	@Test
	public void twin() {
		int[][] tiles = new int[][] { new int[] { 0, 1 }, new int[] { 2, 3 } };
		Board b1 = new Board(tiles);
		Board b2 = b1.twin();
		assertNotEquals(b1, b2);
	}
}
