import java.util.ArrayList;
import java.util.Arrays;

public class Board {

	private final int[][] tiles;
	private int hamming;
	private int manhattan;
	// The coordinate of the blank position
	private int blankRow;
	private int blankCol;

	// create a board from an n-by-n array of tiles,
	// where tiles[row][col] = tile at (row, col)
	public Board(int[][] tiles) {
		if (tiles == null) {
			throw new IllegalArgumentException();
		}
		int n = tiles.length;
		this.tiles = new int[n][];
		for (int row = 0; row < n; row++) {
			if (tiles[row] == null || tiles[row].length != n) {
				throw new IllegalArgumentException();
			}
			this.tiles[row] = new int[n];
			System.arraycopy(tiles[row], 0, this.tiles[row], 0, n);
			updateDistances(tiles[row], row);
		}
	}

	// string representation of this board
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(dimension());
		sb.append("%n");
		for (int[] row : tiles) {
			for (int tile : row) {
				sb.append(' ');
				sb.append(tile);
			}
			sb.append("%n");
		}
		return String.format(sb.toString());
	}

	// board dimension n
	public int dimension() {
		return tiles.length;
	}

	// number of tiles out of place
	public int hamming() {
		return hamming;
	}

	// sum of Manhattan distances between tiles and goal
	public int manhattan() {
		return manhattan;
	}

	// is this board the goal board?
	public boolean isGoal() {
		return hamming == 0;
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (!(y instanceof Board)) {
			return false;
		}
		Board yBoard = (Board) y;
		return Arrays.deepEquals(tiles, yBoard.tiles);
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {
		ArrayList<Board> result = new ArrayList<>();
		if (blankRow != 0)
			result.add(neighbor(blankRow, blankCol, blankRow - 1, blankCol));
		if (blankRow != dimension() - 1)
			result.add(neighbor(blankRow, blankCol, blankRow + 1, blankCol));
		if (blankCol != 0)
			result.add(neighbor(blankRow, blankCol, blankRow, blankCol - 1));
		if (blankCol != dimension() - 1)
			result.add(neighbor(blankRow, blankCol, blankRow, blankCol + 1));
		return result;
	}

	private Board neighbor(int r1, int c1, int r2, int c2) {
		swap(r1, c1, r2, c2);
		Board result = new Board(tiles);
		swap(r1, c1, r2, c2);
		return result;
	}

	private void swap(int r1, int c1, int r2, int c2) {
		int s = tiles[r1][c1];
		tiles[r1][c1] = tiles[r2][c2];
		tiles[r2][c2] = s;
	}

	// a board that is obtained by exchanging any pair of tiles
	public Board twin() {
		int n = dimension();
		int r1 = blankRow;
		int r2 = (blankRow + 1) % n;
		int c1 = blankCol;
		int c2 = (blankCol + 1) % n;
		swap(r1, c2, r2, c1);
		Board result = new Board(tiles);
		swap(r1, c2, r2, c1);
		return result;
	}

	private void updateDistances(int[] tiles, int row) {
		int n = tiles.length;
		for (int col = 0; col < n; col++) {
			int tile = tiles[col];
			if (tile != 0) {
				int expectedRow = (tile - 1) / n;
				int expectedCol = (tile - 1) % n;
				if (expectedRow != row || expectedCol != col)
					hamming += 1;
				manhattan += (Math.abs(row - expectedRow) + Math.abs(col - expectedCol));
			} else {
				blankCol = col;
				blankRow = row;
			}
		}
	}
}