import org.junit.Test;

public class SolverTest {

	int[][] mixedTiles = new int[][] { new int[] { 3, 5, 4 }, new int[] { 8, 0, 7 }, new int[] { 2, 1, 6 } };
	int[][] mixedTilesNeighbor1 = new int[][] { new int[] { 3, 0, 4 }, new int[] { 8, 5, 7 }, new int[] { 2, 1, 6 } };
	int[][] mixedTilesNeighbor2 = new int[][] { new int[] { 3, 5, 4 }, new int[] { 8, 1, 7 }, new int[] { 2, 0, 6 } };
	int[][] mixedTilesNeighbor3 = new int[][] { new int[] { 3, 5, 4 }, new int[] { 0, 8, 7 }, new int[] { 2, 1, 6 } };
	int[][] mixedTilesNeighbor4 = new int[][] { new int[] { 3, 5, 4 }, new int[] { 8, 7, 0 }, new int[] { 2, 1, 6 } };
	int[][] solvedTiles = new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, new int[] { 7, 8, 0 } };

	int[][] nearlySolvedTiles = new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 6, 0, }, new int[] { 7, 5, 8 } };
	int[][] nearlySolvedTiles2 = new int[][] { new int[] { 1, 3, 2 }, new int[] { 6, 4, 0, }, new int[] { 7, 5, 8 } };

	@Test(expected = IllegalArgumentException.class)
	public void nullArgument() {
		new Solver(null);
	}

	@Test
	public void solvedMixed() {
		Solver solver = new Solver(new Board(mixedTiles).twin());
		if (!solver.isSolvable()) {
			System.out.println("Not solvable");
			return;
		}
		System.out.println("moves:" + solver.moves());
		for (Board b : solver.solution()) {
			System.out.println(b);
		}
	}
}
