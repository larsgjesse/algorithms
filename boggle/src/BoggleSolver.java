import java.util.HashSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
	private static final int[] score = new int[] { 0, 0, 1, 1, 2, 3, 5 };
	private static final byte ASCII_A = (byte) 'A';
	private static final byte Q = (byte) ((byte) 'Q' - ASCII_A); // Byte value of letter Q in 26-way trie
	private static final byte U = (byte) ((byte) 'U' - ASCII_A); // Byte value of letter U in 26-way trie
	private static final int R = 26;

	private Node root; // root of dictionary Trie

	private static class Node {
		private Node[] next = new Node[R];
		private boolean isString;
	}

	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A through Z.)
	public BoggleSolver(String[] dictionary) {
		if (dictionary == null)
			throw new IllegalArgumentException();
		for (String word : dictionary) {
			add(word);
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		if (board == null)
			throw new IllegalArgumentException();
		Solver solver = new Solver(board);
		return solver.solve();
	}

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through Z.)
	public int scoreOf(String word) {
		if (word == null)
			throw new IllegalArgumentException();
		if (!contains(word))
			return 0;
		int length = word.length();
		if (length <= score.length) {
			return score[length - 1];
		}
		return 11;
	}

	/**
	 * Adds the key to the set if it is not already present.
	 * 
	 * @param key the key to add
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	private void add(String key) {
		root = add(root, stringToBytes(key), 0);
	}

	private Node add(Node x, byte[] key, int d) {
		if (x == null)
			x = new Node();
		if (d == key.length) {
			x.isString = true;
		} else {
			byte c = key[d];
			x.next[c] = add(x.next[c], key, d + 1);
		}
		return x;
	}

	private boolean contains(String key) {
		Node x = get(root, stringToBytes(key), 0);
		if (x == null)
			return false;
		return x.isString;
	}

	private Node get(Node x, byte[] key, int d) {
		if (x == null)
			return null;
		if (d == key.length)
			return x;
		byte c = key[d];
		return get(x.next[c], key, d + 1);
	}

	// Convert string to bytes in interval [0-25].
	private byte[] stringToBytes(String word) {
		byte[] result = word.toUpperCase().getBytes();
		for (int i = 0; i < result.length; i++) {
			if (result[i] < ASCII_A || result[i] > ASCII_A + 25)
				throw new IllegalArgumentException();
			result[i] = (byte) (result[i] - ASCII_A);
		}
		return result;
	}

	private class Solver {
		private boolean[][] visited; // Keeps track of which tiles have been visited on the current path
		private HashSet<String> words; // Collects the words
		private byte[][] board; // Byte-representation of the BoggleBoard, applicable for 26-way trie

		public Solver(BoggleBoard boggleBoard) {
			visited = new boolean[boggleBoard.rows()][];
			board = new byte[boggleBoard.rows()][];
			for (int i = 0; i < visited.length; i++) {
				visited[i] = new boolean[boggleBoard.cols()];
				board[i] = new byte[boggleBoard.cols()];
				for (int j = 0; j < boggleBoard.cols(); j++) {
					byte c = (byte) Character.toUpperCase(boggleBoard.getLetter(i, j));
					board[i][j] = (byte) (c - ASCII_A);
				}
			}
		}

		public Iterable<String> solve() {
			words = new HashSet<String>();
			// Find words, starting at each individual tile position
			byte[] wordBuilder = new byte[board[0].length * board.length];
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					findWordsFromPosition(i, j, root, wordBuilder, 0);
				}
			}
			return words;
		}

		private void findWordsFromPosition(int row, int col, Node node, byte[] builder, int position) {
			byte c = board[row][col];
			Node next = node.next[c];
			if (next == null)
				return;
			if (c == Q) {
				next = next.next[U];
				if (next == null)
					return;
			}
			builder[position++] = c;
			visited[row][col] = true;
			if (next.isString) {
				addWord(builder, position);
			}

			// Try all neighboring tiles
			for (int newRow = Math.max(0, row - 1); newRow < Math.min(board.length, row + 2); newRow++) {
				for (int newCol = Math.max(0, col - 1); newCol < Math.min(board[row].length, col + 2); newCol++) {
					if (!visited[newRow][newCol])
						findWordsFromPosition(newRow, newCol, next, builder, position);
				}
			}
			visited[row][col] = false;
		}

		private void addWord(byte[] builder, int position) {
			byte[] buffer = new byte[2 * position];
			int dpos = 0;
			for (int i = 0; i < position; i++) {
				buffer[dpos++] = (byte) (builder[i] + ASCII_A);
				if (builder[i] == Q)
					buffer[dpos++] = 'U';
			}
			if (dpos < 3)
				return; // Words must be at least 3 letters
			words.add(new String(buffer, 0, dpos));
		}
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
		BoggleBoard board = new BoggleBoard(args[1]);
		int score = 0;
		for (String word : solver.getAllValidWords(board)) {
			StdOut.println(word);
			score += solver.scoreOf(word);
		}
		StdOut.println("Score = " + score);
	}

}